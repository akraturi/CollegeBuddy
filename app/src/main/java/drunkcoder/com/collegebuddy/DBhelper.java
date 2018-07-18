package drunkcoder.com.collegebuddy;

import android.content.Context;
import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class DBhelper {

    Context mContext;

    public DBhelper(Context context) {
        mContext = context;
    }

    public void saveObject(Model object)
    {
        object.save();
    }

    public List<?> getList(Class model)
    {
        return new Select().from(model).execute();
    }

    // returns the list of @name attribute string with the model
    public List<String> getSubjectNames()
    {
        List<String> names= new ArrayList<>();
        List<Subject> objects=new Select().from(Subject.class).execute();

        for(int i=0;i<objects.size();i++)
        {
            names.add(objects.get(i).getName());
        }
     return names;
    }


}
