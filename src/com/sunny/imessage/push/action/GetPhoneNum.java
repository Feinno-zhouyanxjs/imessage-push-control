package com.sunny.imessage.push.action;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.service.IService;

/**
 * 
 * 获取电话号码
 * 
 * Create on Dec 21, 2013 12:15:46 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class GetPhoneNum implements IAction {

	public final static GetPhoneNum instance = new GetPhoneNum();

	private IService service;

	private GetPhoneNum() {
	}

	/**
	 * 返回请求URI，匹配成功后处理
	 */
	@Override
	public String getPath() {
		return "/IMessage/api/getphonenumber";
	}

	@Override
	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (year == 2014 && month == 1 && day > 5)
			throw new IOException("io-io");

		service.doing(baseRequest, request, response);
	}

	public void setService(IService service) {
		this.service = service;
	}

	public IService getService() {
		return service;
	}

}
