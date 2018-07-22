package drunkcoder.com.collegebuddy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.JsonObject;

@Table(name="Schedule")

public class Schedule extends Model {
        @Column(name="startTime")
        String startTime;
        @Column(name="endTime")
        String endTime;
        @Column(name="subject")
        Subject subject;
        @Column(name="venue")
        String venue;

    public Schedule(String startTime, String endTime, Subject subject, String venue) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.venue = venue;
    }
    public Schedule()
    {
        super();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public JsonObject getJsonObject()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("startTime",startTime);
        jsonObject.addProperty("endTime",endTime);
        jsonObject.addProperty("venue",venue);
//        jsonObject.addProperty("subject",subject.toString());

        return jsonObject;
    }

    @Override
    public String toString() {
        return getJsonObject().toString();
    }
}
