package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHelper {

    private RecyclerView mRecyclerView;
    private List<String> mdataSet;
    private Context mContext;
    private int mRowLayoutId;
    private DataAdapter mDataAdapter;


    public RecyclerViewHelper(Context context, RecyclerView recyclerView, List<String> dataSet,int rowLayoutId)
    {
        mContext=context;
        mRecyclerView=recyclerView;
        mdataSet = dataSet;
        mRowLayoutId=rowLayoutId;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }


    // ViewHolder for the recycler view which inflates our own view
    private class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView,mAuthorTextView,mDateTextView;

        public DataHolder(LayoutInflater inflater, ViewGroup container)
        {
            super(inflater.inflate(mRowLayoutId,container,false));

            itemView.setOnClickListener(this);
            // get reference to the views using the viewholder when the viewholders are created here



        }
        // The method binds the data to the viewholder
        public void bind()
        {

        }

        // implement the recycler view list item click action here
        @Override
        public void onClick(View view) {

        }

    }
    // Adapter for recycler view

    private class DataAdapter extends RecyclerView.Adapter<DataHolder>  {

        public DataAdapter()
        {

        }

        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new DataHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DataHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return 10;
        }


    }

    public void setUpAdapter()
    {
        mDataAdapter = new DataAdapter();
        mRecyclerView.setAdapter(mDataAdapter);
    }

    public void notifyUpdatedDataSet()
    {
        mDataAdapter.notifyDataSetChanged();
    }
}
