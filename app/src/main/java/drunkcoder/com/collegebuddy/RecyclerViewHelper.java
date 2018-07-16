package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHelper {

    private RecyclerView mRecyclerView;
    private List<String> mdataSet;
    private Context mContext;
    private int mRowLayoutId;
    private boolean hasScrollableRowItem;

    private int childRecyclerViewId;
    private int itemLayoutId;

    private DataAdapter parentDataAdapter;
    private DataAdapter childDataAdapter;


    public RecyclerViewHelper(Context context, RecyclerView recyclerView, List<String> dataSet, int rowLayoutId, RecyclerView.LayoutManager layoutManager, boolean hasScrollableRowItem)
    {
        mContext=context;
        mRecyclerView=recyclerView;
        mdataSet = dataSet;
        mRowLayoutId=rowLayoutId;
        this.hasScrollableRowItem=hasScrollableRowItem;
        mRecyclerView.setLayoutManager(layoutManager);


    }

    public RecyclerViewHelper(Context context, RecyclerView recyclerView, List<String> dataSet, int rowLayoutId, RecyclerView.LayoutManager layoutManager, boolean hasScrollableRowItem,int childRecyclerViewId,int itemLayoutId)
    {
        mContext=context;
        mRecyclerView=recyclerView;
        mdataSet = dataSet;
        mRowLayoutId=rowLayoutId;
        this.hasScrollableRowItem=hasScrollableRowItem;
        this.childRecyclerViewId=childRecyclerViewId;
        this.itemLayoutId=itemLayoutId;
        mRecyclerView.setLayoutManager(layoutManager);


    }

    // ViewHolder for the recycler view which inflates our own view
    private class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerView childRecyclerView;

        public DataHolder(LayoutInflater inflater, ViewGroup container)
        {
            super(inflater.inflate(mRowLayoutId,container,false));

            itemView.setOnClickListener(this);
            // get reference to the views using the viewholder when the viewholders are created here

            if(hasScrollableRowItem)
            {

                childRecyclerView = itemView.findViewById(childRecyclerViewId);
            }



        }
        // The method binds the data to the viewholder
        public void bind()
        {
            if(hasScrollableRowItem)
            {
                RecyclerViewHelper mHorizontalRecyclerViewHelper=new RecyclerViewHelper(mContext,
                        childRecyclerView,
                        mdataSet,itemLayoutId,new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false),false);

                Log.i("again","wtih rv:"+childRecyclerView.toString());
                mHorizontalRecyclerViewHelper.setUpChildDataAdapter(childRecyclerView);
            }
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

            holder.bind();
        }

        @Override
        public int getItemCount() {
            return 10;
        }


    }

    public void setUpAdapter(RecyclerView recyclerView)
    {
        parentDataAdapter = new DataAdapter();
        recyclerView.setAdapter(parentDataAdapter);
    }

    public void setUpChildDataAdapter(RecyclerView recyclerView)
    {
        childDataAdapter = new DataAdapter();
        recyclerView.setAdapter(childDataAdapter);
    }


    public void notifyUpdatedDataSet()
    {
        parentDataAdapter.notifyDataSetChanged();
    }

    public void notifyUpdatedDataSetChild()
    {
        childDataAdapter.notifyDataSetChanged();
    }


}