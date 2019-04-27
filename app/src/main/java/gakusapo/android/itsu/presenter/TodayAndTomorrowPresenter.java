package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.R;
import gakusapo.android.itsu.api.service.DateEventDBService;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.presenter.contract.TodayAndTomorrowContract;
import gakusapo.android.itsu.ui.activity.MainActivity;
import gakusapo.android.itsu.ui.fragment.MemoDialogFragment;
import gakusapo.android.itsu.ui.fragment.RegisterDateEventListDialogFragment;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.ArrayList;
import java.util.List;

public class TodayAndTomorrowPresenter implements TodayAndTomorrowContract.Presenter {

    public static final int TYPE_HOMEWORK = 0;
    public static final int TYPE_SUBMISSION = 1;
    public static final int TYPE_TEST = 2;
    public static final int TYPE_CLASS = 3;
    public static final int TYPE_EVENT = 4;

    private TodayAndTomorrowContract.View view;

    private DateEvent currentDateEvent;

    public TodayAndTomorrowPresenter(TodayAndTomorrowContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadData() {
        String date = DateEventDBService.getDate();
        List<String> defaultList = new ArrayList<>();
        defaultList.add(view.getActivity().getResources().getString(R.string.today_and_tomorrow_nothing));

        if (currentDateEvent == null) {
            DateEventDBService service = new DateEventDBService(view.getActivity());
            currentDateEvent = service.getDateEvent(date);
        }

        if (currentDateEvent == null) {
            currentDateEvent = new DateEvent(date);
            view.setMemo("");
            view.setHomeworks(defaultList);
            view.setSubmissions(defaultList);
            view.setTests(defaultList);
            view.setClasses(defaultList);
            view.setEvents(defaultList);

        } else {
            view.setMemo(currentDateEvent.getMemo());
            view.setHomeworks(currentDateEvent.getHomeworks().isEmpty() ? defaultList : currentDateEvent.getHomeworks());
            view.setSubmissions(currentDateEvent.getSubmissions().isEmpty() ? defaultList : currentDateEvent.getSubmissions());
            view.setTests(currentDateEvent.getTests().isEmpty() ? defaultList : currentDateEvent.getTests());
            view.setClasses(currentDateEvent.getClasses().isEmpty() ? defaultList : currentDateEvent.getClasses());
            view.setEvents(currentDateEvent.getEvents().isEmpty() ? defaultList : currentDateEvent.getEvents());
        }

        view.setDate(currentDateEvent.getDate());

        if (DateEventDBService.isTomorrow()) view.setTitle(R.string.today_and_tomorrow_tomorrow);
        else view.setTitle(R.string.today_and_tomorrow_today);

        processTimetable(date);
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

    private void processTimetable(String date) {
        TimetableDBService service = new TimetableDBService(view.getActivity());
        Timetable timetable = service.getTimetables().get("test");
        int day = TimetableUtils.dayToWeek(date);

        if (day == -1) {
            view.setHoliday(true);
        } else {
            view.setTimetables(TimetableUtils.getDaySubjects(timetable, day));
        }
    }

    private void saveDateEvent() {
        DateEventDBService service = new DateEventDBService(view.getActivity());
        if (service.getDateEvent(DateEventDBService.getDate()) == null) {
            service.addDateEvent(currentDateEvent);
        } else {
            service.updateDateEvent(currentDateEvent);
        }
    }

    private void refresh() {
        saveDateEvent();
        reloadData();
    }
}
