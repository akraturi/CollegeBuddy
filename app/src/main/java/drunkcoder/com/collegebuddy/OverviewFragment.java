package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * FRAGMENT DISPLAYS THE OVERVIEW OF THE USER SCHEDUAL, ATTENDANCE , MARKS  AND REMINDERS
 *
 */
public class OverviewFragment extends Fragment implements View.OnClickListener {


    private TextView mCardDetailTextView1;
    private TextView mCardDetailTextView2;
    private TextView mCardDetailTextView3;
    private TextView mCardDetailTextView4;

    private Activity hostingActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview,container,false);

        mCardDetailTextView1=view.findViewById(R.id.card_detail1);
        mCardDetailTextView2=view.findViewById(R.id.card_detail2);
        mCardDetailTextView3=view.findViewById(R.id.card_detail3);
        mCardDetailTextView4=view.findViewById(R.id.card_detail4);
        mCardDetailTextView1.setOnClickListener(this);
        mCardDetailTextView2.setOnClickListener(this);
        mCardDetailTextView3.setOnClickListener(this);
        mCardDetailTextView4.setOnClickListener(this);

        hostingActivity=getActivity();




        return view;
    }

    public static OverviewFragment newInstance()
    {
        return new OverviewFragment();
    }


    // handle click events
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.card_detail1:
                moveToDetailsActivity(0);
                break;
            case R.id.card_detail2:
                moveToDetailsActivity(1);
                break;
            case R.id.card_detail3:
                moveToDetailsActivity(2);
                break;
            case R.id.card_detail4:
                moveToDetailsActivity(3);
                break;
        }

    }

    private void moveToDetailsActivity(int category)
    {
        Intent intent = new Intent(hostingActivity,DetailsActivity.class);
        intent.putExtra("selectedCategory",category);
        startActivity(intent);
    }
}
