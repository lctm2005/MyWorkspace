package observer;

import java.util.Observable;

public class Event extends Observable{

	private String name;
	
	public Event(String name) {
		this.name = name;
	}
	
	public void happen() {
        super.setChanged();  
		notifyObservers();
	}
	
	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		AObserver observer = new AObserver();
		Event a = new Event("fire");
		Event b = new Event("shoot");
		a.addObserver(observer);
		b.addObserver(observer);
		a.happen();
		b.happen();
	}
}
