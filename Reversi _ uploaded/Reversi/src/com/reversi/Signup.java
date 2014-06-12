package com.reversi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends Activity {

	private Button registerBTN;
	private EditText usernameET, passwordET, confPassET, emailET, countryET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		registerBTN = (Button) findViewById(R.id.btn_signup_signup);

		registerBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (validateForm()) {

					try {
						int insert = insertIntoDB();
						if (insert == 1) {
							message("Registered successfully", true);
						} else if (insert == 2) { // username exist before
							message("username exists before", false);
						} else if (insert == 3) {// email exist before
							message("email exists before", false);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	private void message(String error, final boolean f) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this); // final
																					// for
																					// knowing
																					// what
																					// object
																					// use
																					// to
																					// cancel
		builder.setMessage(error).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						builder.setCancelable(true);
						if(f)
							finish(); // return to previous activity
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private boolean checkPassword(String p) {

		int uppercase = 0, digits = 0;

		for (int i = 0; i < p.length(); i++) {
			if (p.charAt(i) >= 'A' && p.charAt(i) <= 'Z')
				uppercase++;
			else if (p.charAt(i) >= '0' && p.charAt(i) <= '9')
				digits++;
		}
		if (digits < 2 || uppercase < 2)
			return false;
		return true;
	}

	public boolean validateForm() {
		usernameET = (EditText) findViewById(R.id.et_username_signup);
		passwordET = (EditText) findViewById(R.id.et_password_signup);
		confPassET = (EditText) findViewById(R.id.et_confpassword_signup);
		emailET = (EditText) findViewById(R.id.et_email_signup);
		countryET = (EditText) findViewById(R.id.et_country_signup);

		String user = usernameET.getText().toString();
		String pass = passwordET.getText().toString();
		String cpass = confPassET.getText().toString();
		String email = emailET.getText().toString();
		String country = countryET.getText().toString();
		// check that data is completed
		if (user.equals("") || pass.equals("") || cpass.equals("")
				|| email.equals("") || country.equals("")) {
			message("Data not completed", false);
			return false;
		} else if (user.length() > 20) { // Username length
			message("Username must be less than or equal 20 character", false);
			return false;
		} else if (!pass.equals(cpass)) { // password match
			message("two Passwords boxes did not match", false);
			return false;
		} else if (!checkPassword(pass)) { // password complexity
			message("Password must contain at least 2 uppercase & 2 digits", false);
			return false;
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) { // check email is valid
			message("Email address is invalid", false);
			return false;
		}

		return true;
	}

	private int insertIntoDB() {

		String user = usernameET.getText().toString();
		String pass = passwordET.getText().toString();
		String email = emailET.getText().toString();
		String country = countryET.getText().toString();

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("username", user));
		params.add(new BasicNameValuePair("password", pass));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("country", country));
		
		JSONParser JP = new JSONParser();

		String URL = "http://10.0.2.2/check_exist.php"; // changed when
														// published
														// to web server
		JSONObject JOb = JP.makeHttpRequest(URL, "POST", params);

		try {
			int usernameExist = JOb.getInt("username_exist"), emailExist = JOb
					.getInt("email_exist");

			if (usernameExist == 1) {
				return 2;
			} else if (emailExist == 1) {
				return 3;
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		URL = "http://10.0.2.2/newMember.php"; // changed when published
		// to web server
		JOb = JP.makeHttpRequest(URL, "POST", params);

		Log.d("Create Response", JOb.toString());
		int success = 0;
		try {
			success = JOb.getInt("success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			message("Error", true);
			// e.printStackTrace();
		}

		return success; // return 0 in case of fail and 1 otherwise

	}

}
