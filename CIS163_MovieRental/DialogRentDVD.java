import java.awt.GridLayout;
import java.awt.event.*;
import java.util.GregorianCalendar;

import javax.swing.*;

public class DialogRentDVD extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField renter;
	private JTextField title;
	private JTextField rentedOn;
	private JTextField dueBack;

	private JLabel renterLabel;
	private JLabel titleLabel;
	private JLabel rentedLabel;
	private JLabel dueBackLabel;

	private JButton okButton;
	private JButton cancelButton;
	private DVD unit;

	private GregorianCalendar gCalendarRented;
	private GregorianCalendar gCalendarDue;

	private JDialog dialog;
	private JFrame parentFrame;
	private JPanel panel;

	private boolean closeStatus;

	private int month;
	private int day;
	private int year;
	private int monthDue;
	private int dayDue;
	private int yearDue;

	public DialogRentDVD(JFrame parent, DVD d) {

		gCalendarRented = new GregorianCalendar();
		month = gCalendarRented.get(GregorianCalendar.MONTH);
		day = gCalendarRented.get(GregorianCalendar.DAY_OF_MONTH);
		year = gCalendarRented.get(GregorianCalendar.YEAR);

		gCalendarDue = new GregorianCalendar();
		gCalendarDue.add(GregorianCalendar.DAY_OF_MONTH, 1);
		monthDue = gCalendarDue.get(GregorianCalendar.MONTH);
		dayDue = gCalendarDue.get(GregorianCalendar.DAY_OF_MONTH);
		yearDue = gCalendarDue.get(GregorianCalendar.YEAR);

		dialog = new JDialog();
		renter = new JTextField();
		title = new JTextField();
		rentedOn = new JTextField(month + "/" + day + "/" + year);
		dueBack = new JTextField(monthDue + "/" + dayDue + "/" + yearDue);
		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");
		renterLabel = new JLabel("Your Name:");
		titleLabel = new JLabel("Title of Game:");
		rentedLabel = new JLabel("Rented on Date:");
		dueBackLabel = new JLabel("Due Back:");
		parentFrame = parent;
		unit = d;
		closeStatus = false;

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		panel = new JPanel();

		panel.add(renterLabel);
		panel.add(renter);
		panel.add(titleLabel);
		panel.add(title);
		panel.add(rentedLabel);
		panel.add(rentedOn);
		panel.add(dueBackLabel);
		panel.add(dueBack);
		panel.add(Box.createVerticalStrut(5));
		panel.add(Box.createVerticalStrut(5));
		panel.add(okButton);
		panel.add(cancelButton);

		panel.setLayout(new GridLayout(6, 2));

		dialog.add(panel);

		dialog.setLocationRelativeTo(parentFrame);
		dialog.setModal(true);
		dialog.setTitle("Rent A DVD");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(350, 250);
		dialog.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			if (check()) {
				unit.setTitle(title.getText());
				unit.setNameOfRenter(renter.getText());
				setBoughtDate();
				setDueDate();
				closeStatus = true;
				dialog.dispose();
			}
		}
		if (e.getSource() == cancelButton) {
			dialog.dispose();
		}
	}

	private boolean check() {
		boolean check = false;
		if (renter.getText().length() > 0 && title.getText().length() > 0
			&& rentedOn.getText().length() > 0 && dueBack.getText().length() > 0) {
			check = true;
		}
		return check;
	}

	public DVD getDVD() {
		return unit;
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}

	private void setBoughtDate() {
		String input[] = rentedOn.getText().split("/");
		int inputInt[] = new int[input.length];

		if (!input[0].isEmpty()) {
			for (int i = 0; i < input.length; i++) {
				inputInt[i] = Integer.parseInt(input[i].trim());
			}
		}

		unit.setBought(new GregorianCalendar(inputInt[2], inputInt[0], inputInt[1]));
	}

	private void setDueDate() {
		String input[] = dueBack.getText().split("/");
		int inputInt[] = new int[input.length];

		if (!input[0].isEmpty()) {
			for (int i = 0; i < input.length; i++) {
				inputInt[i] = Integer.parseInt(input[i].trim());
			}
		}

		unit.setDueBack(new GregorianCalendar(inputInt[2], inputInt[0], inputInt[1]));
	}
}
