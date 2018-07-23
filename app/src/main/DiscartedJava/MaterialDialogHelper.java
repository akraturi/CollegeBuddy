package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaterialDialogHelper {

    private Context mContext;
    private OnListItemClickListener mListItemClickListener;
    private OnInputListener mInputListener;
    private OnResponseListener mOnResponseListener;


    public MaterialDialogHelper(Context context)
    {
        mContext=context;
    }

    // Creating a list dialog

    public MaterialDialogHelper createListDialog(String[] data, @Nullable String title) {
                 new MaterialDialog.Builder(mContext)
                .title(title)
                .items(data)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if(mListItemClickListener!=null)
                        mListItemClickListener.onClick(dialog,view,which,text);

                        }
                    }).itemsLongCallback(new MaterialDialog.ListLongCallback() {
                     @Override
                     public boolean onLongSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                      if(mListItemClickListener!=null)
                      return  mListItemClickListener.onLongClick(dialog,itemView,position,text);

                      return true;
                     }
                 }).show();
                return this;
    }


        public interface OnListItemClickListener
        {
            void onClick(MaterialDialog dialog, View view, int which, CharSequence text);
            boolean onLongClick(MaterialDialog dialog, View itemView, int position, CharSequence text);
        }

        public MaterialDialogHelper setOnListItemClickListener(OnListItemClickListener listener)
        {
            mListItemClickListener=listener;
            return this;
        }

        public interface OnInputListener
        {
            void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated);

        }

        public MaterialDialogHelper setOnInputListener(OnInputListener listener)
        {
            mInputListener=listener;

            return this;
        }

        public interface OnResponseListener
        {
            void onPostiveResponse(@NonNull MaterialDialog dialog, @NonNull DialogAction which);
            void onNegativeResponse(@NonNull MaterialDialog dialog, @NonNull DialogAction which);
        }

        public MaterialDialogHelper setmOnResponseListener(OnResponseListener listener)
        {
            mOnResponseListener = listener;
            return this;
        }

}
