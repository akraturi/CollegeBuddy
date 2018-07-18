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

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu mFloatingActionMenu;

    private FloatingActionButton addSubFloatingActionButton;
    private FloatingActionButton addReminderFloatingActionButton;
    private FloatingActionButton markAttendanceFloatingActionButton;



    private List<String> mSubjects;

    private DBhelper mDBhelper;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        //
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


        BottomNavigationView navigation =  findViewById(R.id.navigation);
        // set the default fragment
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.getMenu().getItem(0).setChecked(true));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mDBhelper = new DBhelper(this);

        mSubjects= mDBhelper.getSubjectNames();
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
                addDialogs();
                mFloatingActionMenu.close(true);
                break;
            case R.id.fab_attendance:
                attendanceDialogs();
                mFloatingActionMenu.close(true);
                break;
            case R.id.fab_reminder:
                reminderDialog();
                mFloatingActionMenu.close(true);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    public void attendanceDialogs()
    {

        new MaterialDialogHelper(this).createListDialog(new String[]{"DBMS","Computer Networks","Real Time System","Image Processing","Computer graphics","Netork security"},"Select Subject")
                .setOnListItemClickListener(new MaterialDialogHelper.OnListItemClickListener() {
                    @Override
                    public void onClick(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onLongClick(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        return false;
                    }
                });

    }
    public void addDialogs()
    {
        new MaterialDialogHelper(this).createInputDialog(null,new ArrayList<CharSequence>(){{add("Subject");add("Faculty");}},"Add")
        .setOnInputListener(new MaterialDialogHelper.OnInputListener() {
            @Override
            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                 Subject subject = new Subject();
                 subject.setName(inputs.get(0).toString());
                 subject.setFaculty(inputs.get(1).toString());
                 mDBhelper.saveObject(subject);
            }
        })
        .setmOnResponseListener(new MaterialDialogHelper.OnResponseListener() {
            @Override
            public void onPostiveResponse(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Toast.makeText(MainActivity.this, "saved!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNegativeResponse(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.edit_subjects:
                 subjectDialog();
                return true;

            case R.id.about:
                return true;

            case R.id.exit:
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    public void editOptionsDialogs()
    {
        new MaterialDialogHelper(this).createListDialog(new String[]{"Edit","Delete"},null)
                .setOnListItemClickListener(new MaterialDialogHelper.OnListItemClickListener() {
                    @Override
                    public void onClick(MaterialDialog dialog, View view, int which, CharSequence text) {
                       addDialogs();
                    }

                    @Override
                    public boolean onLongClick(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        return true;
                    }
                });
    }

    public void subjectDialog()
    {
        new MaterialDialogHelper(this).createListDialog(mSubjects.toArray(new String[mSubjects.size()]) ,"Select Subject")
                .setOnListItemClickListener(new MaterialDialogHelper.OnListItemClickListener() {
                    @Override
                    public void onClick(MaterialDialog dialog, View view, int which, CharSequence text) {
                        editOptionsDialogs();
                    }

                    @Override
                    public boolean onLongClick(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        return false;
                    }
                });
    }

    public void reminderDialog()
    {
        new MaterialDialogHelper(this).createListDialog(new String[]{"Exam", "Assignment", "others"},"Category");

    }

}
