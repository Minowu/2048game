package game2048;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class controller implements KeyListener, ActionListener {
	private view viewGame;
	private model modelGame;
	private boolean isGameStopped = false;
	private Scanner sc;
	private Thread countdownThread;
	private boolean isCountdownRunning = false;
	private int scoreCountDown = 0;
	private int countdownmode3;
	

	public controller(view viewGame) {
		this.viewGame = viewGame;
		modelGame = new model(viewGame.getSIZE());
		this.viewGame.drawScence(modelGame.getGameField());
		if (modelGame.getScore() > modelGame.load_bestScore()) {
			modelGame.save();
		}
		viewGame.showBestScore(
				modelGame.load_bestScore() > modelGame.getScore() ? modelGame.load_bestScore() : modelGame.getScore());

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		// left:37,up:38,right:39,down:40,space:32
		if (this.isGameStopped) {
			if (modelGame.getScore() > modelGame.load_bestScore()) {
				modelGame.save();
			}
		}
		if (e.getKeyCode() == 32) {
			viewGame.dispose();
			this.viewGame = new view(viewGame.getSIZE());
			modelGame = new model(viewGame.getSIZE());
			this.isGameStopped = false;
		}

		switch (e.getKeyCode()) {
		case 37:
			modelGame.moveLeft();
			viewGame.drawScence(modelGame.getGameField());
			break;
		case 38:
			modelGame.moveUp();
			viewGame.drawScence(modelGame.getGameField());
			break;
		case 39:
			modelGame.moveRight();
			viewGame.drawScence(modelGame.getGameField());
			break;
		case 40:
			modelGame.moveDown();
			viewGame.drawScence(modelGame.getGameField());
			break;
		}
		if (modelGame.getMaxValue() == 2048) {
			this.isGameStopped = true;
			viewGame.win();
		}
		if (!modelGame.canUserMove()) {
			this.isGameStopped = true;
			viewGame.gameOver();
		}
		int countdown2 = (modelGame.getScore() - scoreCountDown)/2;
		scoreCountDown = modelGame.getScore();
		countDown_class.setCountdown2(countDown_class.getCountdown2() + countdown2);
		viewGame.showScore(modelGame.getScore());
		viewGame.showBestScore(modelGame.load_bestScore() > modelGame.getScore() ? modelGame.load_bestScore() : modelGame.getScore());
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		if (button.equals("Exit Game")) {
			System.exit(0);
		} else if (button.equals("Mode 1")) {
			if (!isCountdownRunning) {
				stopCountdown();
				viewGame.dispose();
				this.viewGame = new view(viewGame.getSIZE());
				modelGame = new model(viewGame.getSIZE());
				this.isGameStopped = false;
			}
			viewGame.mode_1();
		} else if (button.equals("Mode 2")) {
			stopCountdown();
			viewGame.dispose();
			this.viewGame = new view(viewGame.getSIZE());
			modelGame = new model(viewGame.getSIZE());
			this.isGameStopped = false;
			countDown_class.setCountdown2(60);
			modelGame.setScore(0);
			viewGame.mode_2();
			startCountdown();
			if (countDown_class.getCountdown2() == 0) {
				viewGame.gameOver();

			}
		}

		else if (button.equals("Mode 3")) {
			stopCountdown();
			viewGame.dispose();
			this.viewGame = new view(viewGame.getSIZE());
			modelGame = new model(viewGame.getSIZE());
			this.isGameStopped = false;
			viewGame.mode_3();
			countdownmode3 = 301;
			startCountdown();
			if (countdownmode3 == 0) {
				viewGame.gameOver();

			}
		}

		else if (button.equals("New Game")) {
			stopCountdown();
			viewGame.dispose();
			this.viewGame = new view(viewGame.getSIZE());
			modelGame = new model(viewGame.getSIZE());
			this.isGameStopped = false;
		} else if (button.equals("Set Size")) {
			sc = new Scanner(System.in);
			JFrame frame = new JFrame();
			int newSize = Integer.parseInt(JOptionPane.showInputDialog(frame, "Nhập kích cỡ ô trò chơi:"));
			if(newSize<2 || newSize>20) {
				newSize=4;
			}
			viewGame.dispose();
			this.viewGame = new view(newSize);
			modelGame = new model(newSize);
			this.isGameStopped = false;
		}
	}

	private void startCountdown() {
		if (!isCountdownRunning) {
			countdownThread = new Thread(new CountdownRunnable());
			this.isCountdownRunning = true;
			countdownThread.start();
		}
	}

	private void stopCountdown() {
		if (isCountdownRunning) {
			isCountdownRunning = false;
			countdownThread.interrupt();

		}
	}

	private class CountdownRunnable implements Runnable {

		@Override
		public void run() {
			if (countDown_class.getCountdown2() > 0 && countdownmode3 < 100) {
				while (countDown_class.getCountdown2() > 0) {
					try {
						Thread.sleep(1000); // wait 1 second
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					countDown_class.setCountdown2(countDown_class.getCountdown2() - 1);
					viewGame.showTime(countDown_class.getCountdown2());
				}
				viewGame.dispose();
				viewGame.gameOver();
				isCountdownRunning = false;

			} else {
				while (countdownmode3 > 0) {
					try {
						Thread.sleep(1000); // wait 1 second
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					countdownmode3--;
					viewGame.showTime(countdownmode3);
				}
				viewGame.dispose();
				viewGame.gameOver();
				isCountdownRunning = false;
			}
		}
	}

	private static class countDown_class {
		public static int countdown2 = 60;

		public static int getCountdown2() {
			return countdown2;
		}

		public static void setCountdown2(int countdown) {
			countdown2 = countdown;
		}
	}

}
