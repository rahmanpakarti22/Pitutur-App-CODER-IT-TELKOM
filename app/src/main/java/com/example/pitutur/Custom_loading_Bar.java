package com.example.pitutur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Custom_loading_Bar
{
    private final Activity activity;
    private AlertDialog alertDialog;

    public Custom_loading_Bar(Activity Myact)
    {
        activity = Myact;
    }

    @SuppressLint("InflateParams")
    public void StartDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater     =  activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout, null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();
    }

    public void CloseDialog()
    {
        alertDialog.dismiss();
    }

}
