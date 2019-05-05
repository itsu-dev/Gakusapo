package gakusapo.android.itsu.api.service;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gakusapo.android.itsu.db.DatabaseDAO;
import gakusapo.android.itsu.entity.Subject;
import gakusapo.android.itsu.entity.Timetable;
import gakusapo.android.itsu.utils.TimetableUtils;

import java.util.ArrayList;
import java.util.List;

public class TimetableEditService {

    private Timetable timetable;
    private List<Subject> selectedSubject;
    private Subject oneSubject;

    public TimetableEditService(Timetable timetable) {
        this.timetable = timetable;
        this.selectedSubject = new ArrayList<>();
        timetable.setSubjects(TimetableUtils.sortByPosition(timetable.getSubjects()));

        PreferencesService.setEditing(true);
    }

    public void addSelectedSubject(int position) {
        addSelectedSubject(timetable.getSubjects().get(position));
    }

    public void addSelectedSubject(Subject subject) {
        selectedSubject.add(subject);
    }

    public void removeSelectedSubject(int position) {
        removeSelectedSubject(timetable.getSubjects().get(position));
    }

    public void removeSelectedSubject(Subject subject) {
        selectedSubject.remove(subject);
    }

    public void clearSelectedSubject() {
        selectedSubject.clear();
    }

    public boolean isSelectedSubject(int position) {
        return isSelectedSubject(timetable.getSubjects().get(position));
    }

    public boolean isSelectedSubject(Subject subject) {
        return selectedSubject.contains(subject);
    }

    public Subject getOneSubject() {
        return oneSubject;
    }

    public void setOneSubject(int position) {
        setOneSubject(timetable.getSubjects().get(position));
    }

    public void setOneSubject(Subject oneSubject) {
        this.oneSubject = oneSubject;
    }

    public void setOneSubjectData(String name, String className, String description, int backgroundId) {
        if (oneSubject != null) {
            oneSubject.setBackground(backgroundId);
            oneSubject.setName(name);
            oneSubject.setDescription(description);
            oneSubject.setClassName(className);

            timetable.getSubjects().set(oneSubject.getPosition(), oneSubject);
            oneSubject = null;

            refresh();
        }
    }

    public void setSelectedSubjectData(String name, String className, String description, int backgroundId) {
        if (selectedSubject.size() > 0) {
            for (Subject subject : selectedSubject) {
                subject.setName(name);
                subject.setClassName(className);
                subject.setDescription(description);
                subject.setBackground(backgroundId);
                timetable.getSubjects().set(subject.getPosition(), subject);
            }
            refresh();
        }
    }

    public void setName(String name) {
        timetable.setName(name);
    }

    public void setDayType(int type) {
        timetable.setDayType(type);
    }

    public void setTimeType(int type) {
        timetable.setTimeType(type);
    }

    public List<Subject> getSelectedSubject() {
        return selectedSubject;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public static Timetable getEditingTimetable() {
        return (Timetable) new Gson().fromJson(PreferencesService.getEditdata(), new TypeToken<Timetable>(){}.getType());
    }

    public static void deleteEditingTimetable() {
        PreferencesService.setEditing(false);
        PreferencesService.removeEditData();
    }

    public void save() {
        if (DatabaseDAO.existsTimetable(timetable.getName())) {
            DatabaseDAO.updateTimetable(timetable);
        } else {
            DatabaseDAO.addTimetable(timetable);
        }

        PreferencesService.setEditing(false);
        PreferencesService.removeEditData();
    }

    private void refresh() {
        PreferencesService.setEditing(true);
        PreferencesService.setEditData(new Gson().toJson(timetable));
    }

}
