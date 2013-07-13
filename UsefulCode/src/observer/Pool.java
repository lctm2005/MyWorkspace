package observer;

import java.util.Observable;

/**
 * 票池
 * 
 * @author James-li
 *
 * 2013-6-2
 */
public class Pool extends Observable{

	private static int TICKET_NUM = 100;				//票池中的票数
	private static final int LOWEST_NUM = 20;	//最低票数

	/**
	 * 通知管理员
	 */
	private void notifyManager() {
        super.setChanged();  
		notifyObservers();
	}
	
	/**
	 * 售票
	 * @throws PoolHasEmptyException 
	 */
	public void sellTicket() throws PoolHasEmptyException {
		if(TICKET_NUM <= 0) {
			throw new PoolHasEmptyException();
		}
		TICKET_NUM--;
		if(TICKET_NUM < LOWEST_NUM) {
			notifyManager();
		}
	}
	
	/**
	 * 添票
	 * @param num	 增加的票数
	 */
	public void addTickets(int num) {
		TICKET_NUM += num;
		System.out.println("Add " + num + " tickets");
	}
	
	public static void main(String[] args) {
		Manager observer = new Manager();
		Pool pool = new Pool();
		pool.addObserver(observer);
		try {
			while(true) {
					pool.sellTicket();
			}
		} catch (PoolHasEmptyException e) {
			e.printStackTrace();
		}
	}
}
