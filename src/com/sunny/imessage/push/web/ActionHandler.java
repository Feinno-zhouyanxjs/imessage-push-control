package com.sunny.imessage.push.web;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import com.sunny.imessage.push.action.IAction;

public class ActionHandler extends HandlerWrapper {

	private static final Map<String, IAction> actions = new ConcurrentHashMap<String, IAction>();

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		IAction action = actions.get(target);
		if (action == null) {
			response.getWriter().println("<h1>not found " + target + "</h1>");
		} else {
			action.doing(baseRequest, request, response);
		}
		super.handle(target, baseRequest, request, response);
	}

	public void addActin(IAction action) {
		actions.put(action.getPath(), action);
	}

}
