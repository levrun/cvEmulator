package ru.eraskinalexei.headhunterschooltest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendResponseToApplicantActivity extends Activity {

	public static final String RESPONSE_TEXT_KEY = "response";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_response_to_applicant_activity);
		
		Bundle bundle = getIntent().getExtras();

		final TextView tvFullName = (TextView) findViewById(R.id.tvFullName);
		tvFullName.setText(bundle.getString(EditResumeActivity.FULL_NAME_KEY));

		TextView tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);
		tvBirthDate.setText(bundle.getString(EditResumeActivity.BIRTH_DATE_KEY));

		TextView tvSex = (TextView) findViewById(R.id.tvSex);
		tvSex.setText(bundle.getString(EditResumeActivity.SEX_KEY));

		TextView tvPositionName = (TextView) findViewById(R.id.tvPositionName);
		tvPositionName.setText(bundle
				.getString(EditResumeActivity.POSITION_NAME_KEY));

		TextView tvSalary = (TextView) findViewById(R.id.tvSalary);
		tvSalary.setText(bundle.getString(EditResumeActivity.SALARY_KEY));

		final TextView tvPhone = (TextView) findViewById(R.id.tvPhone);
		tvPhone.setText(bundle.getString(EditResumeActivity.PHONE_KEY));
		tvPhone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:+"
						+ tvPhone.getText().toString().trim()));
				startActivity(callIntent);
			}
		});

		final TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
		tvEmail.setText(bundle.getString(EditResumeActivity.EMAIL_KEY));

		tvEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sendToIntent = new Intent(
						Intent.ACTION_SENDTO,
						Uri.parse("mailto:"
								+ tvEmail.getText().toString().trim()
								+ "?subject="
								+ Uri.encode(getResources().getString(R.string.email_to_candidate_subject))
								+ "&body="
								+ Uri.encode(getResources().getString(R.string.email_to_candidate_hello) 
								+ tvFullName.getText().toString() 
								+ getResources().getString(R.string.email_to_candidate_text))));
				startActivity(sendToIntent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		
		final EditText responseText = (EditText)findViewById(R.id.edResponse);
		responseText.setText(getResources().getString(R.string.message_to_candidate_text2_1) + " " + tvFullName.getText().toString() + getResources().getString(R.string.message_to_candidate_text2_2));
		
		Button sendResponse = (Button)findViewById(R.id.btnSendResponse);
		sendResponse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SendResponseToApplicantActivity.this, EditResumeActivity.class);
				intent.putExtra(RESPONSE_TEXT_KEY, responseText.getText().toString());
				
				startActivity(intent);
			}
		});

	}
}
