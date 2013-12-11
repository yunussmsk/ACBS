import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.Timer;

public class Simulator extends JFrame implements ActionListener {

	int iterator = -1;
	int t = 0;
	int tprog = 0;
	int totalCalls = 0;

	Operator a[] = new Operator[30];

	JButton call = new JButton("Call");
	JButton end = new JButton("End");
	JLabel l1 = new JLabel("The number of operators in the bank = " + a.length);
	JLabel l2 = new JLabel(
			"The probability distribution that governs dial-in attempts.");
	JLabel l3 = new JLabel(
			"The probability distribution that governs connect time");
	JLabel l4 = new JLabel();

	Timer timer = new Timer(1000, this);
	Timer progTime = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int temp = totalCalls;

			tprog++;
			for (int i = 0; i < a.length; i++) {
				temp += a[i].getTotalCall();
			}

			l2.setText("The probability distribution that governs dial-in attempts = "
					+ new DecimalFormat("0.00").format((double) temp / tprog
							* 60));

			l4.setText("The simulation is running for " + tprog + " seconds");

//			for (Operator x : a)
//				System.out.println(x.isBusy());

		}
	});

	public Simulator() {
		super("ACBS BANK");
		setSize(400, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(2, 1));

		for (int i = 0; i < a.length; i++) {
			a[i] = new Operator();
		}

		JPanel p1 = new JPanel(new GridLayout(4, 1));
		JPanel p2 = new JPanel(new BorderLayout());

		p1.add(l1);
		p1.add(l2);
		p1.add(l3);
		p1.add(l4);

		JPanel p = new JPanel(new FlowLayout());
		p.add(call);
		p.add(end);

		end.setEnabled(false);
		call.addActionListener(this);
		end.addActionListener(this);

		p2.add(p, BorderLayout.CENTER);
		add(p1);
		add(p2);
		progTime.start();
	}

	public static void main(String args[]) {

		Simulator s = new Simulator();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == call) {

			totalCalls++;

			try {
				for (iterator = 0;; iterator++)
					if (!a[iterator].isBusy()) {
						a[iterator].call();
						timer.start();
						break;
					}
				call.setEnabled(false);
				end.setEnabled(true);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,
						"All the operators are busy now..");
				iterator = -1;
			}
		}
		if (iterator != -1)
			t++;
		if (e.getSource() == end) {
			call.setEnabled(true);
			end.setEnabled(false);
			a[iterator].end();
			timer.stop();
			t = 0;
		}

		l3.setText("The probability distribution that governs connect time = "
				+ ((t / 60 < 10) ? "0" : "") + (t / 60) + ":"
				+ ((t % 60 < 10) ? "0" : "") + (t % 60));

	}
}
