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

    List<DayOFWeek> weekDays;
    String dayNames[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        getSupportActionBar().hide();

        weekDays = new ArrayList<>();
        dayNames = getResources().getStringArray(R.array.week_days);
        Log.i("Daynames:", dayNames.toString());

        String subjects[] = {"DBMS", "COMPUTER NETWORKS", "REAL TIME SYSTEM", "COMPUTER GRAPHICS", "NETWORK SECURITY"};

        for (int i = 0; i < 7; i++) {
            Random random = new Random();
            DayOFWeek day = new DayOFWeek();
            day.setName(i);
            day.setTotalClasses(random.nextInt(9));
            List<Schedule> daySchedule = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Schedule schedule = new Schedule();
                schedule.setStartTime("0" + random.nextInt() + "AM");
                schedule.setEndTime("0" + random.nextInt() + "PM");
                Subject subject = new Subject();
                subject.setName(subjects[(i+j)%subjects.length]);
                subject.setFaculty("");
                schedule.setSubject(subject);
                schedule.setVenue("LH-" +i*j%10);
                daySchedule.add(schedule);


            }
            day.setScheduleOfWeek(daySchedule);

            Log.i(i+":",day.getScheduleOfWeek().get(3).getSubject().getName());
            weekDays.add(day);
        }

        for(int i=0;i<7;i++)
        {   System.out.print(dayNames[i]+"-->");
            for(int j=0;j<10;j++){
                  System.out.print(weekDays.get(i).getScheduleOfWeek().get(j).getSubject().getName()+" ");
            }

            System.out.println();
        }


        RecyclerView recyclerView = findViewById(R.id.timetable_recyclerview);

        setupRecyclerView(recyclerView);


    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        RecyclerViewHelper recyclerViewHelper = new RecyclerViewHelper(this, recyclerView, true, new LinearLayoutManager(this));

        recyclerViewHelper.configAdapter(new RecyclerViewHelper.AdapterActivityListener() {



            @Override
            public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent) {
                View rowView = inflater.inflate(R.layout.timetable_row_item, parent, false);
                return new DataHolder(rowView) {

                    TextView dayTextview;
                    TextView totalClassesTextView;

                    @Override
                    public void onInit(View view) {
                        // initialise your views here
                        dayTextview = view.findViewById(R.id.dayTextView);
                        totalClassesTextView = view.findViewById(R.id.totalclassTextview);
                    }

                    @Override
                    public void bind(int position) {
                        dayTextview.setText(dayNames[weekDays.get(position).name]);
                        totalClassesTextView.setText("Total Classes:" + weekDays.get(position).getTotalClasses());
                    }
                };
            }

            @Override
            public RecyclerViewHelper.NestedRecyclerViewHandler onBindDataHolder(int position) {
               final List<Schedule> todaysSchedule = weekDays.get(position).getScheduleOfWeek();

               Log.i("schedule_list:",Integer.toString(position));
                return new RecyclerViewHelper.NestedRecyclerViewHandler() {
                    @Override
                    public RecyclerView onSetup(View view) {
                        return view.findViewById(R.id.horizontal_timetable_recyclerview);
                    }

                    @Override
                    public void onChildConfig(RecyclerViewHelper recyclerViewHelper) {
                        recyclerViewHelper.configAdapter(new RecyclerViewHelper.AdapterActivityListener() {
                            @Override
                            public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent) {
                                View horizontalItemView = inflater.inflate(R.layout.timetable_row_item_child, parent, false);
                                return new DataHolder(horizontalItemView) {

                                    ImageView overFlowImageView;
                                    TextView timeTextview;
                                    TextView subjectTextView;
                                    TextView venueTextView;

                                    @Override
                                    public void onInit(View view) {

                                        overFlowImageView = view.findViewById(R.id.overflowImageView);
                                        timeTextview = view.findViewById(R.id.timeTextView);
                                        subjectTextView = view.findViewById(R.id.subjectTextView);
                                        venueTextView = view.findViewById(R.id.venueTextview);

                                        overFlowImageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(TimetableActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }

                                    @Override
                                    public void bind(int position) {
                                        Log.i("day schedule sub:", todaysSchedule.get(position).getSubject().getName());
                                        timeTextview.setText(todaysSchedule.get(position).getStartTime() + "-" + todaysSchedule.get(position).getEndTime());
                                        subjectTextView.setText(todaysSchedule.get(position).getSubject().getName());
                                        venueTextView.setText(todaysSchedule.get(position).getVenue());
                                    }
                                };

                            }


                            @Override
                            public RecyclerViewHelper.NestedRecyclerViewHandler onBindDataHolder(int position) {

                                return null;
                            }

                        }).withData(todaysSchedule).set();


                    }

                };
            }
        }).withData(weekDays).set();

    }

}
