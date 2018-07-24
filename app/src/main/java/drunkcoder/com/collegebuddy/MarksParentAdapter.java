package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarksParentAdapter extends RecyclerView.Adapter<MarksParentAdapter.MarksParentViewHolder> {

    Activity mContext;
    List<Subject> mDataSet;

    public class MarksParentViewHolder extends RecyclerView.ViewHolder{

        RecyclerView mRecyclerView;
        TextView subjectTextView;
        TextView totalMarksTextView;

        MarksParentViewHolder(View view)
        {
            super(view);
            mRecyclerView = view.findViewById(R.id.horizontal_marks_recyclerview);
            subjectTextView=view.findViewById(R.id.subjectTextView);
            totalMarksTextView = view.findViewById(R.id.totalMarksTextView);

        }

        public void bind(int position)
        {
            Subject subject = mDataSet.get(position);
            subjectTextView.setText(subject.getName());
            totalMarksTextView.setText(Marks.getOverallMarksForSubject(mDataSet.get(position).getId()));

        }

    }

    public MarksParentAdapter(Activity context,List<Subject> dataSet)
    {
        mContext=context;
        mDataSet=dataSet;

    }

    @NonNull
    @Override
    public MarksParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new MarksParentViewHolder(inflater.inflate(R.layout.marks_row_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MarksParentViewHolder holder, int position) {
        holder.bind(position);
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        MarksChildAdapter adapter = new MarksChildAdapter(mContext,Marks.getMarksForSubject(mDataSet.get(position).getId()),this,mDataSet.get(position).getId());
        holder.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
