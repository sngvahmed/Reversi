package com.example.clientsocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView ServerMessage;
	Thread m_objectThread;
	Socket clientSocket;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ServerMessage = (TextView) findViewById(R.id.textView1);
	}

	public void Start(View view) {
		m_objectThread = new Thread(new Runnable() {

			public void run() {
				try {
					clientSocket = new Socket("127.0.0.1", 2001);
					ObjectOutputStream oos = new ObjectOutputStream(
							clientSocket.getOutputStream());
					oos.writeObject("HELLO");
					Message ServerMessage = Message.obtain();
					ObjectInputStream ois = new ObjectInputStream(
							clientSocket.getInputStream());
					String message = (String) ois.readObject();
					ServerMessage.obj = message;
					mHandler.sendMessage(ServerMessage);
					oos.close();
					ois.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		m_objectThread.start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			messageDisplay(msg.obj.toString());
		}

		private void messageDisplay(String servermessage) {
			ServerMessage.setText("" + servermessage);
		}
	};
}
