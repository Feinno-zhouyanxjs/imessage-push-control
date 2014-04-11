/**
 * 
 */
package com.sunny.imessage.push.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import com.sunny.imessage.push.file.FileUtils;
import com.sunny.imessage.push.view.ConfigDialog;

/**
 * 
 * 
 * Create on Dec 26, 2013 9:03:40 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class SendService implements IService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final LinkedBlockingQueue<Long> phones = new LinkedBlockingQueue<Long>();

	protected String text;

	protected ConcurrentHashSet<Long> success = new ConcurrentHashSet<Long>();

	protected ConcurrentHashSet<Long> failed = new ConcurrentHashSet<Long>();

	private StyledText outputText;

	private int count = 0;

	private int sum = 0;

	private ArrayList<String> files = new ArrayList<String>();

	protected Button but;

	public void addFile(String file) {
		files.add(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#getNum()
	 */
	@Override
	public Long getNum() {
		return phones.poll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#doing(org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// String count = request.getParameter("count");
		// int max = 1;
		// if (count != null && !count.equals(""))
		// max = Integer.valueOf(count);

		JSONArray lstJSON = new JSONArray();
		logger.debug(ConfigDialog.phones + "-----------------------");
		for (int i = 0; i < ConfigDialog.phones; i++) {
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

			JSONObject phone = new JSONObject();
			String strNum = num + "";
			phone.put("phone", strNum);
			lstJSON.add(phone);

			// 输出状态信息
			print(num + "-下发成功-------已下发:" + (sendCount() + ConfigDialog.sendCount) + "/" + (sum + ConfigDialog.taskCount) + "-------成功:" + (success.size() + ConfigDialog.successCount));
		}

		if (lstJSON.size() == 0)
			return;

		JSONObject resJSON = new JSONObject();
		resJSON.put("text", text);
		resJSON.put("phone", lstJSON);
		response.getOutputStream().write(resJSON.toJSONString().getBytes("utf-8"));
		response.getOutputStream().flush();

	}

	public int sendCount() {
		return sum - phones.size();
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

	public ConcurrentHashSet<Long> getFailed() {
		return failed;
	}

	public ConcurrentHashSet<Long> getSuccess() {
		return success;
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
		phones.clear();
		this.outputText = outputText;
		if (files.size() == 0) {
			print("请先添加号码文件");
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					but.setEnabled(true);
				}

			});
			return;
		}
		for (String file : files) {
			List<Long> f = FileUtils.readPhones(file);
			for (Long p : f) {
				phones.add(p);
				failed.add(p);
			}
		}
		sum = phones.size();
		// logger.info("reading files has finished.");
		print("读取文件成功，开始下发");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.service.IService#stop()
	 */
	@Override
	public void stop() {
		phones.clear();
		files.clear();
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
