package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleTodayAdapter extends RecyclerView.Adapter<ScheduleTodayAdapter.TodayScheduleViewHolder> {

    Context mContext;
    List<Schedule> mDataSet;

    ScheduleTodayAdapter(Context context,List<Schedule> dataSet)
    {
        mContext=context;
        mDataSet = dataSet;
    }

    public class TodayScheduleViewHolder extends RecyclerView.ViewHolder {

        TextView timeTextView;
        TextView subjectTextView;
        TextView venueTextView;

        TodayScheduleViewHolder(View view) {
            super(view);

            timeTextView = view.findViewById(R.id.timeTextView);
            subjectTextView = view.findViewById(R.id.subjectTextView);
            venueTextView = view.findViewById(R.id.venueTextview);

        }

        public void bind(int position) {
            Schedule schedule = mDataSet.get(position);
            timeTextView.setText(schedule.getStartTime()+"-"+schedule.getEndTime());
            subjectTextView.setText(schedule.getSubject().getName());
            venueTextView.setText("("+schedule.getVenue()+")");

        }
    }

    @NonNull
    @Override
    public TodayScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new TodayScheduleViewHolder(inflater.inflate(R.layout.shedule_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodayScheduleViewHolder holder, int position) {
      holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
