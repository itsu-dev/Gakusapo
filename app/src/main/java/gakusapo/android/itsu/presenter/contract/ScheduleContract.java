package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import gakusapo.android.itsu.entity.DateEvent;
import gakusapo.android.itsu.entity.Subject;

import java.util.List;

public interface ScheduleContract {

    interface View {
        Activity getActivity();
        void addEvent(DateEvent event);
        void setTimetables(List<Subject> subjects);
    }

}
