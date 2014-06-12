package com.reversi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class User_login extends Activity {

	private Button login, signup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);

		login = (Button) findViewById(R.id.login);
		signup = (Button) findViewById(R.id.B_signup);

		signup.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent n = new Intent(User_login.this, Signup.class);
				startActivity(n);

			}
		});

		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent n = new Intent(User_login.this, menu.class);
				startActivity(n);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

}
