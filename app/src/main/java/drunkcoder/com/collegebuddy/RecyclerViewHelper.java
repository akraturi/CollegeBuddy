package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.zip.Inflater;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHelper {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private DataAdapter mDataAdapter;
    private boolean mHasNestedHorizontalRecyclerView;


    public RecyclerViewHelper(Context context, RecyclerView recyclerView, boolean hasNestedHorizontalRecyclerView, @Nullable RecyclerView.LayoutManager layoutManager)
    {
        mContext=context;
        mRecyclerView=recyclerView;
        mHasNestedHorizontalRecyclerView=hasNestedHorizontalRecyclerView;
        mRecyclerView.setLayoutManager(layoutManager);

    }

    // Adapter for recycler view

     public class DataAdapter extends RecyclerView.Adapter<DataHolder>  {

        private List<?> mDataSet;
        private AdapterActivityListener mListener;
        private NestedRecyclerViewHandler mNestedRecyclerViewHandler;

        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            return  mListener.onDataHolderCreated(inflater,parent);


        }

        @Override
        public void onBindViewHolder(@NonNull DataHolder holder, int position) {


            mNestedRecyclerViewHandler=mListener.onBindDataHolder(position);
            holder.bind(position);

            if(mHasNestedHorizontalRecyclerView)
            {
              RecyclerView recyclerView=mNestedRecyclerViewHandler.onSetup(holder.itemView);
              RecyclerViewHelper recyclerViewHelper = new RecyclerViewHelper(mContext,recyclerView,false
              ,new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
              mNestedRecyclerViewHandler.onChildConfig(recyclerViewHelper);


            }

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public DataAdapter withData(List<?> dataSet)
        {
            mDataSet = dataSet;
            return this;
        }



        public void set()
        {
            mRecyclerView.setAdapter(this);
        }

        public void setAdapterActivityListener(AdapterActivityListener adapterActivityListener)
        {
            mListener = adapterActivityListener;
        }

    }

    public DataAdapter configAdapter(AdapterActivityListener parentListener)
    {
        mDataAdapter= new DataAdapter();
        mDataAdapter.setAdapterActivityListener(parentListener);
        return mDataAdapter;
    }

    public void notifyDataSetChanged()
    {
        mDataAdapter.notifyDataSetChanged();

    }


    public interface NestedRecyclerViewHandler {

        RecyclerView onSetup(View view);
        void onChildConfig(RecyclerViewHelper recyclerViewHelper);
    }

    public interface AdapterActivityListener {


        DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent);

        @Nullable NestedRecyclerViewHandler onBindDataHolder(int position);

       // RecyclerViewHelper.NestedRecyclerViewHandler onHandleNestedRecyclerView();


    }




}