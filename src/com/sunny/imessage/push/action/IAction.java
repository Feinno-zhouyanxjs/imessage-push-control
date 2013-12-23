package com.sunny.imessage.push.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

public interface IAction {

	public String getPath();

	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
