package drunkcoder.com.collegebuddy;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.ImageView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // up button
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         headerImageView=findViewById(R.id.appbar_header_image);
         int selectedCategory=getIntent().getIntExtra("selectedCategory",0);
         setHeaderImage(selectedCategory);

         addFragment(getTobeHostedFragment(selectedCategory));


        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    private void addFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_container,fragment);
        fragmentTransaction.commitNow();
    }

    private void setHeaderImage(int selectedCategory)
    {
        switch (selectedCategory)
        {
            case 0:
                headerImageView.setImageResource(R.drawable.schedule2);
                break;
            case 1:
                headerImageView.setImageResource(R.drawable.attendence);
                break;
            case 2:
                headerImageView.setImageResource(R.drawable.marks);
                break;
            case 3:
                headerImageView.setImageResource(R.drawable.reminder);
                break;
        }
    }

    private Fragment getTobeHostedFragment(int category)
    {
        switch (category)
        {
            case 0:
                return SheduleTodayFragment.newInstance();
            case 1:
                return AttendanceFragment.newInstance();
            case 2:
                return OverviewFragment.newInstance();
            case 3:
                return OverviewFragment.newInstance();
        }
        return null;
    }
}
