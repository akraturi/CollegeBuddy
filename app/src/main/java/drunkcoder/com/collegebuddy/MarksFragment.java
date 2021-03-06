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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarksFragment extends Fragment {

    private RecyclerViewHelper mVerticalRecyclerViewHelper;
    private RecyclerViewHelper mHorizontalRecyclerViewHelper;
    private Activity mHostingActivity;
    private List<String> subjects;

    public static MarksFragment newInstance() {

        Bundle args = new Bundle();

        MarksFragment fragment = new MarksFragment();
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
        View view = inflater.inflate(R.layout.fragment_marks,container,false);

        RecyclerView recyclerView =view.findViewById(R.id.marks_fragment_recyclerview);
        mVerticalRecyclerViewHelper=new RecyclerViewHelper(mHostingActivity,
                recyclerView,
                subjects,R.layout.marks_row_item,new LinearLayoutManager(mHostingActivity),true,
                R.id.horizontal_marks_recyclerview,R.layout.marks_row_item_child);
        mVerticalRecyclerViewHelper.setUpAdapter(recyclerView);
        return view;
    }



}
