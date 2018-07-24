package drunkcoder.com.collegebuddy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Attendance")
public class Attendance extends Model {

    @Column(name="attendedClasses")
    int attendedClasses;
    @Column(name="totalClasses")
    int totalClasses;

    public Attendance()
    {
        super();
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
}
