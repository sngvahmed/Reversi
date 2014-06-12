package com.reversi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
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

public class TopTen extends Activity {

	private TableLayout topTenNamesTBL, topTenHighScoreTBL;

	private void getFromPHP() {
		JSONArray JArr = new JSONArray();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONParser JP = new JSONParser();
		String URL = "http://10.0.2.2/top_ten.php"; // changed when
		// published
		// to web server
		JSONObject JOb = JP.makeHttpRequest(URL, "POST", params);
		try {
			JArr = JOb.getJSONArray("Top");
			for (int i = 0; i < JArr.length(); i += 2) {
				TextView playerTV = new TextView(this);
				playerTV.setText(JArr.getString(i));
				playerTV.setTextSize((float) 30.0);
				playerTV.setTextColor(Color.parseColor("#FFFFFF"));
				playerTV.setGravity(Gravity.LEFT);
				topTenNamesTBL.addView(playerTV);
				
				playerTV = new TextView(this);
				playerTV.setText(JArr.getString(i + 1));
				playerTV.setTextSize((float) 30.0);
				playerTV.setTextColor(Color.parseColor("#FFFFFF"));
				playerTV.setGravity(Gravity.RIGHT);
				topTenHighScoreTBL.addView(playerTV);
			}
		} catch (JSONException e) {
			message("error1");
			e.printStackTrace();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topten);
		topTenNamesTBL = (TableLayout) findViewById(R.id.tbl_name_topten);
		topTenHighScoreTBL = (TableLayout) findViewById(R.id.tbl_highscore_topten);
		getFromPHP();
	}

	private void message(String Error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(TopTen.this);
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
