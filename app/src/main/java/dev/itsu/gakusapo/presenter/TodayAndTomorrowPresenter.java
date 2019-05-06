package dev.itsu.gakusapo.presenter;

import dev.itsu.gakusapo.R;
import dev.itsu.gakusapo.api.service.PreferencesService;
import dev.itsu.gakusapo.db.DatabaseDAO;
import dev.itsu.gakusapo.entity.DateEvent;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.presenter.contract.onDestroy;
import dev.itsu.gakusapo.ui.activity.MainActivity;
import dev.itsu.gakusapo.ui.dialog.MemoDialogFragment;
import dev.itsu.gakusapo.ui.dialog.RegisterDateEventListDialogFragment;
import dev.itsu.gakusapo.utils.TimetableUtils;

import java.util.*;

public class TodayAndTomorrowPresenter implements onDestroy.Presenter {

    public static final int TYPE_HOMEWORK = 0;
    public static final int TYPE_SUBMISSION = 1;
    public static final int TYPE_TEST = 2;
    public static final int TYPE_CLASS = 3;
    public static final int TYPE_EVENT = 4;
    public static final int TYPE_REMINDER = 5;

    private onDestroy.View view;

    private DateEvent currentDateEvent;

    public TodayAndTomorrowPresenter(onDestroy.View view) {
        this.view = view;
    }

    @Override
    public void reloadData() {
        reloadData(TimetableUtils.getDate());
    }

    @Override
    public void reloadData(String date) {
        currentDateEvent = null;

        List<String> defaultList = new ArrayList<>();
        defaultList.add(view.getActivity().getResources().getString(R.string.today_and_tomorrow_nothing));

        currentDateEvent = DatabaseDAO.getDateEvent(date);

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

        if (date.equals(TimetableUtils.getDate())) {
            if (TimetableUtils.isTomorrow()) {
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
    public void reloadCalendar() {
        List<DateEvent> events = DatabaseDAO.getDateEvents();
        for (DateEvent event : events) {
            if (!event.getTests().isEmpty()) {
                view.addEvent(R.color.default_red, TimetableUtils.fromString(event.getDate()));
            } else {
                view.addEvent(R.color.colorPrimaryDark, TimetableUtils.fromString(event.getDate()));
            }
        }
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
        Timetable timetable = DatabaseDAO.getTimetable(PreferencesService.getCurrentTimetable());

        int day = TimetableUtils.dayToWeek(date);

        if (day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY)) {
            view.setHoliday(true);
        } else {
            view.setHoliday(false);
            view.setTimetables(TimetableUtils.getDaySubjects(timetable, day));
        }
    }

    private void saveDateEvent() {
        if (DatabaseDAO.existsTimetable(currentDateEvent.getDate())) {
            DatabaseDAO.addDateEvent(currentDateEvent);
        } else {
            DatabaseDAO.updateDateEvent(currentDateEvent);
        }
    }

    private void refresh() {
        if (!currentDateEvent.getTests().isEmpty()) {
            view.addEvent(R.color.default_red, TimetableUtils.fromString(currentDateEvent.getDate()));
        } else {
            view.addEvent(R.color.colorPrimaryDark, TimetableUtils.fromString(currentDateEvent.getDate()));
        }
        saveDateEvent();
        reloadData(currentDateEvent.getDate());
    }

    @Override
    public void onPause() {
        view.setTimetables(null);
        view.setHomeworks(null);
        view.setSubmissions(null);
        view.setEvents(null);
        view.setTests(null);
        view.setClasses(null);

        currentDateEvent = null;
        view = null;
    }
}
