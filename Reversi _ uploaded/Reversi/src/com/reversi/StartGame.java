package com.reversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StartGame extends Activity {

	private ImageButton cells[][] = new ImageButton[8][8];
	private char check_color[][] = new char[8][8]; // for check whether cell is
													// Light brown or dark brown
	private char turn;
	private Game game = new Game();
	private TextView t;
	private Socket client;
	private String test = "";
	Handler updateConversationHandler;
	private boolean played = false;
	String id;

	class handler implements View.OnClickListener {

		public void onClick(View v) {

			for (int x = 0; x < 8; x++) {

				for (int y = 0; y < 8; y++) {

					if (v.getId() == cells[x][y].getId()) { // this button
															// pressed

						if (!played && game.Valid_move(x, y)) {

							game.play(x, y);
							UpdateBoard();

							PrintWriter o = null;
							try {
								if (client != null) {
									o = new PrintWriter(new OutputStreamWriter(
											client.getOutputStream()), true);
									o.println(game.getGrid());
								} else
									message("Can not");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							/*
							 * if (game.game_over() != ' ') {
							 * message((game.game_over() == 'W') ? "White Win" :
							 * "Black Win");
							 * 
							 * finish(); // end the game return; }
							 */

							game.set_turn(turn); // switch

							// turn
							t.setText(turn + " Turn");
							played = true;
						} else
							message("Invalid move");

						break;
					}

				}

			}

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_game);

		t = (TextView) findViewById(R.id.tv_player_startgame);
		turn = 'W'; // White plays first

		setupGUI();

		updateConversationHandler = new Handler();
		try { // connect
			InetAddress serverAddr = InetAddress.getByName("10.0.2.2");
			client = new Socket(serverAddr, 5000);
			new background().start();

			// / Receive the user turn
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));

			String msg = in.readLine();
			turn = msg.charAt(0);
			game.set_turn(turn);
			t.setText(turn + " Turn");

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class background extends Thread {
		public void run() {

			while (true) {
				if (client != null) {
					BufferedReader in = null;
					try {
						in = new BufferedReader(new InputStreamReader(
								client.getInputStream()));

						test = in.readLine();

						String grid = test.substring(0, 64), t = test
								.substring(64);
						test = grid;
						updateConversationHandler
								.post(new updateUIThread(grid));

						if (t.charAt(0) == turn) { // allow user to play
							played = false;
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void refresh() {
		if (!test.equals("")) {
			game.setGrid(test);

			UpdateBoard();
		}
	}

	/*
	 * protected void doInBackground() {
	 * 
	 * while (true) { BufferedReader in = null; try { in = new
	 * BufferedReader(new InputStreamReader( client.getInputStream()));
	 * 
	 * String test = in.readLine(); game.setGrid(test); UpdateBoard(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } }
	 * 
	 * }
	 */

	private void UpdateBoard() {

		for (int x = 0; x < 8; x++) {

			for (int y = 0; y < 8; y++) {
				char square = game.getCell(x, y);
				if (square == 'W' && check_color[x][y] == 'L') {
					cells[x][y].setBackgroundResource(R.drawable.desklwb);
				} else if (square == 'W' && check_color[x][y] == 'S') {
					cells[x][y].setBackgroundResource(R.drawable.deskbw);
				} else if (square == 'B' && check_color[x][y] == 'L') {
					cells[x][y].setBackgroundResource(R.drawable.desklbb);
				} else if (square == 'B' && check_color[x][y] == 'S') {
					cells[x][y].setBackgroundResource(R.drawable.deskbb);
				}

			}
		}
	}

	private void setupGUI() {
		TableLayout grid = (TableLayout) findViewById(R.id.tbl_board_startgame);
		handler h = new handler();

		int idd = 0;
		for (int i = 0; i < 8; i++) {
			TableRow row = new TableRow(this);
			for (int j = 0; j < 8; j++) {

				ImageButton cell = new ImageButton(this);
				cell.setId(idd++);

				initialize_cells(i, j, cell); // setup interface design
				cell.setOnClickListener(h);
				cells[i][j] = cell;
				row.addView(cell);

			}
			grid.addView(row);
		}

	}

	private void initialize_cells(int i, int j, ImageButton cell) {
		if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
			check_color[i][j] = 'L';
			cell.setBackgroundResource(R.drawable.desklwb);
		} else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
			check_color[i][j] = 'S';
			cell.setBackgroundResource(R.drawable.deskbb);
		} else if (i % 2 == 0) {
			if (j % 2 == 0) {
				cell.setBackgroundResource(R.drawable.brownl);
				check_color[i][j] = 'L';
			} else {
				cell.setBackgroundResource(R.drawable.browns);
				check_color[i][j] = 'S';
			}

		} else {
			if (j % 2 == 0) {
				cell.setBackgroundResource(R.drawable.browns);
				check_color[i][j] = 'S';
			} else {
				cell.setBackgroundResource(R.drawable.brownl);
				check_color[i][j] = 'L';
			}

		}
	}

	private void message(String Error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(Error).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						builder.setCancelable(true);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void onBackPressed() {

		finish();
	}

	class updateUIThread implements Runnable {
		private String msg;

		public updateUIThread(String str) {
			this.msg = str;
		}

		@Override
		public void run() {
			refresh();

		}
	}
}
