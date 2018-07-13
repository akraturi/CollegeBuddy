package drunkcoder.com.collegebuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FRAGMENT DISPLAYS THE OVERVIEW OF THE USER SCHEDUAL, ATTENDANCE , MARKS  AND REMINDERS
 *
 */
public class OverviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview,container,false);

        return view;
    }

    public static OverviewFragment newInstance()
    {
        return new OverviewFragment();
    }
}
