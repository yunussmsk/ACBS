import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Operator implements ActionListener {
	private boolean busy = false;
	private int callTime = 0;
	private int totalCall = 0;
	private int totalTime = 0;
	private int randomCallTime = -1;
	private Timer time = new Timer(1000, this);

	public Operator() {
		time.start();
	}

	public void call() {
		busy = true;
		totalCall++;
	}

	public void end() {
		busy = false;
		totalTime += callTime;
		callTime = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (busy){
			callTime++;
			if (randomCallTime == callTime)
				end();
		}
		else if ((int) (Math.random() * 15) == 5) {
			call();
			randomCallTime = (int) (30 + Math.random() * 121);
		}

	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public int getTotalCall() {
		return totalCall;
	}

	public void setTotalCall(int totalCall) {
		this.totalCall = totalCall;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
}
