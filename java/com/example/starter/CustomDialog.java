package com.example.starter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

public class CustomDialog {
    Activity mActivity;
    Dialog mDialog;

    CustomDialog(Activity activity){
        this.mActivity=activity;
        this.mDialog=new Dialog(activity);
    }

    public void showDialog(String answer){
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.custom_dialog);
        ((TextView)mDialog.findViewById(R.id.tvHint)).setText(answer);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    public void closeDialog(){
        mDialog.dismiss();
    }
}
