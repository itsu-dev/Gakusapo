package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.api.service.DateEventDBService;
import gakusapo.android.itsu.api.service.PreferencesService;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.presenter.contract.ScheduleContract;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.Calendar;
import java.util.Date;

public class SchedulePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View view;

    public SchedulePresenter(ScheduleContract.View view) {
        this.view = view;
    }

    @Override
    public void reloadCalendar() {
        Calendar calendar = Calendar.getInstance();
        view.setDateText(calendar.get(Calendar.YEAR) + "年 " + (calendar.get(Calendar.MONTH) + 1) + "月");
    }

    @Override
    public void reloadSubjects() {
        reloadSubjects(DateEventDBService.getDate());
    }

    @Override
    public void reloadSubjects(String date) {
        TimetableDBService service = new TimetableDBService(view.getActivity());
        Timetable timetable = service.getTimetables().get(PreferencesService.get().getString("CurrentTimetable", null));

        int day = TimetableUtils.dayToWeek(date);

        if (day == -1 || (day == 5 && timetable.getDayType() == Timetable.DAY_TYPE_MONDAY_TO_FRIDAY)) {
            view.setHoliday(true);
        } else {
            view.setHoliday(false);
            view.setSubjects(TimetableUtils.getDaySubjects(timetable, day));
        }
    }

    @Override
    public void reloadEvents() {

    }

    @Override
    public void onCalendarScrolled(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        view.setDateText(calendar.get(Calendar.YEAR) + "年 " + (calendar.get(Calendar.MONTH) + 1) + "月");
    }

    @Override
    public void onCalendarDateClicked(Date date) {
        reloadSubjects(TimetableUtils.parseDate(date));
    }
}
