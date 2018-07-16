package drunkcoder.com.collegebuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu mFloatingActionMenu;

    private FloatingActionButton addSubFloatingActionButton;
    private FloatingActionButton addReminderFloatingActionButton;
    private FloatingActionButton markAttendanceFloatingActionButton;

    private MaterialDialogHelper mMaterialDialogHelper;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addFragment(OverviewFragment.newInstance());
                    return true;
                case R.id.navigation_timetable:
                    startActivity(new Intent(MainActivity.this,TimetableActivity.class));
                    return true;
                case R.id.navigation_attendance:
                    return true;
                case R.id.navigation_marks:

                    return true;
                case R.id.navigation_reminder:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFloatingActionMenu=findViewById(R.id.floatingActionButton);
        mFloatingActionMenu.setClosedOnTouchOutside(true);

        addSubFloatingActionButton=findViewById(R.id.fabAddSub);
        markAttendanceFloatingActionButton=findViewById(R.id.fab_attendance);
        addReminderFloatingActionButton=findViewById(R.id.fab_reminder);
        addSubFloatingActionButton.setOnClickListener(this);
        markAttendanceFloatingActionButton.setOnClickListener(this);
        addReminderFloatingActionButton.setOnClickListener(this);

        mMaterialDialogHelper=new MaterialDialogHelper(this);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        // set the default fragment
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0).setChecked(true));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void addFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commitNow();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fabAddSub:
                mMaterialDialogHelper.createInputDialog();
                mFloatingActionMenu.close(true);
                break;
            case R.id.fab_attendance:
                mMaterialDialogHelper.createListDialog(new String[]{"DBMS","Computer Networks","Real Time System","Image Processing","Computer graphics","Netork security"});
                mFloatingActionMenu.close(true);
                break;
            case R.id.fab_reminder:
                mMaterialDialogHelper.createListDialog(new String[]{"Exam", "Assignment", "others"});
                mFloatingActionMenu.close(true);
                break;
        }
    }


}
