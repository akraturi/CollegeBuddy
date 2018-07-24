package drunkcoder.com.collegebuddy;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarksChildAdapter extends RecyclerView.Adapter<MarksChildAdapter.MarksChildViewHolder> {

    Activity mContext;
    List<Marks> mDataSet;
    MarksParentAdapter mParentAdapter;
    long selectedSubject;
    RecyclerView mRecyclerView;

    EditText examNameEditText;
    EditText scoreEditText;
    EditText totalEditText;



    // Variables are used to differentiate the static viewholders and dynamic viewholders
    public static final int STATIC=0;
    public static final int  DYNAMIC=1;

    private int mCurrentPostion;


    public class MarksChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView overFlowImageView;
        ImageView addExamImageView;
        TextView scoreTextView;
        TextView totalTextView;
        TextView examTextView;


        MarksChildViewHolder(View view,int viewType)
        {  super(view);
            if(viewType==DYNAMIC) {
                overFlowImageView = view.findViewById(R.id.overflowImageView);
                examTextView = view.findViewById(R.id.examTextView);
                scoreTextView = view.findViewById(R.id.scoreTextView);
                totalTextView = view.findViewById(R.id.totalTextView);
                overFlowImageView.setOnClickListener(this);
            }
            else{
                addExamImageView=view.findViewById(R.id.addscheduleImageview);
                addExamImageView.setOnClickListener(this);
            }



        }

        public void bind(int position)
        {
            Marks marks = mDataSet.get(position);
           examTextView.setText(marks.getExamName());
           scoreTextView.setText("Score:"+marks.getScoredMarks());
           totalTextView.setText("Total:"+marks.getTotalMarks());
        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.overflowImageView:
                    Toast.makeText(mContext, "overflow clicked!", Toast.LENGTH_SHORT).show();
                    showMarksSelectedOptions();
                    break;
                case R.id.addscheduleImageview:
                    Toast.makeText(mContext, "add clicked!", Toast.LENGTH_SHORT).show();
                    addNewMarksDialog();
                    break;
            }
        }
    }

    MarksChildAdapter(Activity context,List<Marks> dataSet,MarksParentAdapter adapter,long selectedSubject){
        mContext = context;
        mDataSet = dataSet;
        mParentAdapter = adapter;
        this.selectedSubject = selectedSubject;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE)
                {
                    mCurrentPostion= ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    Log.i("position:",Integer.toString(mCurrentPostion));
                }

            }
        });
    }

    @NonNull
    @Override
    public MarksChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case DYNAMIC:
                return new MarksChildViewHolder(inflater.inflate(R.layout.marks_row_item_child, parent, false),viewType);
            case STATIC:
                return new MarksChildViewHolder(inflater.inflate(R.layout.add_item_image, parent, false),viewType);
        }
        return new MarksChildViewHolder(inflater.inflate(R.layout.marks_row_item_child, parent, false),viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull MarksChildViewHolder holder, int position) {
        if(getItemViewType(position)!=STATIC)
            holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==mDataSet.size())
        {
            return STATIC;
        }
        else
        {
            return DYNAMIC;
        }

    }

    private void addNewMarksDialog() {

        View view = View.inflate(mContext,R.layout.add_marks,null);

        setUpView(view);

        new MaterialDialog.Builder(mContext)
                .title("Add Exam")
                .customView(view,false)
                .positiveText("Add")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // handle all the data retrievel here
                        retrieveData();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();


    }

    private void setUpView(View view) {

        examNameEditText = view.findViewById(R.id.examNameEditText);
        scoreEditText = view.findViewById(R.id.scoreEditText);
        totalEditText = view.findViewById(R.id.totalEditText);

    }

    private void retrieveData()
    {
        Marks marks = new Marks();
        String name = examNameEditText.getText().toString().trim();
        float score = Float.parseFloat(scoreEditText.getText().toString());
        float total = Float.parseFloat(totalEditText.getText().toString());
        marks.setExamName(name);
        marks.setScoredMarks(score);
        marks.setTotalMarks(total);
        marks.setSubjectId(selectedSubject);

        mDataSet.add(marks);
        notifyDataSetChanged();
        notifyItemInserted(mCurrentPostion);

        // save object to db
        marks.save();

        // update overall marks
        mParentAdapter.notifyDataSetChanged();

        Toast.makeText(mContext, "saved!", Toast.LENGTH_SHORT).show();


    }

    private void showMarksSelectedOptions()
    {
        new MaterialDialog.Builder(mContext)
                .items(new String[]{"Edit","Delete"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if(position==0) // edit
                        {
                            editMarksDialog();
                        }
                        else // delete
                        {
                            deleteMarks();

                        }
                    }
                })
                .show();
    }

    private void editMarksDialog()
    {
        View view = View.inflate(mContext,R.layout.add_marks,null);

        setUpEditView(view);

        new MaterialDialog.Builder(mContext)
                .title("Edit Exam")
                .customView(view,false)
                .positiveText("Save")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // handle all the data retrievel here
                       editMarks();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();


    }

    private  void setUpEditView(View view)
    {
        examNameEditText = view.findViewById(R.id.examNameEditText);
        scoreEditText = view.findViewById(R.id.scoreEditText);
        totalEditText = view.findViewById(R.id.totalEditText);

        String examName= mDataSet.get(mCurrentPostion).getExamName();
        String scoredMarks=Float.toString(mDataSet.get(mCurrentPostion).getScoredMarks());
        String totalMarks = Float.toString(mDataSet.get(mCurrentPostion).getTotalMarks());

        examNameEditText.setText(examName);
        examNameEditText.setSelection(examName.length());
        scoreEditText.setText(scoredMarks);
        scoreEditText.setSelection(scoredMarks.length());
        totalEditText.setText(totalMarks);
        totalEditText.setSelection(totalMarks.length());

    }

    private void editMarks()
    {
       Marks marks = mDataSet.get(mCurrentPostion);
       marks.setExamName(examNameEditText.getText().toString());
       marks.setScoredMarks(Float.parseFloat(scoreEditText.getText().toString()));
       marks.setTotalMarks(Float.parseFloat(totalEditText.getText().toString()));

       mDataSet.set(mCurrentPostion,marks);
       notifyItemInserted(mCurrentPostion);
       notifyDataSetChanged();

       marks.save();

       mParentAdapter.notifyDataSetChanged();
    }
    private void deleteMarks()
    {
        Marks marks =mDataSet.get(mCurrentPostion);
        mDataSet.remove(marks);
        notifyItemRemoved(mCurrentPostion);
        notifyItemRangeChanged(mCurrentPostion,mDataSet.size());
        marks.delete();
        Toast.makeText(mContext, "deleted!", Toast.LENGTH_SHORT).show();
    }

}
