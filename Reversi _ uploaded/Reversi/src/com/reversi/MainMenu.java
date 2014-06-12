package com.reversi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {

	private Button startBTN, logoutBTN, helpBTN, topTenBTN, highScoreBTN;
	private TextView welcomeTV;
	private String username = "", password = "";

	private class Handler implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == startBTN.getId()) { // start server

				Intent n = new Intent(MainMenu.this, StartGame.class);
				startActivity(n);

			} else if (v.getId() == logoutBTN.getId()) { // logout
				if (updateLogin() == 1) {
					finish();
				}
			} else if (v.getId() == helpBTN.getId()) {
				Intent n = new Intent(MainMenu.this, Help.class);
				startActivity(n);
			} else if (v.getId() == topTenBTN.getId()) {
				Intent n = new Intent(MainMenu.this, TopTen.class);
				startActivity(n);
			} else if (v.getId() == highScoreBTN.getId()) {
				Intent n = new Intent(MainMenu.this, HighScore.class);
				n.putExtra("usrName", username);
				startActivity(n);
			}

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		Handler h = new Handler();
		startBTN = (Button) findViewById(R.id.btn_startgame_menu);
		helpBTN = (Button) findViewById(R.id.btn_help_menu);
		logoutBTN = (Button) findViewById(R.id.btn_exit_menu);
		topTenBTN = (Button) findViewById(R.id.btn_topten_menu);
		highScoreBTN = (Button) findViewById(R.id.btn_highscore_menu);
		welcomeTV = (TextView) findViewById(R.id.tv_welcome_menu);

		startBTN.setOnClickListener(h);
		helpBTN.setOnClickListener(h);
		logoutBTN.setOnClickListener(h);
		topTenBTN.setOnClickListener(h);
		highScoreBTN.setOnClickListener(h);

		Intent myIntent = getIntent(); // gets the previously created intent
		username = myIntent.getStringExtra("username"); // will return
														// "username"
		password = myIntent.getStringExtra("password"); // will return
														// "password"

		Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),
				"fonts/welcome_user.ttf");
		welcomeTV.setTypeface(myTypeface);
		welcomeTV.setText(username);
	}

	public int updateLogin() {

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));

		JSONParser JP = new JSONParser();

		String URL = "http://10.0.2.2/logout.php"; // changed when
													// published
													// to web server
		JSONObject JOb = JP.makeHttpRequest(URL, "POST", params);

		int successLogout = 0;
		try {
			successLogout = JOb.getInt("success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return successLogout;
	}

	public void onBackPressed() {
		// override this function because user may return without login update
		// in db
		if (updateLogin() == 1) {
			finish();
		}

	}
}
