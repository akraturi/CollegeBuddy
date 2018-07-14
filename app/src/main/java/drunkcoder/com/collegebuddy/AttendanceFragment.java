package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceFragment extends Fragment {

    private RecyclerViewHelper mRecyclerViewHelper;
    private Activity mHostingActivity;
    private List<String> subjects;

    public static AttendanceFragment newInstance() {

        Bundle args = new Bundle();

        AttendanceFragment fragment = new AttendanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHostingActivity=getActivity();
        subjects = new ArrayList<>();
        subjects.add("Real Time System");
        subjects.add("Computer Networks");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance,container,false);
        mRecyclerViewHelper=new RecyclerViewHelper(mHostingActivity,(RecyclerView)view.findViewById(R.id.attendance_fragment_recyclerview),subjects,R.layout.attendance_row_item);
        mRecyclerViewHelper.setUpAdapter();
        return view;
    }

}
