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

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionMenu mFloatingActionMenu;

    private FloatingActionButton addSubFloatingActionButton;
    private FloatingActionButton addReminderFloatingActionButton;
    private FloatingActionButton markAttendanceFloatingActionButton;



    private List<Subject> mSubjects;

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
                    startActivity(new Intent(MainActivity.this,NewTimeTableActivity.class));                    return true;
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



    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubjects= Subject.getSubjects();
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
                addSubject();
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


    }
    public void editSubject(final int position)
    {
        String name = mSubjects.get(position).getName();
        new MaterialDialog.Builder(this)
                .title("Edit")
                .positiveText("Save")
                .negativeText("Cancel")
                .input(null,name, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                         reflectSubEditinDB(position,input.toString());
                        }
                }).show();


    }

    public void addSubject()
    {
        new MaterialDialog.Builder(this)
                .title("Add Subject")
                .positiveText("Save")
                .negativeText("Cancel")
                .input("Subject",null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        addSubtoDB(input.toString());
                    }
                }).show();
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

    public void editOptionsDialogs(final int index)
    {
         new MaterialDialog.Builder(this)
                 .items(new String[]{"Edit","Delete"})
                 .itemsCallback(new MaterialDialog.ListCallback() {
                     @Override
                     public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                         switch (position)
                         {
                             case 0:
                                 editSubject(index);
                                 break;
                             case 1:
                                 deleteSubFromDB(mSubjects.get(index));
                                 break;
                         }
                     }
                 }).show();
    }

    public void subjectDialog()
    {
        new MaterialDialog.Builder(this)
                .items(mSubjects)
                .title("Subjects")
                .itemsLongCallback(new MaterialDialog.ListLongCallback() {
                    @Override
                    public boolean onLongSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        editOptionsDialogs(position);
                        return true;
                    }
                })
                .show();
    }

    public void reminderDialog()
    {


    }

    public void addSubtoDB(String subjectName)
    {
        Subject subject = new Subject(subjectName);
        mSubjects.add(subject);
        subject.save();
        Toast.makeText(MainActivity.this, subjectName+": saved to DB", Toast.LENGTH_SHORT).show();
    }

    public void deleteSubFromDB(Subject subject)
    {
        mSubjects.remove(subject);
        subject.delete();
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();

    }

    public void reflectSubEditinDB(int position,String subjectName)
    {
        Subject subject = mSubjects.get(position);
        subject.setName(subjectName);
        subject.save();
        Toast.makeText(MainActivity.this, subjectName+": saved to DB", Toast.LENGTH_SHORT).show();
    }

}
