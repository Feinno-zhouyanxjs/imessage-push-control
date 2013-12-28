/**
 * 
 */
package com.sunny.imessage.push.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.service.IService;

/**
 * 
 * 处理客户端处理结果
 * 
 * Create on Dec 21, 2013 1:17:31 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ReportSend implements IAction {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.IAction#getPath()
	 */
	@Override
	public String getPath() {
		return "/IMessage/api/reportsend";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sunny.imessage.push.action.IAction#doing(org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("type");
		String phone = request.getParameter("phone");

		if (phone == null || phone.equals("") || type == null || type.equals(""))
			return;

		String[] phones = phone.split(",");
		if (phones.length == 0)
			return;

		if (type.equals("deliver")) {
			GetPhoneNum.instance.getService().success(phones);
		} else {
			GetPhoneNum.instance.getService().failed(phones);
		}

		// long finished = GetPhoneNum.instance.getFailed() + GetPhoneNum.instance.getSuccess();
		// double rate = finished * 1.0d / GetPhoneNum.instance.getSize();

		// StringBuffer sb = new StringBuffer();
		//
		// for (String p : phones) {
		// String line = p.replaceAll("\\+", "");
		// sb.append(line + "\n");
		// }

		// logger.debug(sb.toString());
		// GetPhoneNum.instance.print(sb.toString());

		JSONObject json = new JSONObject();
		json.put("resultNum", phones.length);
		String responseXml = json.toJSONString();
		response.getOutputStream().write(responseXml.getBytes("utf-8"));
		response.getOutputStream().flush();
	}

}
