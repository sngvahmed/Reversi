package com.reversi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

	private Button loginBTN, signupBTN;
	private EditText usernameET, passwordET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginBTN = (Button) findViewById(R.id.btn_login_login);
		signupBTN = (Button) findViewById(R.id.btn_signup_login);
		usernameET = (EditText) findViewById(R.id.et_username_login);
		passwordET = (EditText) findViewById(R.id.et_password_login);
		signupBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent n = new Intent(Login.this, Signup.class);
				startActivity(n);
			}
		});

		loginBTN.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				int tryLogin = enterAccount();

				if (tryLogin == 1) {
					Intent n = new Intent(Login.this, MainMenu.class);
					// send username and password to next activity
					n.putExtra("username", usernameET.getText().toString());
					n.putExtra("password", passwordET.getText().toString());
					// //
					startActivity(n);
				} else if (tryLogin == 0) {
					message("Invalid username or password");
				} else if (tryLogin == 2) {
					message("Account already login");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

	private int enterAccount() { // return 0 in case of fail to login and
									// otherwise

		String user = usernameET.getText().toString();
		String pass = passwordET.getText().toString();

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("username", user));
		params.add(new BasicNameValuePair("password", pass));

		JSONParser JP = new JSONParser();

		String URL = "http://10.0.2.2/login.php"; // changed when
													// published
													// to web server
		JSONObject JOb = JP.makeHttpRequest(URL, "POST", params);

		int successLogin = 0, alreadyLogin = 0;
		try {
			successLogin = JOb.getInt("success");
			alreadyLogin = JOb.getInt("login");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (alreadyLogin == 1) {
			return 2; // 2 means account already login
		}
		return successLogin;
	}

	private void message(String Error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				Login.this);

		builder.setMessage(Error).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						builder.setCancelable(true);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
