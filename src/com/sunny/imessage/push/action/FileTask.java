/**
 * 
 */
package com.sunny.imessage.push.action;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.custom.StyledText;

/**
 * 
 * 文件任务
 * 
 * Create on Dec 22, 2013 2:45:47 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class FileTask implements Task {

	private Iterator<Long> iter;

	public String type;

	private String text;

	private int size;

	private List<Long> phones;

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param phones
	 *            the phones to set
	 */
	public void setPhones(List<Long> phones) {
		this.iter = phones.iterator();
		this.size = phones.size();
		this.phones = phones;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#getType()
	 */
	@Override
	public String getType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#getNextPhone()
	 */
	@Override
	public synchronized long getNextPhone() {
		if (iter.hasNext()) {
			long num = iter.next();
			iter.remove();
			return num;
		} else {
			if (phones.size() > 0) {
				iter = phones.iterator();
				return iter.next();
			} else
				return -1;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#getText()
	 */
	@Override
	public String getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#size()
	 */
	@Override
	public long size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#remainPhone()
	 */
	@Override
	public long remainPhone() {
		return phones.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.Task#removePhone(java.lang.String)
	 */
	@Override
	public void removePhone(Long phone) {
		phones.remove(phone);

	}

}
