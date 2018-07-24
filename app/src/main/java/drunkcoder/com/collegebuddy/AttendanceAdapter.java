package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    Context mContext;
    List<Subject> mDataSet;

    AttendanceAdapter(Context context,List<Subject> dataSet)
    {
        mContext=context;
        mDataSet = dataSet;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {

           TextView subjectTextView;
           TextView attendedTextView;
           TextView totalTextView;
           TextView attendancePercentTextView;
           TextView remarkTextView;
           ImageView remarkImageView;

           AttendanceViewHolder(View view) {
            super(view);

            subjectTextView = view.findViewById(R.id.subjectTextView);
            attendedTextView = view.findViewById(R.id.attendedTextView);
            totalTextView = view.findViewById(R.id.totalTextView);
            attendancePercentTextView = view.findViewById(R.id.attendancePercentTextView);
            remarkTextView = view.findViewById(R.id.remarkTextView);
            remarkImageView = view.findViewById(R.id.remarkImageView);


           }

        public void bind(int position) {

               Subject subject = mDataSet.get(position);
               String name = subject.getName();
               int attended = subject.getAttendance().getAttendedClasses();
               int total =  subject.getAttendance().getTotalClasses();

               subjectTextView.setText(name);
               attendedTextView.setText("Attended:"+attended);
               totalTextView.setText("Total:"+total);
               float percent = ((float) attended/total)*100;
               attendancePercentTextView.setText(Float.toString(percent));

               if(percent<=75.0)
               {
                   remarkTextView.setText("You are short on attendance");

               }
               else
               {
                   remarkTextView.setText("You are on the track");
                   remarkImageView.setImageResource(R.drawable.warning);
               }


        }
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new AttendanceViewHolder(inflater.inflate(R.layout.attendance_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




}
