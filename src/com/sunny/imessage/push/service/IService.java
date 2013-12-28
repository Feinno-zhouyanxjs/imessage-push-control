/**
 * 
 */
package com.sunny.imessage.push.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;

/**
 * 
 * 服务接口
 * 
 * Create on Dec 26, 2013 8:58:39 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public interface IService {

	public long getNum();

	public void doing(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException;

	public void failed(String[] phones);

	public void success(String[] phones);

	public void start(StyledText outputText, String text, Button but) throws IOException;

	public void stop();

	public void print(String line);
}
