package com.louanimashaun.fattyzgrill.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.louanimashaun.fattyzgrill.R;
import com.louanimashaun.fattyzgrill.data.DataSource;
import com.louanimashaun.fattyzgrill.util.PreconditonUtil;
import com.louanimashaun.fattyzgrill.util.Util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by louanimashaun on 21/12/2017.
 */

public class ETADialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private ETACompletionCallback mCompletionCallback;
    private Calendar mInitialCalendar;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        mInitialCalendar = Calendar.getInstance();
        int hour = mInitialCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mInitialCalendar.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        int beforeHour = mInitialCalendar.get(Calendar.HOUR);

        if(beforeHour == 24 && hourOfDay == 0){
            mInitialCalendar.add(Calendar.DATE, 1);
        }

        mInitialCalendar.set(Calendar.HOUR, hourOfDay);
        mInitialCalendar.set(Calendar.MINUTE, minute);


        mCompletionCallback.onComplete(mInitialCalendar);
    }

    public void setCompletionCallback(ETACompletionCallback callback){
        mCompletionCallback = PreconditonUtil.checkNotNull(callback);
    }

    public interface ETACompletionCallback {

        public void onComplete(Calendar calendar);
    }
}
