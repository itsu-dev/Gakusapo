package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import gakusapo.android.itsu.entity.Subject;

import java.util.List;
import java.util.Map;

public interface TodayAndTomorrowContract {

    interface View {
        Activity getActivity();
        void setTitle(int id);
        void setDate(String date);
        void setMemo(String memo);
        void setHomeworks(List<String> data);
        void setSubmissions(List<String> data);
        void setTests(List<String> data);
        void setClasses(List<String> data);
        void setEvents(List<String> data);
        void setReminders(Map<String, Boolean> data);
        void setTimetables(List<Subject> subjects);
        void setHoliday(boolean bool);
    }

    interface Presenter {
        void reloadData();
        void onMemoButtonClicked();
        void onContentButtonClicked(int type);
        void onMemoDialogCallback(String memo);
        void onDateEventDialogCallback(List<String> data, int type);
        void onReminderChecked(String text, boolean checked);
    }
}
