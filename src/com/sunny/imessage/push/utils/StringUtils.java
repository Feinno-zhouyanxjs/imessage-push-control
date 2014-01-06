/**
 * 
 */
package com.sunny.imessage.push.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunny.imessage.push.Activator;

/**
 * 
 * 字符串工具类
 * 
 * Create on Dec 21, 2013 1:23:20 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class StringUtils {

	public final static int PHONE_LEN = 11;

	private final static Logger logger = LoggerFactory.getLogger(StringUtils.class);

	public static String paddingLeft(String src, String ele, int length) {
		if (src.length() < length) {
			String eles = "";
			for (int i = 0; i < length - src.length(); i++) {
				eles += ele;
			}
			return eles + src;
		} else {
			return src;
		}
	}

	public static String getLocation() {
		// String path = null;
		// Location location = Platform.getInstallLocation();
		// if (location != null) {
		// URL url = location.getURL();
		// path = url.getPath();
		// }
		// logger.debug("-----" + path);
		// return path;

		try {
			URL url = FileLocator.toFileURL(Activator.getDefault().getBundle().getResource("."));
			logger.debug("getLocation -- " + url.getPath());
			return url.getPath() + ".." + File.separatorChar;
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
}
