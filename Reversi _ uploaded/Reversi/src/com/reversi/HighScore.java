package com.reversi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TextView;

public class HighScore extends Activity {

	private TableLayout highScoreTBL;
	private String usrName;

	private void getFromPHP() {
		JSONArray JArr = new JSONArray();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", usrName));

		JSONParser JP = new JSONParser();
		String URL = "http://10.0.2.2/high_score.php"; // changed when
		// published
		// to web server
		JSONObject JOb = JP.makeHttpRequest(URL, "POST", params);
		try {
			JArr = JOb.getJSONArray("high");

			TextView playerTV;			
			
			playerTV = new TextView(this);
			playerTV.setText(JArr.getString(1));
			playerTV.setTextSize((float) 30.0);
			playerTV.setTextColor(Color.parseColor("#FFFFFF"));
			playerTV.setGravity(Gravity.RIGHT);
			highScoreTBL.addView(playerTV);
			
			playerTV = new TextView(this);
			playerTV.setText(JArr.getString(2));
			playerTV.setTextSize((float) 30.0);
			playerTV.setTextColor(Color.parseColor("#FFFFFF"));
			playerTV.setGravity(Gravity.RIGHT);
			highScoreTBL.addView(playerTV);
			
			playerTV = new TextView(this);
			int x = Integer.valueOf(JArr.getString(1)) - Integer.valueOf(JArr.getString(2));
			playerTV.setText(String.valueOf(x));
			playerTV.setTextSize((float) 30.0);
			playerTV.setTextColor(Color.parseColor("#FFFFFF"));
			playerTV.setGravity(Gravity.RIGHT);
			highScoreTBL.addView(playerTV);
			
			playerTV = new TextView(this);
			playerTV.setText(JArr.getString(0));
			playerTV.setTextSize((float) 30.0);
			playerTV.setTextColor(Color.parseColor("#FFFFFF"));
			playerTV.setGravity(Gravity.RIGHT);
			highScoreTBL.addView(playerTV);
			
		} catch (JSONException e) {
			message("error1");
			e.printStackTrace();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score);
		highScoreTBL = (TableLayout)findViewById(R.id.tbl_score_highscore);
		usrName = this.getIntent().getStringExtra("usrName");
		getFromPHP();
	}

	private void message(String Error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(HighScore.this);
		builder.setMessage(Error).setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				builder.setCancelable(true);
				finish(); // return to previous activity
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
