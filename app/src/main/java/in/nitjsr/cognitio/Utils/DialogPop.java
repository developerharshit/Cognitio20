package in.nitjsr.cognitio.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import in.nitjsr.cognitio.R;

public class DialogPop extends DialogFragment {

    private Context context;
    private CollegeName collegeName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        context = getActivity();
        collegeName = (CollegeName) getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        switch (getTag()) {
            case ("college"):
                builder.setItems(new String[]{"NIT Jamshedpur", "School", "Other College"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            collegeName.getCollege("NIT Jamshedpur", "0");
                        } else if (which == 1) {
                            final AlertDialog dialog1 = new AlertDialog.Builder(context)
                                    .setTitle("Enter School Name")
                                    .setCancelable(false)
                                    .setView(R.layout.view_college_dialog)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .create();

                            dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(final DialogInterface dialog) {
                                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                    button.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            EditText text = dialog1.findViewById(R.id.text);
                                            String college = text.getText().toString();
                                            if (!text.getText().toString().isEmpty()) {
                                                collegeName.getCollege(text.getText().toString(), "1");
                                                dialog.dismiss();
                                            } else {
                                                text.setError("Enter College Name");
                                            }
                                        }
                                    });
                                }
                            });
                            dialog1.show();
                        } else if (which == 2) {
                            final AlertDialog dialog1 = new AlertDialog.Builder(context)
                                    .setTitle("Enter College Name")
                                    .setCancelable(false)
                                    .setView(R.layout.view_college_dialog)
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .create();

                            dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(final DialogInterface dialog) {
                                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                    button.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            EditText text = dialog1.findViewById(R.id.text);
                                            String college = text.getText().toString();
                                            if (!text.getText().toString().isEmpty()) {
                                                collegeName.getCollege(text.getText().toString(), "2");
                                                dialog.dismiss();
                                            } else {
                                                text.setError("Enter College Name");
                                            }
                                        }
                                    });
                                }
                            });
                            dialog1.show();
                        }
                    }
                });
                break;
        }

        return builder.create();
    }

    public interface CollegeName {
        void getCollege(String college, String college_code);
    }
}
