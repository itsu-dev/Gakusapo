package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.DateEventDBService;
import gakusapo.android.itsu.api.service.PreferencesService;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.presenter.contract.TodayAndTomorrowContract;
import gakusapo.android.itsu.ui.activity.MainActivity;
import gakusapo.android.itsu.ui.fragment.MemoDialogFragment;
import gakusapo.android.itsu.ui.fragment.RegisterDateEventListDialogFragment;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.*;

public class TodayAndTomorrowPresenter implements TodayAndTomorrowContract.Presenter {

    public static final int TYPE_HOMEWORK = 0;
    public static final int TYPE_SUBMISSION = 1;
    public static final int TYPE_TEST = 2;
    public static final int TYPE_CLASS = 3;
    public static final int TYPE_EVENT = 4;
    public static final int TYPE_REMINDER = 5;

    private TodayAndTomorrowContract.View view;

    private DateEvent currentDateEvent;

    public TodayAndTomorrowPresenter(TodayAndTomorrowContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadData() {
        reloadData(DateEventDBService.getDate());
    }

    @Override
    public void reloadData(String date) {
        currentDateEvent = null;

        List<String> defaultList = new ArrayList<>();
        defaultList.add(view.getActivity().getResources().getString(R.string.today_and_tomorrow_nothing));

        DateEventDBService service = new DateEventDBService(view.getActivity());
        currentDateEvent = service.getDateEvent(date);

        if (currentDateEvent == null) {
            currentDateEvent = new DateEvent(date);
            view.setMemo("");
            view.setHomeworks(defaultList);
            view.setSubmissions(defaultList);
            view.setTests(defaultList);
            view.setClasses(defaultList);
            view.setEvents(defaultList);
            view.setReminders(new HashMap<String, Boolean>());

        } else {
            view.setMemo(currentDateEvent.getMemo());
            view.setHomeworks(currentDateEvent.getHomeworks().isEmpty() ? defaultList : currentDateEvent.getHomeworks());
            view.setSubmissions(currentDateEvent.getSubmissions().isEmpty() ? defaultList : currentDateEvent.getSubmissions());
            view.setTests(currentDateEvent.getTests().isEmpty() ? defaultList : currentDateEvent.getTests());
            view.setClasses(currentDateEvent.getClasses().isEmpty() ? defaultList : currentDateEvent.getClasses());
            view.setEvents(currentDateEvent.getEvents().isEmpty() ? defaultList : currentDateEvent.getEvents());
            view.setReminders(currentDateEvent.getReminders().isEmpty() ? new HashMap<String, Boolean>() : currentDateEvent.getReminders());
        }

        view.setDate(currentDateEvent.getDate());

        if (date.equals(DateEventDBService.getDate())) {
            if (DateEventDBService.isTomorrow()) {
                view.setTitle(R.string.today_and_tomorrow_tomorrow);
            } else {
                view.setTitle(R.string.today_and_tomorrow_today);
            }
        } else {
            view.setTitle(R.string.today_and_tomorrow_schedule);
        }

        processTimetable(date);
    }

    @Override
    public void onCalendarScrolled(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        view.setDate(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1));
    }

    @Override
    public void onCalendarDateClicked(Date date) {
        view.setDate(TimetableUtils.parseDate(date));
        reloadData(TimetableUtils.parseDate(date));
    }

    @Override
    public void onMemoButtonClicked() {
        MainActivity activity = (MainActivity) view.getActivity();
        MemoDialogFragment fragment = MemoDialogFragment.newInstance(this, currentDateEvent == null ? "" : currentDateEvent.getMemo());
        fragment.show(activity.getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onContentButtonClicked(int type) {
        List<String> data;
        int titleId;

        switch (type) {
            case TYPE_HOMEWORK:
                data = currentDateEvent.getHomeworks();
                titleId = R.string.today_and_tomorrow_homework;
                break;
            case TYPE_SUBMISSION:
                data = currentDateEvent.getSubmissions();
                titleId = R.string.today_and_tomorrow_submission;
                break;
            case TYPE_TEST:
                data = currentDateEvent.getTests();
                titleId = R.string.today_and_tomorrow_test;
                break;
            case TYPE_CLASS:
                data = currentDateEvent.getClasses();
                titleId = R.string.today_and_tomorrow_class;
                break;
            case TYPE_EVENT:
                data = currentDateEvent.getEvents();
                titleId = R.string.today_and_tomorrow_event;
                break;
            case TYPE_REMINDER:
                data = new ArrayList<>(currentDateEvent.getReminders().keySet());
                titleId = R.string.today_and_tomorrow_reminder;
                break;
            default:
                return;
        }

        MainActivity activity = (MainActivity) view.getActivity();
        RegisterDateEventListDialogFragment fragment = RegisterDateEventListDialogFragment.newInstance(this, data, titleId, type);
        fragment.show(activity.getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onDateEventDialogCallback(List<String> data, int type) {
        switch (type) {
            case TYPE_HOMEWORK:
                currentDateEvent.setHomeworks(data);
                break;
            case TYPE_SUBMISSION:
                currentDateEvent.setSubmissions(data);
                break;
            case TYPE_TEST:
                currentDateEvent.setTests(data);
                break;
            case TYPE_CLASS:
                currentDateEvent.setClasses(data);
                break;
            case TYPE_EVENT:
                currentDateEvent.setEvents(data);
                break;
            case TYPE_REMINDER:
                Map<String, Boolean> reminders = new HashMap<>();
                for (String s : data) {
                    if (currentDateEvent.getReminders().containsKey(s)) {
                        reminders.put(s, currentDateEvent.getReminders().get(s));
                    } else {
                        reminders.put(s, false);
                    }
                }
                currentDateEvent.setReminders(reminders);
                break;
            default:
                return;
        }
        refresh();
    }

    @Override
    public void onMemoDialogCallback(String memo) {
        if (memo != null && !memo.isEmpty()) {
            currentDateEvent.setMemo(memo);
            refresh();
        }
    }

    @Override
    public void onReminderChecked(String text, boolean checked) {
        if (currentDateEvent.getReminders().containsKey(text)) {
            currentDateEvent.getReminders().put(text, checked);
            refresh();
        }
    }

    private void processTimetable(String date) {
        TimetableDBService service = new TimetableDBService(view.getActivity());
        Timetable timetable = service.getTimetables().get(PreferencesService.get().getString("CurrentTimetable", null));

        int day = TimetableUtils.dayToWeek(date);

        if (day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY)) {
            view.setHoliday(true);
        } else {
            view.setHoliday(false);
            view.setTimetables(TimetableUtils.getDaySubjects(timetable, day));
        }
    }

    private void saveDateEvent() {
        DateEventDBService service = new DateEventDBService(view.getActivity());
        if (service.getDateEvent(currentDateEvent.getDate()) == null) {
            service.addDateEvent(currentDateEvent);
        } else {
            service.updateDateEvent(currentDateEvent);
        }
    }

    private void refresh() {
        saveDateEvent();
        reloadData(currentDateEvent.getDate());
    }
}
