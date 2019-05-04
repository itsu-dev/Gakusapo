package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Subject;

import java.util.Date;
import java.util.List;

public interface ScheduleContract {

    interface View {
        Activity getActivity();
        void addEvent(DateEvent event);
        void setSubjects(List<Subject> subjects);
        void setHoliday(boolean bool);
        void setDateText(String dateText);
    }

    interface Presenter {
        void reloadSubjects(String date);
        void reloadSubjects();
        void reloadEvents();
        void reloadCalendar();
        void onCalendarScrolled(Date date);
        void onCalendarDateClicked(Date date);
    }

}
