package drunkcoder.com.collegebuddy;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import androidx.annotation.NonNull;

public class MaterialDialogHelper {

    private Context context;

    public MaterialDialogHelper(Context context)
    {
        this.context=context;
    }

   public  void createListDialog( String[] data) {
        new MaterialDialog.Builder(context)
                .title("Category")
                .items(data)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                        //  createDialogWithCustomView(R.layout.add_reminder);
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                })
                .show();
    }

    public void createDialogWithCustomView(int layoutId)
    {
        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(context)
                .title("Add Reminder")
                .customView(layoutId, wrapInScrollView)
                .positiveText("Add")
                .negativeText("Cancel")
                .show();
    }

    public void createInputDialog()
    {
        new MultiInputMaterialDialogBuilder(context)
                .addInput(0,R.string.title_subject)
                .addInput(0,R.string.title_faculty)
                .inputs(new MultiInputMaterialDialogBuilder.InputsCallback() {
                    @Override
                    public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                        Toast.makeText(context, "Input 1:"+inputs.get(0)+" input2:"+inputs.get(1), Toast.LENGTH_SHORT).show();
                    }
                })
                .title("Add subject")
                .positiveText("Save")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .build().show();
        }



}
