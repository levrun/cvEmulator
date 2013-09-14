package ru.eraskinalexei.headhunterschooltest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface.OnClickListener;


public class ResponseToCandidateDialogFragment extends DialogFragment implements OnClickListener {
		
		private String message;
		
		public void setMessage(String message) {
			this.message = message;
		}
	
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
					.setTitle(R.string.response_from_employer_activity1).setPositiveButton(R.string.yes, this)
					.setMessage(message).setIcon(R.drawable.ic_launcher);
			
			return adb.create();
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
