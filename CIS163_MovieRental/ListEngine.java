import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.AbstractListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ListEngine extends AbstractListModel<DVD> {

	private static final long serialVersionUID = 1L;

	private ArrayList<DVD> listDVDs;

	public ListEngine() {
		listDVDs = new ArrayList<DVD>();
	}

	public DVD getElementAt(int i) {
		return listDVDs.get(i);
	}

	public int getSize() {
		return listDVDs.size();
	}

	public void add(DVD dvd) {
		listDVDs.add(dvd);
		fireIntervalAdded(listDVDs, 0, 0);
	}

	public void delete(int i) {
		listDVDs.remove(i);
		fireContentsChanged(listDVDs, 0, 0);
	}

	public void update() {
		fireContentsChanged(listDVDs, 0, 0);
	}

	@SuppressWarnings("unchecked")
	public void load() throws ClassNotFoundException {
		ObjectInputStream fileIn;
		try {
			fileIn = new ObjectInputStream(new FileInputStream("saved.ser"));
			listDVDs = (ArrayList<DVD>) fileIn.readObject();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		update();

	}

	public void save() {
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream("saved.ser"));
			out.writeObject(listDVDs);
			out.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveText() {
		PrintWriter out = null;

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Load");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter(chooser.getSelectedFile()
					.toString())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (DVD d : listDVDs) {

				int monthBought = d.getBought().get(2);
				int dayBought = d.getBought().get(5);
				int yearBought = d.getBought().get(1);

				int monthDue = d.getDueBack().get(2);
				int dayDue = d.getDueBack().get(5);
				int yearDue = d.getDueBack().get(1);

				if (d instanceof Game) {
					String player;
					PlayerType type = ((Game) d).getPlayer();

					if (type == PlayerType.PS4) {
						player = "PS4";
					} else if (type == PlayerType.XBOX360) {
						player = "XBOX360";
					} else {
						player = "XBOX720";
					}

					out.println(d.getNameOfRenter() + ";" + d.getTitle() + ";" + monthBought + ";"
						+ dayBought + ";" + yearBought + ";" + monthDue + ";" + dayDue + ";"
						+ yearDue + ";" + player);
				} else {
					out.println(d.getNameOfRenter() + ";" + d.getTitle() + ";" + monthBought + ";"
						+ dayBought + ";" + yearBought + ";" + monthDue + ";" + dayDue + ";"
						+ yearDue);
				}
			}
			out.close();
		} else {
			System.out.println("No Selection");
		}
	}

	public void loadText() {
		Scanner fileReader;
		listDVDs.clear();

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Load");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				fileReader = new Scanner(new File(chooser.getSelectedFile().toString()));

				while (fileReader.hasNext()) {
					String[] loaded = fileReader.next().split(";");

					int monthBought = Integer.parseInt(loaded[2]);
					int dayBought = Integer.parseInt(loaded[3]);
					int yearBought = Integer.parseInt(loaded[4]);

					int monthDue = Integer.parseInt(loaded[5]);
					int dayDue = Integer.parseInt(loaded[6]);
					int yearDue = Integer.parseInt(loaded[7]);

					if (loaded.length == 9) {
						Game d = new Game();

						PlayerType type;

						if (loaded[8].equals("PS4")) {
							type = PlayerType.PS4;
						} else if (loaded[8].equals("XBOX360")) {
							type = PlayerType.XBOX360;
						} else {
							type = PlayerType.XBOX720;
						}

						((Game) d).setPlayer(type);
						d.setNameOfRenter(loaded[0]);
						d.setTitle(loaded[1]);

						d.setBought(new GregorianCalendar(yearBought, monthBought, dayBought));
						d.setDueBack(new GregorianCalendar(yearDue, monthDue, dayDue));
						add(d);
					} else {
						DVD d = new DVD();
						d.setNameOfRenter(loaded[0]);
						d.setTitle(loaded[1]);

						d.setBought(new GregorianCalendar(yearBought, monthBought, dayBought));
						d.setDueBack(new GregorianCalendar(yearDue, monthDue, dayDue));
						add(d);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No Selection");
		}

	}

	public void search(String term) {
		int length = term.length();
		String s = "";

		for (DVD d : listDVDs) {
			for (int i = 0; i < d.getTitle().length() - length; i++) {
				if (d.getTitle().substring(i, i + length).equals(term)) {
					s += d.getTitle() + "\n";
				}
			}
		}

		JOptionPane.showMessageDialog(null, "These titles matched your search\n" + s);
	}

	public boolean checkLate(GregorianCalendar g) {
		boolean late = false;
		GregorianCalendar current = new GregorianCalendar();
		current.set(GregorianCalendar.HOUR, 0);
		current.set(GregorianCalendar.HOUR_OF_DAY, 0);
		current.set(GregorianCalendar.MINUTE, 0);
		current.set(GregorianCalendar.SECOND, 0);
		current.set(GregorianCalendar.MILLISECOND, 0);
		g.set(GregorianCalendar.HOUR, 0);
		g.set(GregorianCalendar.HOUR_OF_DAY, 0);
		g.set(GregorianCalendar.MINUTE, 0);
		g.set(GregorianCalendar.SECOND, 0);
		g.set(GregorianCalendar.MILLISECOND, 0);

		if (current.compareTo(g) > 0) {
			late = true;
		}

		return late;
	}

	public void checkAll(String s) {
		GregorianCalendar g = new GregorianCalendar();
		String[] date = s.split("/");
		String late = "";

		g.set(GregorianCalendar.MONTH, Integer.parseInt(date[0]));
		g.set(GregorianCalendar.DAY_OF_MONTH, Integer.parseInt(date[1]));
		g.set(GregorianCalendar.YEAR, Integer.parseInt(date[2]));

		for (DVD d : listDVDs) {
			if (d.getDueBack().compareTo(g) > 0) {
				late += d.getTitle() + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, "These titles are late\n" + late);
	}
}
