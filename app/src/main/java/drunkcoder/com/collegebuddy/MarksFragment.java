package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarksFragment extends Fragment {

    private Activity mHostingActivity;
    private List<Subject>  mSubjectList;
    private RecyclerView mRecyclerView;


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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marks,container,false);

        mRecyclerView = view.findViewById(R.id.marks_fragment_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mHostingActivity));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mSubjectList = Subject.getSubjects();

        MarksParentAdapter adapter = new MarksParentAdapter(mHostingActivity,mSubjectList);

        mRecyclerView.setAdapter(adapter);

    }

}
