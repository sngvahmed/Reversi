package com.reversi;

import java.util.*;

public class Game {
	private Vector<Vector<Character>> grid = new Vector<Vector<Character>>();
	private int di[] = { 0, 0, 1, -1, 1, -1, 1, -1 };
	private int dj[] = { 1, -1, 0, 0, 1, -1, -1, 1 };
	private char turn;
	private char other_turn;

	Game() { // to play new game
		turn = ' ';
		other_turn = ' ';
		grid.setSize(8);
		for (int i = 0; i < 8; i++) {
			grid.set(i, new Vector<Character>(8));
			for (int j = 0; j < 8; j++) {
				grid.get(i).add(' ');
			}
		}
		grid.get(3).set(3, 'W');
		grid.get(3).set(4, 'B');
		grid.get(4).set(3, 'B');
		grid.get(4).set(4, 'W');
	}

	public void set_turn(char c) { // to set turn and other_turn
		turn = c;
		other_turn = 'W';
		if (turn == 'W')
			other_turn = 'B';
	}

	// to check border
	private int BORD(int dx, int dy, int x, int y) {
		// dx -> current x , dy -> current y , x,y ->move value coordinate
		int count = 0;
		while (true) {
			dx = dx + x;
			dy = dy + y;
			if (dx >= 8 || dy >= 8 || dx < 0 || dy < 0)
				return -1;
			if (grid.get(dx).get(dy) == turn)
				return count;
			if (grid.get(dx).get(dy) == ' ')
				return -1;
			count++;
		}
	}

	// change coordinate to the offset value
	public void play(int x, int y) {
		grid.get(x).set(y, turn);
		for (int i = 0; i < 8; i++) {
			int dx = x, dy = y;

			if (BORD(x, y, di[i], dj[i]) > 0) {
				while (true) {
					dx = dx + di[i];
					dy = dy + dj[i];
					if (grid.get(dx).get(dy) == turn)
						break;
					grid.get(dx).set(dy, turn);
				}
			}
		}
	}

	public boolean Valid_move(int x, int y) {// to check the move is valid or
		// not
		if (grid.get(x).get(y) != ' ')
			return false;
		for (int i = 0; i < 8; i++) {
			if (BORD(x, y, di[i], dj[i]) > 0)
				return true;
		}
		return false;
	}

	public char game_over() {// to check the game end or not
		int count_W = 0, count_B = 0;
		turn = other_turn;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Valid_move(i, j))
					return ' ';
				if (grid.get(i).get(j) == 'W')
					count_W++;
				else
					count_B++;
			}
		}
		if (count_W > count_B)
			return 'W';
		else if (count_B > count_W)
			return 'B';
		else
			return 'T';
	}

	public char getCell(int x, int y) {
		return grid.get(x).get(y);
	}

	public String getGrid() { // for sending to another user

		String ret = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ret += grid.get(i).get(j);
			}
		}

		return ret;
	}

	public void setGrid(String g) { // for sending to another user

		int p = 0;
		for (int i = 0; i < g.length(); i += 8, p++) {
			String n = g.substring(i, i + 8);
			for (int j = 0; j < 8; j++) {
				grid.get(p).set(j, n.charAt(j));
			}
		}

	}
}