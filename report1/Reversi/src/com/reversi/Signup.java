package com.reversi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends Activity {

	private Button register;
	private EditText Username, Password, confPass, Email, Country;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		register = (Button) findViewById(R.id.signup_form);

		register.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (validate_form()) {

					/*
					 * insert account in database here :)
					 */
					message("Registered successfully");

					finish(); // return to previous activity

				}
			}
		});

	}

	private void message(String Error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this); // final
																					// for
																					// knowing
																					// what
																					// object
																					// use
																					// to
																					// cancel
		builder.setMessage(Error).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						builder.setCancelable(true);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private boolean check_password(String p) {

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

	public boolean validate_form() {
		Username = (EditText) findViewById(R.id.username);
		Password = (EditText) findViewById(R.id.password);
		confPass = (EditText) findViewById(R.id.confpassword);
		Email = (EditText) findViewById(R.id.email);
		Country = (EditText) findViewById(R.id.country);

		String username = Username.getText().toString();
		String pass = Password.getText().toString();
		String cpass = confPass.getText().toString();
		String email = Email.getText().toString();
		String country = Country.getText().toString();
		// check that data is completed
		if (username.equals("") || pass.equals("") || cpass.equals("")
				|| email.equals("") || country.equals("")) {
			message("Data not completed");
			return false;
		} else if (username.length() > 20) { // Username length
			message("Username must be less than or equal 20 character");
			return false;
		} else if (!pass.equals(cpass)) { // password match
			message("two Passwords boxes did not match");
			return false;
		} else if (!check_password(pass)) { // password complexity
			message("Password must contain at least 2 uppercase & 2 digits");
			return false;
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) { // check email is valid
			message("Email address is invalid");
			return false;
		}

		return true;
	}

}
