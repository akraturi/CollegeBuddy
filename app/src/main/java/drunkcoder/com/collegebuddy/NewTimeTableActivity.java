package drunkcoder.com.collegebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NewTimeTableActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TimeTableParentAdapter mAdapter;
    private List<DayOFWeek> weekDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_table);

        getSchedule();

        setUpRecyclerView();

    }

    private void getSchedule() {
       String dayNames[] = getResources().getStringArray(R.array.week_days);
        weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String dayName= dayNames[i];
            List<Schedule> daySchedule=DayOFWeek.getScheduleForDay(i);
            int totalClasses = 4;

            DayOFWeek day = new DayOFWeek();
            day.setName(dayName);
            day.setTotalClasses(totalClasses);
            day.setScheduleOfDay(daySchedule);
            weekDays.add(day);
        }
    }

    private void setUpRecyclerView()
    {
        mRecyclerView = findViewById(R.id.timetable_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TimeTableParentAdapter(this,weekDays);
        mRecyclerView.setAdapter(mAdapter);
    }

}
