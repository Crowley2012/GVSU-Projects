import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIRentalStore extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame frame;

	private JMenuBar menu;

	private JMenu fileMenu;
	private JMenuItem openSerial;
	private JMenuItem saveSerial;
	private JMenuItem openText;
	private JMenuItem saveText;
	private JMenuItem quit;

	private JMenu actionMenu;
	private JMenuItem rentDVD;
	private JMenuItem rentGame;
	private JMenuItem ret;
	private JMenuItem search;
	private JMenuItem check;

	private JList<DVD> list;
	private JScrollPane scroll;

	private ListEngine engine;

	public GUIRentalStore() {

		engine = new ListEngine();
		list = new JList<DVD>(engine);

		scroll = new JScrollPane();
		scroll.setViewportView(list);

		frame = new JFrame();

		menu = new JMenuBar();

		fileMenu = new JMenu("File");
		openSerial = new JMenuItem("Open Serial ..");
		saveSerial = new JMenuItem("Save Serial ..");
		openText = new JMenuItem("Open Text ..");
		saveText = new JMenuItem("Save Text ..");
		quit = new JMenuItem("Quit");

		actionMenu = new JMenu("Action");
		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		ret = new JMenuItem("Return");
		search = new JMenuItem("Search");
		check = new JMenuItem("Check Late");

		openSerial.addActionListener(this);
		saveSerial.addActionListener(this);
		openText.addActionListener(this);
		saveText.addActionListener(this);
		quit.addActionListener(this);
		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		ret.addActionListener(this);
		search.addActionListener(this);
		check.addActionListener(this);

		fileMenu.add(openSerial);
		fileMenu.add(saveSerial);
		fileMenu.addSeparator();
		fileMenu.add(openText);
		fileMenu.add(saveText);
		fileMenu.addSeparator();
		fileMenu.add(quit);

		actionMenu.add(rentDVD);
		actionMenu.add(rentGame);
		actionMenu.addSeparator();
		actionMenu.add(ret);
		actionMenu.addSeparator();
		actionMenu.add(search);
		actionMenu.addSeparator();
		actionMenu.add(check);

		menu.add(fileMenu);
		menu.add(actionMenu);

		frame.add(scroll);

		frame.setTitle("RedBox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menu);
		frame.setSize(600, 300);
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openSerial) {
			try {
				engine.load();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == saveSerial) {
			engine.save();
		}
		if (e.getSource() == openText) {
			engine.loadText();
		}
		if (e.getSource() == saveText) {
			engine.saveText();
		}
		if (e.getSource() == quit) {
			System.exit(0);
		}
		if (e.getSource() == rentDVD) {
			DVD dvd = new DVD();
			DialogRentDVD dialog = new DialogRentDVD(this, dvd);
			if (dialog.getCloseStatus()) {
				engine.add(dialog.getDVD());
			}
		}
		if (e.getSource() == rentGame) {
			Game game = new Game();
			DialogRentGame dialog = new DialogRentGame(this, game);
			if (dialog.getCloseStatus()) {
				engine.add(dialog.getGame());
			}
		}
		if (e.getSource() == ret) {
			message();
		}
		if (e.getSource() == search) {
			DialogSearch dialog = new DialogSearch(this);
			if (dialog.getCloseStatus()) {
				engine.search(dialog.getTerm());
			}
		}
		if (e.getSource() == check) {
			DialogCheckLate dialog = new DialogCheckLate(this);
			if (dialog.getCloseStatus()) {
				engine.checkAll(dialog.getDate());
			}
		}
	}

	private void message() {
		DVD d = engine.getElementAt(list.getSelectedIndex());
		if (d instanceof Game) {
			if (engine.checkLate(d.getDueBack())) {
				JOptionPane.showMessageDialog(frame, "Thank You, " + d.getNameOfRenter()
					+ "\nfor returning " + d.getTitle() + ", you owe: $10.00");
			} else {
				JOptionPane.showMessageDialog(frame, "Thank You, " + d.getNameOfRenter()
					+ "\nfor returning " + d.getTitle() + "late, you owe: $5.00");
			}
		} else {
			if (engine.checkLate(d.getDueBack())) {
				JOptionPane.showMessageDialog(frame, "Thank You, " + d.getNameOfRenter()
					+ "\nfor returning " + d.getTitle() + ", you owe: $2.00");
			} else {
				JOptionPane.showMessageDialog(frame, "Thank You, " + d.getNameOfRenter()
					+ "\nfor returning " + d.getTitle() + ", you owe: $1.20");
			}
		}
		engine.delete(list.getSelectedIndex());
	}

	public static void main(String[] args) {
		new GUIRentalStore();
	}

}
