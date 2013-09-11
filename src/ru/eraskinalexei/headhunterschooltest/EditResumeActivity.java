package ru.eraskinalexei.headhunterschooltest;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditResumeActivity extends Activity {

	public static final String FULL_NAME_KEY = "fullName";
	public static final String BIRTH_DATE_KEY = "birthDate";
	public static final String SEX_KEY = "sex";
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
	private Spinner spSexSelector;
	private EditText edPositionName;
	private EditText edSalary;
	private EditText edPhone;
	private Intent responseIntent;
	
	private String fullName;
	
	private int year;
	private int month;
	private int day;

	private static final int DATE_DIALOG_ID = 999;
	private static final int RESPONSE_DIALOG_ID = 998;
	
	private String[] genderList = new String[2];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_resume_activity);
		
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		genderList[1] = getResources().getString(R.string.man);
		genderList[0] = getResources().getString(R.string.woman);
		
		edFullName = (EditText) findViewById(R.id.edFullName);
		if(fullName != null) {
			edFullName.setText(fullName);
		}
		
		tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);
		edPositionName = (EditText)findViewById(R.id.edPositionName);
		edSalary = (EditText)findViewById(R.id.edExpectedSalary);
		edPhone = (EditText)findViewById(R.id.edPhone);
		edEmail = (EditText) findViewById(R.id.edEmail);
		
		setCurrentDateOnView();
		addListenerOnButton();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, genderList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spSexSelector = (Spinner) findViewById(R.id.spBirthDate);
		spSexSelector.setAdapter(adapter);
		spSexSelector.setSelection(0);
		
		responseIntent = getIntent();
		if(responseIntent != null && responseIntent.getExtras() != null) {
			if(responseIntent.getExtras().get(SendResponseToApplicantActivity.RESPONSE_TEXT_KEY) != null) {
				showDialog(RESPONSE_DIALOG_ID);
			}
		}
		
		
	}

	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.tvBirthDate);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		tvDisplayDate.setText(new StringBuilder()
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

	}

	public void addListenerOnButton() {
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnChangeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}

		});

		btnSendResume = (Button) findViewById(R.id.btnSendResume);
		btnSendResume.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditResumeActivity.this,
						SendResponseToApplicantActivity.class);
				
				intent.putExtra(FULL_NAME_KEY, edFullName.getText().toString());
				intent.putExtra(BIRTH_DATE_KEY, tvBirthDate.getText().toString());
				intent.putExtra(SEX_KEY, spSexSelector.getSelectedItem().toString());
				intent.putExtra(POSITION_NAME_KEY, edPositionName.getText().toString());
				intent.putExtra(SALARY_KEY, edSalary.getText().toString());
				intent.putExtra(PHONE_KEY, edPhone.getText().toString());
				intent.putExtra(EMAIL_KEY, edEmail.getText().toString());
				
				startActivity(intent);
			}

		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		case RESPONSE_DIALOG_ID:
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
	        adb.setTitle(getResources().getString(R.string.response_from_employer_activity1));
	        adb.setMessage(responseIntent.getExtras().get(SendResponseToApplicantActivity.RESPONSE_TEXT_KEY).toString());
	        adb.setIcon(android.R.drawable.ic_dialog_info);
	        adb.setPositiveButton(getResources().getString(android.R.string.ok), null);
	        return adb.create();
		}
		
		return null;
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
	  
	  int sex = spSexSelector.getSelectedItemPosition();
	  editor.putInt(SEX_KEY, sex);
	  
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
		spSexSelector.setSelection(preferences.getInt(SEX_KEY, 0));
		edPositionName.setText(preferences.getString(POSITION_NAME_KEY, ""));
		edSalary.setText(preferences.getString(SALARY_KEY, ""));
		edPhone.setText(preferences.getString(PHONE_KEY, ""));
		edEmail.setText(preferences.getString(EMAIL_KEY, ""));
	}

}
