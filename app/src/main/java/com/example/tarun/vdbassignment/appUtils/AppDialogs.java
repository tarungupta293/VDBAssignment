package com.example.tarun.vdbassignment.appUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Tarun on 1/28/2019.
 */

public class AppDialogs {

    public static void showAlertDialog(Context context, String title, String message, boolean isShowCancelButton, final AppListeners.DialogCallback dialogCallback){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogCallback!=null)
                    dialogCallback.onClickPositiveButton();
            }
        });
        if (isShowCancelButton){
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (dialogCallback!=null)
                        dialogCallback.onClickNegativeButton();
                }
            });
        }
        alertDialogBuilder.show();
    }
}
