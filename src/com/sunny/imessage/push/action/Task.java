/**
 * 
 */
package com.sunny.imessage.push.action;

/**
 * 
 * 下发任务
 * 
 * Create on Dec 22, 2013 11:03:02 AM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public interface Task {

	public final static String SEND = "send";
	public final static String SCAN = "scan";

	public String getType();

	public long getNextPhone();

	public String getText();

	public long size();
	
	public long remainPhone();
	
	public void removePhone(Long phone);

}
