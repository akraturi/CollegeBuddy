package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceFragment extends Fragment {

    private Activity mHostingActivity;
    private List<Subject> subjectList;
    private RecyclerView mRecyclerView;

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance,container,false);

        mRecyclerView = view.findViewById(R.id.attendance_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mHostingActivity));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        subjectList = Subject.getSubjects();

        setUpAdapter();

    }

    private void setUpAdapter()
    {
        AttendanceAdapter adapter = new AttendanceAdapter(mHostingActivity,subjectList);
        mRecyclerView.setAdapter(adapter);
    }




}
