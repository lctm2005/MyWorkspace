package observer;

import java.util.Observable;
import java.util.Observer;

public class AObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		Event event = (Event)o;
		System.out.println(event.getName());
	}

}
