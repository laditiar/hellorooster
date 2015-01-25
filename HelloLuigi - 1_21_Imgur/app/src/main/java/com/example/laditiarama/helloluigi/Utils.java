package com.example.laditiarama.helloluigi;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class Utils {

    public static final String USER_PROFILE = "roosterProfileData";

    public static void alert(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        // Set the Icon for the Dialog
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

    public static void alert(String message, String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        // Set the Icon for the Dialog
        // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }

}
