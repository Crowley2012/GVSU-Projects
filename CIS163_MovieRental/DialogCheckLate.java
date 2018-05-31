import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogCheckLate extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField input;

	private JButton okButton;
	private JButton cancelButton;

	private JDialog dialog;
	private JFrame parentFrame;
	private JPanel panel;

	private boolean closeStatus;

	private String date;

	public DialogCheckLate(JFrame parent) {

		input = new JTextField("MM/DD/YYYY");
		okButton = new JButton("Check");
		cancelButton = new JButton("Cancel");
		dialog = new JDialog();
		closeStatus = false;

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		panel = new JPanel();

		panel.add(input);
		panel.add(okButton);
		panel.add(cancelButton);

		panel.setLayout(new GridLayout(3, 1));

		dialog.add(panel);

		dialog.setLocationRelativeTo(parentFrame);
		dialog.setModal(true);
		dialog.setTitle("Check Late Titles");
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialog.setSize(250, 150);
		dialog.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			date = input.getText();
			closeStatus = true;
			dialog.dispose();
		}
		if (e.getSource() == cancelButton) {
			dialog.dispose();
		}
	}

	public String getDate() {
		return date;
	}

	public boolean getCloseStatus() {
		return closeStatus;
	}
}
