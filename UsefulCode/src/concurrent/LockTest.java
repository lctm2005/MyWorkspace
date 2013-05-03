package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试 java.util.concurrent.locks.Lock 的使用
 * 
 * @author James-li
 *
 * 2013-3-15
 */
public class LockTest {

	private final Lock lock = new ReentrantLock();

	public void put(int i) {
		// lock.lockInterruptibly();

		// 尝试获取锁，若无法获取，不再执行该线程
		if (lock.tryLock()) {
			try {
				System.out.println("put" + i);
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		// 时间锁等候
		try {
			// 尝试获取锁，若3S内没有获取，不再执行该线程
			if (lock.tryLock(3, TimeUnit.SECONDS)) {
				try {
					System.out.println("put" + i);
					// 锁占用时间 
					TimeUnit.SECONDS.sleep(1);		
				}
				catch (InterruptedException e1) {
					e1.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// 可中断锁等候
		// Thread.interrupt()方法不会中断一个正在运行的线程。这一方法实际上完成的是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态。
		// 更确切的说，如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，那么，它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态。
		// 因此，如果线程被上述几种方法阻塞，正确的停止线程方式是设置共享变量，并调用interrupt()（注意变量应该先设置）。
		// 如果线程没有被阻塞，这时调用interrupt()将不起作用；否则，线程就将得到异常（该线程必须事先预备好处理此状况），接着逃离阻塞状态。
		// 在任何一种情况中，最后线程都将检查共享变量然后再停止。
		try {
			lock.lockInterruptibly();
			System.out.println("put" + i);
			// 锁占用时间 
			TimeUnit.SECONDS.sleep(10);	
		} catch (InterruptedException e) {
			System.out.println("Interrupted" + i);
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public static class MyThread implements Runnable {
		private LockTest test;
		private int i;

		MyThread(LockTest test, int i) {
			this.test = test;
			this.i = i;
		}

		@Override
		public void run() {
			test.put(i);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LockTest test = new LockTest();
		Thread a = new Thread(new MyThread(test, 1));
		a.setName("Thread-1");
		Thread b = new Thread(new MyThread(test, 2));
		b.setName("Thread-2");
		Thread c = new Thread(new MyThread(test, 3));
		c.setName("Thread-3");
		a.start();
		b.start();
		c.start();
		
		// 中断线程
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		b.interrupt();
		c.interrupt();
	}

}
