package com.example.serverclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class MyServer {
	Thread MobThread;
	ServerSocket M_Server;
	String M_Message;
	DataDisplay M_DataDistplay;
	Object M_Connected;

	public MyServer() {

	}

	public void SetEventListner(DataDisplay dataDisplay) {
		M_DataDistplay = dataDisplay;
	}

	public void startListening() {
		MobThread = new Thread(new Runnable() {
			public void run() {
				try {
					M_Server = new ServerSocket(2001);
					Socket connectedSocket = M_Server.accept();
					Message ClientMassage = Message.obtain();
					ObjectInputStream ois = new ObjectInputStream(
							connectedSocket.getInputStream());
					String strMassage = (String) ois.readObject();
					ClientMassage.obj = strMassage;
					mHandler.sendMessage(ClientMassage);
					ObjectOutputStream oos = new ObjectOutputStream(
							connectedSocket.getOutputStream());
					oos.writeObject("HI :: ");
					ois.close();
					oos.close();
					M_Server.close();
				} catch (Exception e) {
					Message M3 = Message.obtain();
					M3.obj = e.getMessage();
					mHandler.sendMessage(M3);
				}
			}
		});
		MobThread.start();
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message status) {
			M_DataDistplay.Display(status.obj.toString());
		}
	};
}
