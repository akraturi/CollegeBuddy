package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        RecyclerView recyclerView = view.findViewById(R.id.marks_fragment_recyclerview);


        return view;
    }





}
