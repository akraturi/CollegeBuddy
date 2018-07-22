package drunkcoder.com.collegebuddy;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

@Table(name="DayOfWeek")
public class DayOFWeek extends Model {
    @Column(name="name")
    String name;
    @Column(name="scheduleOfWeek")
    List<Schedule> scheduleOfDay;
    @Column(name="totalClasses")
    int totalClasses;

    public DayOFWeek(String name, List<Schedule> scheduleOfWeek, int totalClasses) {
        super();
        this.name = name;
        this.scheduleOfDay = scheduleOfWeek;
        this.totalClasses = totalClasses;
    }

    public DayOFWeek()
    {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Schedule> getScheduleOfDay() {
        return scheduleOfDay;
    }

    public void setScheduleOfDay(List<Schedule> scheduleOfDay) {
        this.scheduleOfDay = scheduleOfDay;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public void addToSchedule(Schedule schedule)
    {
        scheduleOfDay.add(schedule);
    }


}
