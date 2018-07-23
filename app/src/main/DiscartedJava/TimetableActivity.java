package drunkcoder.com.collegebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class TimetableActivity extends AppCompatActivity {

    private List<DayOFWeek> weekDays;
    private List<String> subjectNames;
    private String dayNames[];

    private Button startTimeButton;
    private Button endTimeButton;
    private EditText venueEditText;
    private Spinner subjectSelectionSpinner;

    private RecyclerViewHelper parentRecyclerViewHelper;
    private RecyclerViewHelper childRecyclerViewHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        getSupportActionBar().hide();

        getSchedule();


        RecyclerView recyclerView = findViewById(R.id.timetable_recyclerview);
        setupRecyclerView(recyclerView);



    }

    private void getSchedule() {
        dayNames = getResources().getStringArray(R.array.week_days);
        weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String dayName= dayNames[i];
            List<Schedule> daySchedule;
            int totalClasses = 4;

            DayOFWeek day = new DayOFWeek();
            day.setName(dayName);
            day.setTotalClasses(totalClasses);
            day.setScheduleOfDay(new ArrayList<Schedule>());
            weekDays.add(day);
        }
    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        parentRecyclerViewHelper = new RecyclerViewHelper(this, recyclerView, true, new LinearLayoutManager(this));

        parentRecyclerViewHelper.configAdapter(new RecyclerViewHelper.AdapterActivityListener() {



            @Override
            public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent,int viewType) {

                View rowView;
                rowView= inflater.inflate(R.layout.timetable_row_item, parent, false);


                DataHolder dataHolder= new DataHolder(rowView) {

                    @Override
                    public void onClick(View view) {

                    }

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
                        dayTextview.setText(weekDays.get(position).getName());
                        totalClassesTextView.setText("Total Classes:" + weekDays.get(position).getTotalClasses());
                    }
                };
                return dataHolder;
            }

            @Override
            public RecyclerViewHelper.NestedRecyclerViewHandler onBindDataHolder(DataHolder holder, final int position) {

                holder.bind(position);

               final List<Schedule>  todaysSchedule= weekDays.get(position).getScheduleOfDay();
               todaysSchedule.add(new Schedule());

               Log.i("schedule_list:",Integer.toString(position));
                return new RecyclerViewHelper.NestedRecyclerViewHandler() {
                    @Override
                    public RecyclerView onSetup(View view) {
                        return view.findViewById(R.id.horizontal_timetable_recyclerview);
                    }

                    @Override
                    public void onChildConfig(RecyclerViewHelper recyclerViewHelper) {
                        childRecyclerViewHelper=recyclerViewHelper;
                        recyclerViewHelper.configAdapter(new RecyclerViewHelper.AdapterActivityListener() {
                            @Override
                            public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent,int viewType) {

                                View horizontalItemView;
                                DataHolder dataHolder;
                                if(viewType==RecyclerViewHelper.STATIC) {
                                    horizontalItemView = inflater.inflate(R.layout.add_item_image, parent, false);
                                    dataHolder= new DataHolder(horizontalItemView) {
                                        @Override
                                        public void onClick(View view) {

                                            addNewScheduleDialog(position);

                                        }

                                        @Override
                                        public void onInit(View view) {

                                            view.setOnClickListener(this);

                                        }

                                        @Override
                                        public void bind(int position) {

                                        }
                                    };

                                    return  dataHolder;

                                }
                                else {
                                    Log.i("schdule:",todaysSchedule.toString());
                                    horizontalItemView = inflater.inflate(R.layout.timetable_row_item_child, parent, false);
                                    dataHolder= new DataHolder(horizontalItemView) {

                                        @Override
                                        public void onClick(View view) {

                                        }

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
                                            try {
                                                Log.i("daymSchedule sub:", todaysSchedule.get(position).getSubject().getName());

                                            }catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }

                                        }
                                    };

                                    return  dataHolder;

                                }

                            }


                            @Override
                            public RecyclerViewHelper.NestedRecyclerViewHandler onBindDataHolder(DataHolder holder,int position) {
                                Log.i("pos:",Integer.toString(position));
                                holder.bind(position);
                                return null;

                            }

                            @Override
                            public int getItemCount() {
                                return todaysSchedule.size();
                            }

                        }).withData(todaysSchedule).set();


                    }

                };
            }

            @Override
            public int getItemCount() {
                return weekDays.size();
            }
        }).withData(weekDays).set();

    }

    private void addNewScheduleDialog(final int position) {

        View view = View.inflate(this,R.layout.add_schedule,null);

        setUpView(view);

        new MaterialDialog.Builder(this)
                .title("AddmSchedule")
                .customView(view,false)
                .positiveText("Add")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // handle all the data retrievel here
                        retrieveData(position);

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();


        }

    private void retrieveData(int selectedDayOfWeek) {
//       Schedule mSchedule=new Schedule();
//        mSchedule.setStartTime(startTimeButton.getText().toString());
//        mSchedule.setEndTime(endTimeButton.getText().toString());
//        mSchedule.setVenue(venueEditText.getText().toString().trim());
//        Subject subject = new Subject();
//        subject.setName(dayNames[subjectSelectionSpinner.getSelectedItemPosition()]);
//        subject.setFaculty("None");
//        mSchedule.setSubject(subject);
//        DayOFWeek dayOFWeek = weekDays.get(selectedDayOfWeek);
//        dayOFWeek.addToSchedule(mSchedule);
//        childRecyclerViewHelper.notifyDataSetChanged();

    }

    private void setUpView(View view) {

       startTimeButton = view.findViewById(R.id.startTimeButton);
       endTimeButton = view.findViewById(R.id.endTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Log.i("button:","clicked");
             createTimePickerDialog(true);
            }
        });
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTimePickerDialog(false);
            }
        });

        subjectSelectionSpinner = view.findViewById(R.id.subjectChoiceSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,dayNames);
        subjectSelectionSpinner.setAdapter(arrayAdapter);

        venueEditText = view.findViewById(R.id.venueEditText);


    }

    private void createTimePickerDialog(final boolean isStartTime) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                String time = getTime(hourOfDay,minute);
                if(isStartTime)
                {
                    startTimeButton.setText(time);
                }
                else
                {   endTimeButton.setText(time);
                }
            }
        },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),false).show(getFragmentManager(),"DatePicker");

    }

    public String getTime(int hr, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hr);
        cal.set(Calendar.MINUTE,min);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(cal.getTime());
    }

}
