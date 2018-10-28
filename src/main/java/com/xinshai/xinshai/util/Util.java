package com.xinshai.xinshai.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {


	/*public static String encode(String password) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}
*/
	public static List<Map<String, Object>> DataToTree(List<Map<String, Object>> data,
													   String parentKey, String parentValue, String primaryKey,
													   String propertyName) {
		List<Map<String, Object>> dic = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : data) {
			if (map.get(parentKey) != null
					&& map.get(parentKey).equals(parentValue)) {
				rows.add(map);
			} else if (map.get(parentKey) == parentValue) {
				rows.add(map);
			}
		}

		for (Map<String, Object> map : rows) {
			Map<String, Object> dicc = new HashMap<String, Object>();

			for (Entry<String, Object> entry : map.entrySet()) {
				dicc.put(entry.getKey(), entry.getValue());

			}

			dicc.put(propertyName, DataToTree(data, parentKey, map.get(primaryKey).toString(),primaryKey, propertyName));

			dic.add(dicc);
		}
		if (dic.size() == 0)
			return null;

		return dic;
	}



}