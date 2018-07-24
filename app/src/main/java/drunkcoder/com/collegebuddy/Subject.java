package drunkcoder.com.collegebuddy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name="Subject")
public class Subject extends Model {
    @Column(name="name")
    String name;
    @Column(name="attendance")
    Attendance attendance;


    public Subject(String name) {
        super();
        this.name = name;
    }

    public Subject()
    {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
       return name;
    }

    public static List<Subject> getSubjects()
    {
        return new Select().from(Subject.class).execute();
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

}
