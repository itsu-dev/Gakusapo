package gakusapo.android.itsu.presenter.contract;

import android.app.Activity;
import android.widget.AdapterView;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.ui.adapter.TimetableAdapter;

public interface TimetableContract {

    interface View {
        Activity getActivity();
        android.view.View getView();
        void setSubjects(TimetableAdapter adapter);
        void setTimetableTitle(String title);
        void setEditmode(boolean bool);
        void setTimetableType(int dayType, int timeType);
        void setSubjectBackground(int position, int colorId);
        void setSelectButtonClicked(boolean bool);
        void showEditmodeToast(boolean bool);
        void showSelectToast(boolean bool);
    }

    interface Presenter {
        void reloadTimetable();
        void reloadTimetable(Timetable timetable);
        void setCurrentTimetableName(String name);
        void onSubjectClicked(AdapterView<?> parent, android.view.View view, int position, long id);
        void onTimetableEditButtonClicked();
        void onTimetableAddButtonClicked();
        void onTimetableSelectButtonClicked();
        void onTimetableEditSubjectButtonClicked();
        void onRegisterSubjectDialogCallback(String name, String className, String description, int background);
        void onCreateTimetableDialogCallback(String name, int dayType, int timeType);
    }
}
