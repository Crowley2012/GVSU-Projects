import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class MyTimerPanel {

	private StopWatch s1;
	private StopWatch s2;

	private Timer javaTimer1;
	private Timer javaTimer2;
	private Timer javaTimer3;

	private JFrame frame;
	private JPanel panel;
	private JPanel group1;
	private JPanel group2;
	private JPanel group3;
	private JPanel group4;
	private JPanel group5;
	private JPanel group6;

	private JTextArea timeResults1;
	private JTextArea timeResults2;

	private JMenuBar menu;
	private JMenu fileMenu;
	private JMenuItem quit;

	private JMenu timerMenu1;
	private JMenuItem load1;
	private JMenuItem save1;

	private JMenu timerMenu2;
	private JMenuItem load2;
	private JMenuItem save2;

	private JButton start1;
	private JButton stop1;
	private JButton reset1;
	private JButton start2;
	private JButton stop2;
	private JButton reset2;

	private File f;

	public MyTimerPanel() {

		s1 = new StopWatch();
		s2 = new StopWatch();

		f = new File("Timer1.txt");
		if (!f.exists()) {
			s1.save("Timer1.txt");
		}

		f = new File("Timer2.txt");
		if (!f.exists()) {
			s2.save("Timer2.txt");
		}

		frame = new JFrame("Stopwatch");
		panel = new JPanel();
		group1 = new JPanel();
		group2 = new JPanel();
		group3 = new JPanel();
		group4 = new JPanel();
		group5 = new JPanel();
		group6 = new JPanel();

		timeResults1 = new JTextArea("0:00:000");
		timeResults2 = new JTextArea("0:00:000");

		start1 = new JButton("Start");
		stop1 = new JButton("Stop");
		reset1 = new JButton("Reset");
		start2 = new JButton("Start");
		stop2 = new JButton("Stop");
		reset2 = new JButton("Reset");

		menu = new JMenuBar();
		fileMenu = new JMenu("File");
		quit = new JMenuItem("Quit");

		timerMenu1 = new JMenu("Timer 1");
		load1 = new JMenuItem("Load");
		save1 = new JMenuItem("Save");

		timerMenu2 = new JMenu("Timer 2");
		load2 = new JMenuItem("Load");
		save2 = new JMenuItem("Save");

		ButtonListener listener = new ButtonListener();

		start1.addActionListener(listener);
		stop1.addActionListener(listener);
		reset1.addActionListener(listener);
		start2.addActionListener(listener);
		stop2.addActionListener(listener);
		reset2.addActionListener(listener);
		load1.addActionListener(listener);
		save1.addActionListener(listener);
		load2.addActionListener(listener);
		save2.addActionListener(listener);
		quit.addActionListener(listener);

		javaTimer1 = new Timer(1, listener);
		javaTimer2 = new Timer(1, listener);
		javaTimer3 = new Timer(0, listener);

		stop1.setEnabled(false);
		stop1.setFocusPainted(false);
		start1.setFocusPainted(false);
		reset1.setFocusPainted(false);
		stop2.setEnabled(false);
		stop2.setFocusPainted(false);
		start2.setFocusPainted(false);
		reset2.setFocusPainted(false);

		start1.setMaximumSize(new Dimension(90, 20));
		stop1.setMaximumSize(new Dimension(90, 20));
		reset1.setMaximumSize(new Dimension(90, 20));
		start2.setMaximumSize(new Dimension(90, 20));
		stop2.setMaximumSize(new Dimension(90, 20));
		reset2.setMaximumSize(new Dimension(90, 20));

		timeResults1.setFont(new Font("Verdana", Font.BOLD, 20));
		timeResults1.setBackground(null);
		timeResults1.setEditable(false);
		timeResults2.setFont(new Font("Verdana", Font.BOLD, 20));
		timeResults2.setBackground(null);
		timeResults2.setEditable(false);

		group1.setLayout(new BoxLayout(group1, BoxLayout.PAGE_AXIS));
		group3.setBorder(BorderFactory.createTitledBorder("Timer 1"));
		group4.setLayout(new BoxLayout(group4, BoxLayout.PAGE_AXIS));
		group6.setBorder(BorderFactory.createTitledBorder("Timer 2"));

		timerMenu1.add(load1);
		timerMenu1.add(save1);
		timerMenu2.add(load2);
		timerMenu2.add(save2);
		fileMenu.add(quit);
		menu.add(fileMenu);
		menu.add(timerMenu1);
		menu.add(timerMenu2);

		group1.add(start1);
		group1.add(Box.createVerticalStrut(10));
		group1.add(stop1);
		group1.add(Box.createVerticalStrut(10));
		group1.add(reset1);

		group2.add(timeResults1);

		group3.add(Box.createHorizontalStrut(25));
		group3.add(group1);
		group3.add(Box.createHorizontalStrut(25));
		group3.add(group2);
		group3.add(Box.createHorizontalStrut(25));

		group4.add(start2);
		group4.add(Box.createVerticalStrut(10));
		group4.add(stop2);
		group4.add(Box.createVerticalStrut(10));
		group4.add(reset2);

		group5.add(timeResults2);

		group6.add(Box.createHorizontalStrut(25));
		group6.add(group4);
		group6.add(Box.createHorizontalStrut(25));
		group6.add(group5);
		group6.add(Box.createHorizontalStrut(25));

		panel.add(group3);
		panel.add(group6);

		frame.add(panel);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menu);
		frame.pack();
		frame.setVisible(true);
		javaTimer3.start();

	}

	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == start1) {
				javaTimer1.start();
				stop1.setEnabled(true);
				start1.setEnabled(false);
			}
			if (e.getSource() == stop1) {
				javaTimer1.stop();
				stop1.setEnabled(false);
				start1.setEnabled(true);
			}
			if (e.getSource() == reset1) {
				javaTimer1.stop();
				stop1.setEnabled(false);
				start1.setEnabled(true);
				s1 = new StopWatch();
				timeResults1.setText(s1.toString());
			}
			if (e.getSource() == start2) {
				javaTimer2.start();
				stop2.setEnabled(true);
				start2.setEnabled(false);
			}
			if (e.getSource() == stop2) {
				javaTimer2.stop();
				stop2.setEnabled(false);
				start2.setEnabled(true);
			}
			if (e.getSource() == reset2) {
				javaTimer2.stop();
				stop2.setEnabled(false);
				start2.setEnabled(true);
				s2 = new StopWatch();
				timeResults2.setText(s2.toString());
			}
			if (e.getSource() == javaTimer1) {
				timeResults1.setText(s1.toString());
				s1.inc();
			}
			if (e.getSource() == javaTimer2) {
				timeResults2.setText(s2.toString());
				s2.inc();
			}
			if (e.getSource() == load1) {
				s1.load("Timer1.txt");
				timeResults1.setText(s1.toString());
			}
			if (e.getSource() == save1) {
				s1.save("Timer1.txt");
			}
			if (e.getSource() == load2) {
				s2.load("Timer2.txt");
				timeResults2.setText(s2.toString());
			}
			if (e.getSource() == save2) {
				s2.save("Timer2.txt");
			}
			if (e.getSource() == quit) {
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MyTimerPanel p = new MyTimerPanel();
	}
}
