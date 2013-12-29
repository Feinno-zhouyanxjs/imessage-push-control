/**
 * 
 */
package com.sunny.imessage.push.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jetty.util.ConcurrentHashSet;

/**
 * 
 * 文件读写
 * 
 * Create on Dec 21, 2013 11:08:07 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class FileUtils {

	public final static String DIR = "data/";

	public final static String successFile = "success.txt";

	public final static String failedFile = "failed.txt";

	public final static String scanFile = "scan.txt";

	public static List<Long> readPhones(String file) throws IOException {
		FileReader fr = new FileReader(new File(file));
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		List<Long> set = Collections.synchronizedList(new ArrayList<Long>());
		try {
			while ((line = br.readLine()) != null) {
				long num = Long.valueOf(line);
				set.add(num);
			}
		} finally {
			br.close();
		}
		return set;
	}

	public static void writePhones(ConcurrentHashSet<Long> set, String file) throws IOException {
		if (file == null || file.equals(""))
			return;
		FileWriter fw = new FileWriter(new File(file));
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			if (set.size() == 0) {
				bw.append("no phone numbers.");
				return;
			} else {
				Iterator<Long> iter = set.iterator();
				while (iter.hasNext()) {
					bw.append(iter.next() + "\n");
				}
			}
		} finally {
			bw.flush();
			bw.close();
		}

	}

	public static File[] files(String path) {
		File file = new File(path);
		return file.listFiles();
	}

}
