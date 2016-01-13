package observer;

import java.util.Observable;
import java.util.Observer;

public class Manager implements Observer {

	private static final int ADD_TICKET_NUM = 50;
	@Override
	public void update(Observable o, Object arg) {
		Pool pool = (Pool)o;
		pool.addTickets(ADD_TICKET_NUM);
	}

}
