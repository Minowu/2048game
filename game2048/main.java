package game2048;

import javax.swing.UIManager;

class main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new controller(new view(5));
		}catch(Exception e) {
			e.printStackTrace();
		}
}
}
