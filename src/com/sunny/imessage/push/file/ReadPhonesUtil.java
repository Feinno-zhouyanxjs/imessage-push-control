/**
 * 
 */
package com.sunny.imessage.push.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.jetty.util.ConcurrentHashSet;

/**
 * 
 * 生成号段
 * 
 * Create on Dec 28, 2013 5:51:11 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class ReadPhonesUtil {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader(new File("/Users/sunny/Desktop/new-phone"));
		BufferedReader br = new BufferedReader(fr);
		String line = null;

		HashMap<String, HashMap<String, HashMap<String, ConcurrentHashSet<Long>>>> map = new HashMap<String, HashMap<String, HashMap<String, ConcurrentHashSet<Long>>>>();

		try {
			while ((line = br.readLine()) != null) {
				String[] items = line.split(" ");
				if (items.length == 3 && items[1].trim().equals("北京")) {
					ArrayList<String> lst = new ArrayList<String>();
					lst.add(items[0]);
					lst.add(items[1]);
					lst.add(items[1]);
					lst.add(items[2]);
					items = lst.toArray(new String[4]);
				}

				if (items.length == 3 && items[1].trim().startsWith("重庆")) {
					ArrayList<String> lst = new ArrayList<String>();
					lst.add(items[0]);
					lst.add("重庆");
					lst.add(items[1].trim().replaceAll("重庆", ""));
					lst.add(items[2]);
					items = lst.toArray(new String[4]);
				}

				if (items.length == 5) {
					ArrayList<String> lst = new ArrayList<String>();
					lst.add(items[0]);
					lst.add(items[1]);
					lst.add(items[2]);
					lst.add(items[3].trim() + items[4]);
					items = lst.toArray(new String[4]);
				}

				if (items.length == 4) {
					String sheng = items[1];
					String shi = items[2];
					String shang = items[3];
					String hao = items[0];

					HashMap<String, HashMap<String, ConcurrentHashSet<Long>>> shengmap = map.get(sheng);
					if (shengmap == null) {
						shengmap = new HashMap<String, HashMap<String, ConcurrentHashSet<Long>>>();
						map.put(sheng.trim(), shengmap);
					}

					HashMap<String, ConcurrentHashSet<Long>> shimap = shengmap.get(shi);
					if (shimap == null) {
						shimap = new HashMap<String, ConcurrentHashSet<Long>>();
						shengmap.put(shi.trim(), shimap);
					}

					ConcurrentHashSet<Long> phones = shimap.get(shang);
					if (phones == null) {
						phones = new ConcurrentHashSet<Long>();
						shimap.put(shang.trim(), phones);
					}

					phones.add(Long.valueOf(hao));
				} else {
					System.out.println(line + " error");
				}
			}
			System.out.println(map.size());

			String dir = "/Users/sunny/Desktop/data";

			Iterator<Entry<String, HashMap<String, HashMap<String, ConcurrentHashSet<Long>>>>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, HashMap<String, HashMap<String, ConcurrentHashSet<Long>>>> shenge = iter.next();
				String sheng = shenge.getKey();
				String fp = dir + "/" + sheng;
				HashMap<String, HashMap<String, ConcurrentHashSet<Long>>> shimap = shenge.getValue();
				Iterator<Entry<String, HashMap<String, ConcurrentHashSet<Long>>>> shiiter = shimap.entrySet().iterator();
				while (shiiter.hasNext()) {
					Entry<String, HashMap<String, ConcurrentHashSet<Long>>> shie = shiiter.next();
					String shi = shie.getKey();
					String sp = fp + "/" + shi;
					HashMap<String, ConcurrentHashSet<Long>> shangmap = shie.getValue();
					Iterator<Entry<String, ConcurrentHashSet<Long>>> shangiter = shangmap.entrySet().iterator();
					while (shangiter.hasNext()) {
						Entry<String, ConcurrentHashSet<Long>> shange = shangiter.next();
						String shang = shange.getKey();
						ConcurrentHashSet<Long> ps = shange.getValue();

						new File(sp).mkdirs();
						FileUtils.writePhones(ps, sp + "/" + shang.trim());
					}
				}
			}
		} finally {
			br.close();
		}

	}

}
