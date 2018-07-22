package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableChildAdapter extends RecyclerView.Adapter<TimeTableChildAdapter.MyRowViewHolder> {

    Activity mContext;
    List<Schedule> mDataSet;
    String dayNames[];
    TimeTableParentAdapter mParentAdapter;
    int selectedDay;

    // Variables are used to differentiate the static viewholders and dynamic viewholders
    public static final int STATIC=0;
    public static final int  DYNAMIC=1;


    private Button startTimeButton;
    private Button endTimeButton;
    private EditText venueEditText;
    private Spinner subjectSelectionSpinner;


    public class MyRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView overFlowImageView;
        TextView timeTextview;
        TextView subjectTextView;
        TextView venueTextView;
        ImageView addScheduleImageView;
        
        MyRowViewHolder(View view,int viewType)
        {  super(view);
            if(viewType==DYNAMIC) {
                overFlowImageView = view.findViewById(R.id.overflowImageView);
                overFlowImageView.setOnClickListener(this);
                timeTextview = view.findViewById(R.id.timeTextView);
                subjectTextView = view.findViewById(R.id.subjectTextView);
                venueTextView = view.findViewById(R.id.venueTextview);
            }
            else{
                addScheduleImageView=view.findViewById(R.id.addscheduleImageview);
                addScheduleImageView.setOnClickListener(this);
            }


        }

        public void bind(int position)
        {
            timeTextview.setText(mDataSet.get(position).getStartTime() + "-" + mDataSet.get(position).getEndTime());
            subjectTextView.setText(mDataSet.get(position).getSubject().getName());
            venueTextView.setText(mDataSet.get(position).getVenue());
        }

        @Override
        public void onClick(View view) {
        
            switch (view.getId())
            {
                case R.id.overflowImageView:
                    Toast.makeText(mContext, "overflow clicked!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.addscheduleImageview:
                    addNewScheduleDialog();
                    Toast.makeText(mContext, "add clicked!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    TimeTableChildAdapter(Activity context,List<Schedule> dataSet,TimeTableParentAdapter adapter,int selectedDay){
        mContext = context;
        mDataSet = dataSet;
        mParentAdapter = adapter;
        this.selectedDay=selectedDay;
        dayNames=mContext.getResources().getStringArray(R.array.week_days);
    }

    
    @NonNull
    @Override
    public MyRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case DYNAMIC:
                return new MyRowViewHolder(inflater.inflate(R.layout.timetable_row_item_child, parent, false),viewType);
            case STATIC:
                return new MyRowViewHolder(inflater.inflate(R.layout.add_item_image, parent, false),viewType);
        }
        return new MyRowViewHolder(inflater.inflate(R.layout.timetable_row_item_child, parent, false),viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRowViewHolder holder, int position) {
        if(getItemViewType(position)!=STATIC)
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==mDataSet.size())
        {
            return STATIC;
        }
        else
        {
            return DYNAMIC;
        }
        
    }

    private void addNewScheduleDialog() {

        View view = View.inflate(mContext,R.layout.add_schedule,null);

        setUpView(view);

        new MaterialDialog.Builder(mContext)
                .title("AddmSchedule")
                .customView(view,false)
                .positiveText("Add")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // handle all the data retrievel here
                        retrieveData();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();


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
        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext,android.R.layout.simple_list_item_1,dayNames);
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
        },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),now.get(Calendar.SECOND),false).show(mContext.getFragmentManager(),"DatePicker");

    }

    public String getTime(int hr, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hr);
        cal.set(Calendar.MINUTE,min);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(cal.getTime());
    }

    private void retrieveData() {
        Schedule mSchedule=new Schedule();
        mSchedule.setStartTime(startTimeButton.getText().toString());
        mSchedule.setEndTime(endTimeButton.getText().toString());
        mSchedule.setVenue(venueEditText.getText().toString().trim());
        Subject subject = new Subject();
        subject.setName(dayNames[subjectSelectionSpinner.getSelectedItemPosition()]);
        subject.setFaculty("None");
        mSchedule.setSubject(subject);
        DayOFWeek dayOFWeek =mParentAdapter.mDataSet.get(selectedDay);
        dayOFWeek.addToSchedule(mSchedule);
        mParentAdapter.notifyDataSetChanged();

    }



}
