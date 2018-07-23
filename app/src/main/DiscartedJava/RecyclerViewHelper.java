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
    private boolean mHasNestedHorizontalRecyclerView; // variable to indicated that if the adapter has nested recyclerview

    // Variables are used to differentiate the static viewholders and dynamic viewholders
    public static final int STATIC=0;
    public static final int  DYNAMIC=1;


    public RecyclerViewHelper(Context context, RecyclerView recyclerView, boolean hasNestedHorizontalRecyclerView, @Nullable RecyclerView.LayoutManager layoutManager)
    {
        mContext=context;
        mRecyclerView=recyclerView;
        mRecyclerView.setNestedScrollingEnabled(false);
        mHasNestedHorizontalRecyclerView=hasNestedHorizontalRecyclerView;
        mRecyclerView.setLayoutManager(layoutManager);

    }

    // Adapter for recycler view

     public class DataAdapter extends RecyclerView.Adapter<DataHolder>  {

        private List<?> mDataSet;
        private AdapterActivityListener mListener; // provides the interface to implement the native Adapter methods to caller
        private NestedRecyclerViewHandler mNestedRecyclerViewHandler; // provides the interface to handle the nested recyclerview


        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(mContext);

            return  mListener.onDataHolderCreated(inflater,parent,viewType);

        }

        @Override
        public void onBindViewHolder(@NonNull DataHolder holder, int position) {

            mNestedRecyclerViewHandler=mListener.onBindDataHolder(holder,position);

            // configure the nested recyclerview if it has any
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
            return mListener.getItemCount();
        }

        public DataAdapter withData(List<?> dataSet)
        {
            mDataSet = dataSet;
            return this;
        }



        public DataAdapter set()
        {
            mRecyclerView.setAdapter(this);
            return this;
        }

        public void setAdapterActivityListener(AdapterActivityListener adapterActivityListener)
        {
            mListener = adapterActivityListener;
        }

        // setup the difference between the static and dynamic viewholder
         @Override
         public int getItemViewType(int position) {

                if(position==mDataSet.size()-1)
                {
                    return STATIC;
                }
                else
                {
                    return DYNAMIC;
                }
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

    // interface handlers the nested recyclerview
    public interface NestedRecyclerViewHandler {

        RecyclerView onSetup(View view);
        void onChildConfig(RecyclerViewHelper recyclerViewHelper);
    }

    // interface provides the mechanism to implement native adapter methods
    public interface AdapterActivityListener {


        DataHolder onDataHolderCreated(LayoutInflater inflater, ViewGroup parent,int viewType);

        @Nullable NestedRecyclerViewHandler onBindDataHolder(DataHolder holder,int position);

        int getItemCount();

       // RecyclerViewHelper.NestedRecyclerViewHandler onHandleNestedRecyclerView();


    }

}