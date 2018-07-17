package drunkcoder.com.collegebuddy;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name="DayOfWeek")
public class DayOFWeek extends Model {
    @Column(name="name")
    int name;
    @Column(name="scheduleOfWeek")
    List<Schedule> scheduleOfWeek;
    @Column(name="totalClasses")
    int totalClasses;

    public DayOFWeek(int name, List<Schedule> scheduleOfWeek, int totalClasses) {
        super();
        this.name = name;
        this.scheduleOfWeek = scheduleOfWeek;
        this.totalClasses = totalClasses;
    }

    public DayOFWeek()
    {
        super();
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public List<Schedule> getScheduleOfWeek() {
        return scheduleOfWeek;
    }

    public void setScheduleOfWeek(List<Schedule> scheduleOfWeek) {
        this.scheduleOfWeek = scheduleOfWeek;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
}
