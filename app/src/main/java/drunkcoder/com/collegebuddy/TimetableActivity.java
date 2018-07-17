package drunkcoder.com.collegebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TimetableActivity extends AppCompatActivity {

    List<DayOFWeek>  weekDays;
    List<Schedule>   daySchedule;

     String dayNames[] = new String[7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        getSupportActionBar().hide();

        weekDays = new ArrayList<>();
        daySchedule = new ArrayList<>();
        dayNames=getResources().getStringArray(R.array.week_days);

        for(int i=0;i<7;i++)
        {   Random random = new Random();
            DayOFWeek day = new DayOFWeek();
            day.setName(i);
            day.setTotalClasses(random.nextInt(9));
            for(int j=0;j<10;j++)
            {
                String subjects[]={"DBMS","COMPUTER NETWORKS","REAL TIME SYSTEM","COMPUTER GRAPHICS","NETWORK SECURITY"};
                Schedule schedule = new Schedule();
                schedule.setStartTime("0"+random.nextInt(9)+"AM");
                schedule.setEndTime("0"+random.nextInt(9)+"PM");
                Subject subject = new Subject();
                subject.setName(subjects[random.nextInt(subjects.length)]);
                subject.setFaculty("");
                schedule.setSubject(subject);
                schedule.setVenue("LH-"+random.nextInt(i+10));
                daySchedule.add(schedule);
            }
            day.setScheduleOfWeek(daySchedule);
            weekDays.add(day);
        }



        RecyclerView recyclerView =findViewById(R.id.timetable_recyclerview);

        setupRecyclerView(recyclerView);

        
    }

    public void setupRecyclerView(RecyclerView recyclerView)
    {
        RecyclerViewHelper recyclerViewHelper = new RecyclerViewHelper(this,recyclerView,true,new LinearLayoutManager(this));

        recyclerViewHelper
                .configAdapter(new RecyclerViewHelper.AdapterActivityListener() {
                    TextView dayTextview;
                    TextView totalClassesTextView;


                    @Override
                    public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent) {
                        View rowView = inflater.inflate(R.layout.timetable_row_item, parent, false);
                        return new DataHolder(rowView) {

                            @Override
                            public void onInit(View view) {
                                // initialise your views here
                                dayTextview = view.findViewById(R.id.dayTextView);
                                totalClassesTextView = view.findViewById(R.id.totalclassTextview);
                            }
                        };
                    }

                    @Override
                    public void onBindDataHolder(int position) {
                        daySchedule=weekDays.get(position).getScheduleOfWeek();
                        dayTextview.setText(dayNames[weekDays.get(position).name]);
                        totalClassesTextView.setText("Total Classes:"+weekDays.get(position).getTotalClasses());

                    }
                }, new RecyclerViewHelper.NestedRecyclerViewHandler() {
                    @Override
                    public RecyclerView onSetup(View itemView) {
                        return itemView.findViewById(R.id.horizontal_timetable_recyclerview);
                    }

                    @Override
                    public void onChildConfig(RecyclerViewHelper recyclerViewHelper) {
                        recyclerViewHelper.configAdapter(new RecyclerViewHelper.AdapterActivityListener() {
                            ImageView overFlowImageView;
                            TextView timeTextview;
                            TextView subjectTextView;
                            TextView venueTextView;

                            @Override
                            public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent) {

                                View horizontalItemView = inflater.inflate(R.layout.timetable_row_item_child,parent,false);
                                return new DataHolder(horizontalItemView) {

                                    @Override
                                    public void onInit(View view) {
                                        overFlowImageView=view.findViewById(R.id.overflowImageView);
                                        timeTextview=view.findViewById(R.id.timeTextView);
                                        subjectTextView=view.findViewById(R.id.subjectTextView);
                                        venueTextView=view.findViewById(R.id.venueTextview);

                                        overFlowImageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(TimetableActivity.this,"Clicked!",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                };
                            }

                            @Override
                            public void onBindDataHolder(int position) {

                                timeTextview.setText(daySchedule.get(position).getStartTime()+"-"+daySchedule.get(position).getEndTime());
                                subjectTextView.setText(daySchedule.get(position).getSubject().getName());
                                venueTextView.setText("("+daySchedule.get(position).getVenue()+")");
                            }
                        },null)
                        .withData(daySchedule)
                        .set();
                    }
                })
                .withData(weekDays)
                .set();
    }
}
