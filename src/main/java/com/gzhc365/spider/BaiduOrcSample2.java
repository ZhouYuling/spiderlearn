package com.gzhc365.spider;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

/**
 * 更多信息请参看http://ai.baidu.com/docs#/OCR-Java-SDK/top
 */
public class BaiduOrcSample2 {

	//这个可以自己去百度上申请、每天都有免费次数的
	private static final String APP_ID = "16438814";
	private static final String API_KEY = "7zWsfy40f2hImD2Cm22FIn1R";
	private static final String SECRET_KEY = "7xfzDAyCOZmk4d9ovGrr1H33DFh2S1d1";

	public static void main(String[] args) {

		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("detect_direction", "true");
		options.put("probability", "true");

		String imagePath = System.getProperty("user.dir") + "\\src\\main\\resources\\pic\\test.png";

		// 参数为本地路径
		JSONObject res = client.basicAccurateGeneral(imagePath, options);
		System.out.println(res.toString(2));

		// 参数为二进制数组
		byte[] file = null;
		try {
			file = readFile(imagePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		res = client.basicAccurateGeneral(file, options);
		System.out.println(res.toString(2));

	}

	private static byte[] readFile(String fileName) throws IOException {

		InputStream in = new FileInputStream(fileName);
		byte[] data = toByteArray(in);
		in.close();
		return data;

	}

	private static byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}


}
