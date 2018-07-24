package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SheduleTodayFragment extends Fragment {

    private Activity mHostingActivity;
    private List<Schedule> todaysSchedule;
    private RecyclerView mRecyclerView;

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



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_today_fragment,container,false);

        Log.i("yes;","onCreateView");
        mRecyclerView= view.findViewById(R.id.schdule_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mHostingActivity));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("start:","about to enter");

        getTodaysSchedule();

        setUpAdapter();
    }

    private void getTodaysSchedule() {

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        day-=2;
        Log.i("day::",Integer.toString(day%7));
        todaysSchedule = DayOFWeek.getScheduleForDay(day%7);
    }

    private void setUpAdapter()
    {
        ScheduleTodayAdapter adapter = new ScheduleTodayAdapter(mHostingActivity,todaysSchedule);
        mRecyclerView.setAdapter(adapter);
    }


}
