package gakusapo.android.itsu.presenter;

import gakusapo.android.itsu.api.service.DateEventDBService;
import gakusapo.android.itsu.api.service.PreferencesService;
import gakusapo.android.itsu.api.service.TimetableDBService;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.presenter.contract.ScheduleContract;
import gakusapo.android.itsu.utils.TimetableUtils;

public class SchedulePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View view;

    public SchedulePresenter(ScheduleContract.View view) {
        this.view = view;
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

}
