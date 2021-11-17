package org.danyuan.application.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.danyuan.application.config.UserConfig;
import org.springframework.stereotype.Service;

@Service
public class FileDelHelp {

	String line = System.lineSeparator();
	Long ln = 0L;
	int times = 0;
	Long[] tem = { 0L, 0L, 0L };

	@Resource
	UserConfig userConfig;

	public void run() throws Exception {
		String path = userConfig.getPath();
		String fileName = userConfig.getFile();
		File file = new File(path.replace("\\", "/") + "/" + fileName);
		if (!file.exists()) {
			throw new Exception("文件未找到，请确定文件路径和文件名称正确！！！");
		}
		InputStreamReader reader = new InputStreamReader(new FileInputStream(file), userConfig.getEncoding());
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(path.replace("\\", "/") + "/" + fileName + ".new")), userConfig.getEncoding());

		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String tempString = null;
			List<String> list = new ArrayList<>();
			while ((tempString = bufferedReader.readLine()) != null) {
				ln++;
				// 显示行号
				if (tempString.equals(userConfig.getDelStartWith()) || tempString.equals(userConfig.getExpStartWith())) {
					System.out.println("第一次出现匹配的行号" + ln);
					list.add(tempString);
					content(bufferedReader, tempString, list, writer);
				} else {
					writer.append(tempString + "\r");
				}

			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

	}

	private void content(BufferedReader reader, String tempString, List<String> list, OutputStreamWriter writer) throws IOException {

		while ((tempString = reader.readLine()) != null) {
			if (tempString.startsWith("*")) {
				list = extracted(list, writer);
			}
			ln++;
			list.add(tempString);

		}
		System.out.println("最后的行号" + ln);
		if (list.size() > 0) {
			extracted(list, writer);
		}
	}

	/**
	 * @param tempString
	 * @param list
	 * @param writer
	 * @param ln
	 * @param tem
	 * @param times
	 * @return
	 * @throws IOException
	 */
	private List<String> extracted(List<String> list, OutputStreamWriter writer) throws IOException {
		boolean f = true;
		boolean start = false;
		for (int a = 0; a < list.size(); a++) {
			String tempString = list.get(a);
			if (tempString.equals(userConfig.getDelStartWith()) || tempString.equals(userConfig.getExpStartWith())) {
				start = true;
			}
			if (start && (tempString.startsWith(userConfig.getDelContextStartWith()) || tempString.startsWith(userConfig.getExpContextStartWith()))) {
				f = false;
			}
		}
		if (f && list.size() > 0) {
			if (times == 3) {
				System.out.println("连续出现匹配行号后还有内容行号：" + ln);
			}
			times = 0;
			tem = new Long[] { 0L, 0L, 0L };
			for (String string : list) {
				// System.out.println(string);
				if (string == null) {
					string = "";
				}
				writer.append(string + line);
			}
		} else if (times < 3) {
			tem[times] = ln - list.size();
			times++;
			if (times == 3) {
				for (Long long1 : tem) {
					System.out.println("连续出现匹配行号" + long1);
				}
			}
		}
		list = new ArrayList<>();
		return list;
	}

}
