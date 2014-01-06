/**
 * 
 */
package com.sunny.imessage.push.service;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.view.ConfigDialog;

/**
 * 
 * 自定义扫描
 * 
 * Create on Dec 26, 2013 10:25:28 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ScanCustomService implements IService {

	private LinkedBlockingQueue<Interval> lst = new LinkedBlockingQueue<Interval>();

	// private LinkedBlockingQueue<Long> phones = new LinkedBlockingQueue<Long>();

	protected String text;

	protected ConcurrentHashSet<Long> success = new ConcurrentHashSet<Long>();

	protected ConcurrentHashSet<Long> failed = new ConcurrentHashSet<Long>();

	private StyledText outputText;

	private int count = 0;

	private int sum = 0;

	protected Button but;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private int sendCount = 0;

	private class Interval {
		private long min;
		private long max;
		private LinkedBlockingQueue<Interval> lst;

		/**
		 * @param min
		 * @param max
		 */
		public Interval(long min, long max, LinkedBlockingQueue<Interval> lst) {
			super();
			this.min = min;
			this.max = max;
			this.lst = lst;
		}

		public long getNum() {
			long r = min++;
			if (r == max) {
				lst.remove();
				return max;
			} else
				return r;
		}

	}

	public void addinterval(String str) {
		if (str == null || str.equals("")) {
			return;
		}
		String[] interval = str.split(",");
		if (interval.length > 0) {
			for (String item : interval) {
				String[] mm = item.split("-");
				if (mm.length > 0) {
					lst.add(new Interval(Long.valueOf(mm[0]), Long.valueOf(mm[1]), lst));
				}
			}
		}
	}

	public long getSourcePhone() {
		Interval interval = lst.peek();
		if (interval == null)
			return -1;
		long r = interval.getNum();
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.SendService#getNum()
	 */
	@Override
	public Long getNum() {
		sendCount++;
		return getSourcePhone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#doing(org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {

		Long num = getNum();
		logger.debug("---------------------" + num);

		if (num == null || num == -1) {
			print("任务完成");
			if (but != null)
				but.setEnabled(true);
			response.getOutputStream().write("".getBytes("utf-8"));
			response.getOutputStream().flush();
			return;
		}

		JSONArray lstJSON = new JSONArray();
		JSONObject phone = new JSONObject();
		String strNum = num + "";
		phone.put("phone", strNum);
		lstJSON.add(phone);

		if (lstJSON.size() == 0)
			return;

		JSONObject resJSON = new JSONObject();
		resJSON.put("text", text);
		resJSON.put("phone", lstJSON);
		response.getOutputStream().write(resJSON.toJSONString().getBytes("utf-8"));
		response.getOutputStream().flush();

		// 输出状态信息
		print(num + "-下发成功-------已下发:" + (sendCount + ConfigDialog.sendCount) + "/" + (sum + ConfigDialog.taskCount) + "-------成功:" + (success.size() + ConfigDialog.successCount));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#failed(java.lang.String[])
	 */
	@Override
	public void failed(String[] phones) {
		String[] p = phones;
		for (String phone : p) {
			failed.add(Long.valueOf(phone.replaceAll("+", "")));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#success(java.lang.String[])
	 */
	@Override
	public void success(String[] phones) {
		String[] p = phones;
		for (String phone : p) {
			success.add(Long.valueOf(phone.replaceAll("\\+", "")));
			failed.remove(Long.valueOf(phone.replaceAll("\\+", "")));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#start(org.eclipse.swt.custom.StyledText, java.lang.String, org.eclipse.swt.widgets.Button)
	 */
	@Override
	public void start(StyledText outputText, String text, Button buts) throws IOException {
		this.text = text;
		this.but = buts;
		// phones.clear();
		this.outputText = outputText;
		if (lst.size() == 0) {
			print("请先添加扫描号码段");
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					but.setEnabled(true);
				}
			});
			return;
		}
		// long phone = -1;
		// while ((phone = getSourcePhone()) != -1) {
		// phones.add(phone);
		// }
		sum = lst.size() * 10000;
		print("初始化号段成功，开始下发");

	}

	@Override
	public void stop() {
		lst.clear();
		// phones.clear();
	}

	public ConcurrentHashSet<Long> getFailed() {
		return failed;
	}

	public ConcurrentHashSet<Long> getSuccess() {
		return success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#print(java.lang.String)
	 */
	@Override
	public synchronized void print(final String line) {
		if (outputText == null)
			return;
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (count == 1000)
					outputText.setText("");
				outputText.append(line + "\n");
				count++;
				ScrollBar vsb = outputText.getVerticalBar();
				vsb.setSelection(vsb.getMaximum());
				outputText.setSelection(outputText.getText().length());
			}

		});

	}

}
