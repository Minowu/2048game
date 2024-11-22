	package game2048;
	
	import javax.swing.*;
	import java.awt.*;
import java.net.URL;
	
	
	public class view extends JFrame {
		private int SIZE;// de nhap
		private JLabel[][] box ;
		private JPanel panel;
		private controller controllerGame;
		private JLabel label_score;
		private JPanel panel_score;
	    private int bestScore=0;
		private JLabel label_bestScore;
		private JLabel label_time;
		private JPanel panel_mode;
		
	    public int getBestScore() {
			return bestScore;
		}

		public void setBestScore(int bestScore) {
			this.bestScore = bestScore;
		}

		public view(int size) {
		    this.SIZE = size;
		    this.box = new JLabel[SIZE][SIZE];
		    this.init();
		    this.setLocationRelativeTo(null);
		    this.setLayout(new BorderLayout());
		    controllerGame = new controller(this);
		    // Thêm KeyListener vào cửa sổ JFrame
		    this.addKeyListener(controllerGame);
		    this.menuBar();
		    this.mode_button();
		    this.setFocusable(true);
		    this.setEnabled(true);
		    this.requestFocusInWindow();
		    this.setVisible(true);
		}
	    public int getSIZE() {
			return SIZE;
		}
		public void setSIZE(int sIZE) {
			SIZE = sIZE;
		}
	    public void init() {
	        setTitle("2048 Game");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(false);//Không cho thay đổi kích thước
	        this.setSize(550,600);
	        
	        URL url_2048 = view.class.getResource("2048-icon-2025x2048-d48254hz.png");
	        Image img = Toolkit.getDefaultToolkit().createImage(url_2048);
	        this.setIconImage(img);
	        
	    	panel_score = new JPanel();
	    	panel_score.setLayout(new GridLayout(2,2));
	    	label_score = new JLabel("Score: 0" );
	    	label_time = new JLabel(" Time:");
	        label_bestScore = new JLabel("BEST: "+getBestScore());
	        label_score.setFont(new Font("Arial", Font.BOLD, 26));
	        label_bestScore.setFont(new Font("Arial", Font.BOLD, 26));
	        label_time.setFont(new Font("Arial",Font.ITALIC,15));
	    	panel_score.add(label_score);
	    	panel_score.add(label_bestScore);
	    	panel_score.add(label_time);
	    	this.add(panel_score,BorderLayout.NORTH);
	    	
	        this.panel = new JPanel();
	        panel.setBackground(new Color(192,192,192));
	        panel.setLayout(new GridLayout(SIZE, SIZE, 2, 2));
	        panel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,new Color(192,192,192)));//tao viền bên ngoài
	        
	        JPanel southPanel = new JPanel();
	        southPanel.setLayout(new BorderLayout());
	        JLabel labelCreatedBy	= new JLabel("Trường GTVT");
	        southPanel.add(labelCreatedBy, BorderLayout.CENTER);
	        this.add(southPanel, BorderLayout.SOUTH);
	        
	        for (int i = 0; i < SIZE; i++) {
	            for (int j = 0; j < SIZE; j++) {
	                box[i][j] = new JLabel("", SwingConstants.CENTER);
	                box[i][j].setPreferredSize(new Dimension(100, 100));
	                box[i][j].setOpaque(true);
	                box[i][j].setFont(new Font("Arial", Font.BOLD, 24));
	                panel.add(box[i][j]);
	            }
	        }
	        this.add(panel,BorderLayout.CENTER);
	        drawScence(new int[SIZE][SIZE]);
	        
	    }
	    

		public void drawScence(int[][] gameField) {
	    	for (int i = 0; i < gameField.length; i++) {
	            for (int j = 0; j < gameField[i].length; j++) {
	            	setCellColoredNumber(i,j , gameField[i][j]);
	            }
	        }
	    	this.setVisible(true);
	    }
	
	    private void setCellColoredNumber(int x, int y, int value) {
	        Color color = 	getColorByValue(value);
	        String str = value > 0 ? "" + value : "";
	        box[x][y].setText(str);
	        box[x][y].setBackground(color);
	    }
	    private Color getColorByValue(int value) {
	        switch (value) {
	            case 0:
	                return new Color(255,229,204);
	            case 2:
	                return new Color(255,178,102);
	            case 4:
	                return new Color(255,153,51);
	            case 8:
	                return new Color(255,165,0);
	            case 16:
	                return new Color(255,128,0);
	            case 32:
	                return new Color(255,255,21);
	            case 64:
	                return new Color(153,255,51)	;
	            case 128:
	                return new Color(51,255,51);
	            case 256:
	                return new Color(51,255,255);
	            case 512:
	                return new Color(127,0,255);
	            case 1024:
	                return new Color(255,51,153);
	            case 2048:
	            	return new Color(255,51,51);
	            default:
	            	return new Color(255,229,204);
	        }
	    }
	    public void win() {
	    	JOptionPane.showMessageDialog(this, "YOU WIN!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	    	startOver();
		}
	    public void gameOver() {
	    	JOptionPane.showMessageDialog(this, "GAME OVER!", "Message", JOptionPane.INFORMATION_MESSAGE);
	    	startOver();
	    }
	    private void startOver() {
	    	JLabel label_startOver = new JLabel("Bấm phím cách để bắt đầu lại trò chơi");
	    	
	    	JPanel panel1 = new JPanel();
	    	panel1.setLayout(new GridBagLayout());
	    	panel1.add(label_startOver);
	    	this.add(panel1);
	    	this.setVisible(true);
	    }
	    public void showScore(int score) {
	    	label_score.setText("Score: " +score);
	    }
	    public void showBestScore(int bestscore) {
	    	label_bestScore.setText("BEST: " + bestscore);
	    }
	    public void showTime(int time) {
			label_time.setText("Time:"+time);;
		}
	    public void mode_1() {
	        label_time.setText("Time: ∞");
	        this.addKeyListener(controllerGame);
	        this.requestFocusInWindow();
	    }

	    public void mode_2() {
	    	this.addKeyListener(controllerGame);
	        this.requestFocusInWindow();   
	    }

	    public void mode_3() {
	        // Define the behavior for Mode 3
	        this.addKeyListener(controllerGame);
	        this.requestFocusInWindow();
	    }
	    public void menuBar() {
	    	URL setSize = view.class.getResource("setSize.jpg");
		    Image img1 = Toolkit.getDefaultToolkit().createImage(setSize);
		    
	    	JMenuBar jmenu_bar = new JMenuBar();
	        JMenu jmenu_file = new JMenu("File");
	        JMenuItem jmenu_new = new JMenuItem("New Game");
	        jmenu_new.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(view.class.getResource("new.png"))));
	        jmenu_new.addActionListener(this.controllerGame);
	        JMenuItem jmenu_setSize = new JMenuItem("Set Size");
	        jmenu_setSize.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(view.class.getResource("setting.png"))));
	        jmenu_setSize.addActionListener(this.controllerGame);
	        JMenuItem jmenu_exit = new JMenuItem("Exit Game");
	        jmenu_exit.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(view.class.getResource("exit.png"))));
	        jmenu_exit.addActionListener(this.controllerGame);
	        jmenu_file.add(jmenu_new);
	        jmenu_file.addSeparator();
	        jmenu_file.add(jmenu_setSize);
	        jmenu_file.addSeparator();
	        jmenu_file.add(jmenu_exit);
	        jmenu_bar.add(jmenu_file);
	        this.setJMenuBar(jmenu_bar);
	    }
	    public void mode_button() {
	    	panel_mode = new JPanel();
	    	panel_mode.setLayout(new GridLayout(1,3));
	    	JButton button_1= new JButton("Mode 1");
	    	JButton button_2= new JButton("Mode 2");
	    	JButton button_3= new JButton("Mode 3");
	    	button_1.addActionListener(controllerGame);
	    	button_2.addActionListener(controllerGame);
	    	button_3.addActionListener(controllerGame);
	    	panel_mode.add(button_1);
	    	panel_mode.add(button_2);
	    	panel_mode.add(button_3);
	    	panel_score.add(panel_mode);
	    	
	    }
	}
	
	
