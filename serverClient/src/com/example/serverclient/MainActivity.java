package com.example.serverclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements DataDisplay {

	TextView ServerMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ServerMessage = (TextView) findViewById(R.id.textView1);
	}

	public void connect(View view) {
		MyServer Server = new MyServer();
		Server.SetEventListner(this);
		Server.startListening();
	}

	public void Display(String message) {
		ServerMessage.setText(" " + message);
	}
}
