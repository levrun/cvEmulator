package ru.eraskinalexei.headhunterschooltest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment implements OnClickListener {
	
	private DatePickerDialog.OnDateSetListener datePickerListener;
	private int year;
	private int month;
	private int day;
	
	public void setValues(DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
		this.datePickerListener = listener;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
	}

	public void onClick(DialogInterface dialog, int which) {
		getDialog().dismiss();
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}
}
