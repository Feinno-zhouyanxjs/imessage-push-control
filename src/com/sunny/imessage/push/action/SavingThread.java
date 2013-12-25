/**
 * 
 */
package com.sunny.imessage.push.action;

/**
 * 
 * 保存结果线程
 * 
 * Create on Dec 22, 2013 4:05:59 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class SavingThread implements Runnable {

	private final GetPhoneNum task;
	private final String type;

	/**
	 * @param task
	 */
	public SavingThread(GetPhoneNum task, String type) {
		super();
		this.task = task;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		task.save(type);

	}

}
