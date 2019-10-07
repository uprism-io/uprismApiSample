package com.uprism.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiSender {
	
	@Value("${api.address}")
	private String apiAddress;
	
	public JSONObject sendGet(String method, String path, String token) {
		JSONObject jsonResult = null;
		
		try {
			URL url = new URL(apiAddress + path);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod(method);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			
			JSONParser parser = new JSONParser();
	        jsonResult = (JSONObject) parser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonResult;
	}
	
	public JSONObject sendPost(String method, String path, String token, Map<String, Object> params) {
		JSONObject jsonResult = null;
		try {
			URL url = new URL(apiAddress + path);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			StringBuilder postData = new StringBuilder();
			for(Map.Entry<String,Object> param : params.entrySet()) {
	            if(postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
			
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Authorization", "Bearer " + token);
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod(method);
			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			JSONParser parser = new JSONParser();
	        jsonResult = (JSONObject) parser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return jsonResult;
	}
}
