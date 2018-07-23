package drunkcoder.com.collegebuddy;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class DataHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {


    public DataHolder(View view)
    {
        super(view);
        onInit(itemView);

    }

    public abstract void onInit(View view);
    public abstract void bind(int position);

}
