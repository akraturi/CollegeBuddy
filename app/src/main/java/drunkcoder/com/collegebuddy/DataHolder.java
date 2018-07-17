package drunkcoder.com.collegebuddy;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DataHolder extends RecyclerView.ViewHolder {


    public DataHolder(View view)
    {
        super(view);
        onInit(itemView);
    }

    public abstract void onInit(View view);

}
