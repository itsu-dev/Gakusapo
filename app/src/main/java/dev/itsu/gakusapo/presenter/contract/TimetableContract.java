package dev.itsu.gakusapo.presenter.contract;

import android.app.Activity;
import android.widget.AdapterView;
import dev.itsu.gakusapo.entity.Timetable;
import dev.itsu.gakusapo.ui.adapter.TimetableAdapter;

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
        void setTimetableButtonVisible(boolean bool);
        void showEditmodeToast(boolean bool);
        void showSelectToast(boolean bool);
    }

    interface Presenter {
        void initialize();
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
        void onDestroy();
    }
}
