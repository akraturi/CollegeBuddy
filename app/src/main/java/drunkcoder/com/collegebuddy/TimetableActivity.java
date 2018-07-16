package drunkcoder.com.collegebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        getSupportActionBar().hide();

        RecyclerView recyclerView =findViewById(R.id.timetable_recyclerview);
        RecyclerViewHelper mVerticalRecyclerViewHelper=new RecyclerViewHelper(this,
                recyclerView,
                new ArrayList<String>(),R.layout.timetable_row_item,new LinearLayoutManager(this),true,
                R.id.horizontal_timetable_recyclerview,R.layout.timetable_row_item_child);
        mVerticalRecyclerViewHelper.setUpAdapter(recyclerView);
    }
}
