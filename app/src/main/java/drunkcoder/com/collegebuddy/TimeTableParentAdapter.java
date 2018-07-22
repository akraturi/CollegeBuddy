package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import javax.xml.transform.Templates;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTableParentAdapter extends RecyclerView.Adapter<TimeTableParentAdapter.MyViewHolder> {

    Activity mContext;
    List<DayOFWeek> mDataSet;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dayTextview;
        TextView totalClassesTextView;
        RecyclerView mRecyclerView;

        MyViewHolder(View view)
        {
            super(view);
            dayTextview = view.findViewById(R.id.dayTextView);
            totalClassesTextView = view.findViewById(R.id.totalclassTextview);
            mRecyclerView = view.findViewById(R.id.horizontal_timetable_recyclerview);

        }

        public void bind(int position)
        {
            dayTextview.setText(mDataSet.get(position).getName());
            totalClassesTextView.setText("Total Classes:" +mDataSet.get(position).getTotalClasses());

        }

    }

    public TimeTableParentAdapter(Activity context,List<DayOFWeek> dataSet)
    {
        mContext=context;
        mDataSet=dataSet;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MyViewHolder(inflater.inflate(R.layout.timetable_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.bind(position);

         List<Schedule> childDataSet = mDataSet.get(position).getScheduleOfDay();
         TimeTableChildAdapter adapter = new TimeTableChildAdapter(mContext,childDataSet,this,position);
         holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
         holder.mRecyclerView.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
