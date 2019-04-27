package gakusapo.android.itsu.entity;

import java.util.ArrayList;
import java.util.List;

public class DateEvent {

    private String date;
    private String memo;
    private List<String> homeworks;
    private List<String> submissions;
    private List<String> tests;
    private List<String> classes;
    private List<String> events;

    public DateEvent(String date) {
        this.date = date;
        memo = "";
        homeworks = new ArrayList<>();
        submissions = new ArrayList<>();
        tests = new ArrayList<>();
        classes = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<String> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<String> homeworks) {
        this.homeworks = homeworks;
    }

    public List<String> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<String> submissions) {
        this.submissions = submissions;
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }
}
