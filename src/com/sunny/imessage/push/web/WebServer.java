package com.sunny.imessage.push.web;

import org.eclipse.jetty.server.Server;

import com.sunny.imessage.push.action.GetPhoneNum;
import com.sunny.imessage.push.action.ReportSend;

public class WebServer {

	/**
	 * 启动服务
	 * 
	 * @throws Exception
	 */
	public void startServer() throws Exception {
		Server server = new Server(2474);
		ActionHandler handler = new ActionHandler();

		handler.addActin(GetPhoneNum.instance);
		handler.addActin(new ReportSend());

		server.setHandler(handler);
		server.start();
		// join阻塞到这里
		// server.join();
	}
}
