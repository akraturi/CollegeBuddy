package drunkcoder.com.collegebuddy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.UUID;

@Table(name="Subject")
public class Subject extends Model {
    @Column(name="name")
    String name;
    @Column(name="faculty")
    String faculty;
    @Column(name="uid")
    String id;

    public Subject(String name, String faculty) {
        super();
        this.name = name;
        this.faculty = faculty;
        id=UUID.randomUUID().toString();
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getUId() {
        return id;
    }

    @Override
    public String toString() {
       return name;
    }
}
