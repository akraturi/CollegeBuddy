package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SheduleTodayFragment extends Fragment {

    private RecyclerViewHelper mRecyclerViewHelper;
    private Activity mHostingActivity;
    private List<Schedule> todaysSchedule;

    public static SheduleTodayFragment newInstance() {

        Bundle args = new Bundle();

        SheduleTodayFragment fragment = new SheduleTodayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHostingActivity=getActivity();
        todaysSchedule=new ArrayList<>();
        for(int i=0;i<10;i++)
        {   Random random = new Random();
            String subjects[]={"DBMS","COMPUTER NETWORKS","REAL TIME SYSTEM","COMPUTER GRAPHICS","NETWORK SECURITY"};
            Schedule schedule = new Schedule();
            schedule.setStartTime("0"+random.nextInt(9)+"AM");
            schedule.setEndTime("0"+random.nextInt(9)+"PM");
            Subject subject = new Subject();
            subject.setName(subjects[random.nextInt(subjects.length)]);
            subject.setFaculty("");
            schedule.setSubject(subject);
            schedule.setVenue("LH-"+random.nextInt(i+10));
            todaysSchedule.add(schedule);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.schedule_today_fragment,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.schdule_fragment_recyclerview);
        setupRecyclerView(recyclerView);
        return view;
    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        RecyclerViewHelper recyclerViewHelper = new RecyclerViewHelper(mHostingActivity, recyclerView, false, new LinearLayoutManager(mHostingActivity));


        recyclerViewHelper
                .configAdapter(new RecyclerViewHelper.AdapterActivityListener() {

                    @Override
                    public DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent) {
                        View rowView = inflater.inflate(R.layout.shedule_row_item, parent, false);
                        return new DataHolder(rowView) {

                            TextView timeTextview;
                            TextView subjectTextView;
                            TextView venueTextView;

                            @Override
                            public void onInit(View view) {
                                // initialise your views here
                                timeTextview = view.findViewById(R.id.timeTextView);
                                subjectTextView = view.findViewById(R.id.subjectTextView);
                                venueTextView = view.findViewById(R.id.venueTextview);
                            }

                            @Override
                            public void bind(int position) {


                                timeTextview.setText(todaysSchedule.get(position).getStartTime() + "-" + todaysSchedule.get(position).getEndTime());
                                subjectTextView.setText(todaysSchedule.get(position).getSubject().getName());
                                venueTextView.setText("(" + todaysSchedule.get(position).getVenue() + ")");
                            }
                        };
                    }

                    @Override
                    public RecyclerViewHelper.NestedRecyclerViewHandler onBindDataHolder(int position) {

                         return null;
                    }

                })
                .withData(todaysSchedule)
                .set();
    }


}
