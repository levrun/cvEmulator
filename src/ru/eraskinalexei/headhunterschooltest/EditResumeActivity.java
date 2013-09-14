package ru.eraskinalexei.headhunterschooltest;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditResumeActivity extends FragmentActivity {

	public static final String FULL_NAME_KEY = "fullName";
	public static final String BIRTH_DATE_KEY = "birthDate";
	public static final String GENDER_KEY = "gender";
	public static final String POSITION_NAME_KEY = "positionName";
	public static final String SALARY_KEY = "salary";
	public static final String PHONE_KEY = "phone";
	public static final String EMAIL_KEY = "email";

	private TextView tvDisplayDate;
	private Button btnChangeDate;
	private Button btnSendResume;
	private EditText edEmail;
	private EditText edFullName;
	private TextView tvBirthDate;
	private Spinner spGenderSelector;
	private EditText edPositionName;
	private EditText edSalary;
	private EditText edPhone;
	private Intent responseIntent;

	private String fullName;

	private int year;
	private int month;
	private int day;

	private String[] genderList = new String[2];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_resume_activity);

		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

		genderList[1] = getResources().getString(R.string.man);
		genderList[0] = getResources().getString(R.string.woman);

		edFullName = (EditText) findViewById(R.id.edFullName);
		if (fullName != null) {
			edFullName.setText(fullName);
		}

		tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);
		edPositionName = (EditText) findViewById(R.id.edPositionName);
		edSalary = (EditText) findViewById(R.id.edExpectedSalary);
		edPhone = (EditText) findViewById(R.id.edPhone);
		edEmail = (EditText) findViewById(R.id.edEmail);

		setCurrentDateOnView();
		addListenerOnButton();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, genderList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spGenderSelector = (Spinner) findViewById(R.id.spBirthDate);
		spGenderSelector.setAdapter(adapter);
		spGenderSelector.setSelection(0);

		responseIntent = getIntent();
		if (responseIntent != null && responseIntent.getExtras() != null) {
			if (responseIntent.getExtras().get(
					SendResponseToApplicantActivity.RESPONSE_TEXT_KEY) != null) {
				ResponseToCandidateDialogFragment dialog = new ResponseToCandidateDialogFragment();
				dialog.setMessage(responseIntent.getExtras()
						.get(SendResponseToApplicantActivity.RESPONSE_TEXT_KEY)
						.toString());
				dialog.show(getSupportFragmentManager(), "RespondToCandidateDialog");
			}
		}

	}

	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.tvBirthDate);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		tvDisplayDate.setText(new StringBuilder().append(month + 1).append("-")
				.append(day).append("-").append(year).append(" "));

	}

	public void addListenerOnButton() {
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnChangeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialogFragment dialog = new DatePickerDialogFragment();
				dialog.setValues(datePickerListener, year, month, day);
				dialog.show(getSupportFragmentManager(), "DateChangeDialog");
			}

		});

		btnSendResume = (Button) findViewById(R.id.btnSendResume);
		btnSendResume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ("".equals(edFullName.getText().toString())
						|| edFullName.getText().toString().trim().equals("")) {
					Toast.makeText(
							EditResumeActivity.this,
							getResources()
									.getString(R.string.error_message_fio),
							Toast.LENGTH_LONG).show();
					edFullName.requestFocus();
					return;
				}

				Intent intent = new Intent(EditResumeActivity.this,
						SendResponseToApplicantActivity.class);

				intent.putExtra(FULL_NAME_KEY, edFullName.getText().toString());
				intent.putExtra(BIRTH_DATE_KEY, tvBirthDate.getText()
						.toString());
				intent.putExtra(GENDER_KEY, spGenderSelector.getSelectedItem()
						.toString());
				intent.putExtra(POSITION_NAME_KEY, edPositionName.getText()
						.toString());
				intent.putExtra(SALARY_KEY, edSalary.getText().toString());
				intent.putExtra(PHONE_KEY, edPhone.getText().toString());
				intent.putExtra(EMAIL_KEY, edEmail.getText().toString());

				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}

		});

	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			tvDisplayDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.resume_edit, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		String name = edFullName.getText().toString();
		editor.putString(FULL_NAME_KEY, name);

		String birthDate = tvDisplayDate.getText().toString();
		editor.putString(BIRTH_DATE_KEY, birthDate);

		int sex = spGenderSelector.getSelectedItemPosition();
		editor.putInt(GENDER_KEY, sex);

		String positionName = edPositionName.getText().toString();
		editor.putString(POSITION_NAME_KEY, positionName);

		String salary = edSalary.getText().toString();
		editor.putString(SALARY_KEY, salary);

		String phone = edPhone.getText().toString();
		editor.putString(PHONE_KEY, phone);

		String email = edEmail.getText().toString();
		editor.putString(EMAIL_KEY, email);

		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		edFullName.setText(preferences.getString(FULL_NAME_KEY, ""));
		tvDisplayDate.setText(preferences.getString(BIRTH_DATE_KEY, ""));
		spGenderSelector.setSelection(preferences.getInt(GENDER_KEY, 0));
		edPositionName.setText(preferences.getString(POSITION_NAME_KEY, ""));
		edSalary.setText(preferences.getString(SALARY_KEY, ""));
		edPhone.setText(preferences.getString(PHONE_KEY, ""));
		edEmail.setText(preferences.getString(EMAIL_KEY, ""));
	}

}
