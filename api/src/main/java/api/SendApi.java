package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;

public class SendApi {
	private static final String CLIENT_KEY = "CAB0527D-14BD-4FFF-A25B-ACF2E141355C";
	private static final String CLIENT_SECRET = "89368D83-EE39-48BA-9C04-3AF9B47C3F78";
	
	public String getAccessToken(String apiAddress) {
		String accessToken = "";
		Map<String, Object> params = new HashMap<>();
		params.put("grant_type", "client_credentials");
		
		try {
			URL url = new URL(apiAddress + "oauth/token");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			Encoder encoder = Base64.getEncoder();
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
			con.setRequestProperty("Authorization", "Basic " + encoder.encodeToString((CLIENT_KEY + ":" + CLIENT_SECRET).getBytes("UTF-8")));
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("POST");
			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine, result = "";
	        while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
	        
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonResult = (JSONObject) jsonParser.parse(result);
	        
	        accessToken = (String) jsonResult.get("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return accessToken;
	}
	
	public JSONObject getRequest(String url, String parameter, @Value("${api.address}") String apiAddress) {
		
		return new JSONObject();
	}
	
	public JSONObject makeConf(String userId, String confTitle, String confAgenda, String apiAddress) {
		JSONObject conf = null;
		Map<String, Object> params = new HashMap<>();
		params.put("title", confTitle);
		params.put("agenda", confAgenda);
		
		try {
			URL url = new URL(apiAddress + "v1/users/" + userId + "/conference");
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
			con.setRequestProperty("Authorization", "Bearer " + getAccessToken(apiAddress));
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("POST");
			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			
			JSONParser jsonParser = new JSONParser();
	        conf = (JSONObject) jsonParser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conf;
	}
	
	public JSONObject getConf(String apiAddress) {
		JSONObject conf = null;
		
		try {
			URL url = new URL(apiAddress + "v1/users/ekim@uprism.com/conference");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			StringBuilder postData = new StringBuilder();
			
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Authorization", "Bearer " + getAccessToken(apiAddress));
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("GET");
//			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			
			JSONParser jsonParser = new JSONParser();
	        conf = (JSONObject) jsonParser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conf;
	}
	
	public JSONObject makeUser(Map<String, Object> params, String apiAddress) {
		JSONObject user = null;
		
		try {
			URL url = new URL(apiAddress + "v1/users");
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
			con.setRequestProperty("Authorization", "Bearer " + getAccessToken(apiAddress));
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("POST");
			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			
			JSONParser jsonParser = new JSONParser();
	        user = (JSONObject) jsonParser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return user;
	}

	public JSONObject joinConf(String userId, String roomId, String apiAddress) {
		JSONObject conf = null;
		
		try {
			URL url = new URL(apiAddress + "v1/users/"+ userId +"/conference/" + roomId);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
			con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Authorization", "Bearer " + getAccessToken(apiAddress));
			con.setRequestProperty("Accept", "*");
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			String result = "";
	        
			while((inputLine = in.readLine()) != null) { // response 출력
	            result += inputLine;
	        }
			
			JSONParser jsonParser = new JSONParser();
	        conf = (JSONObject) jsonParser.parse(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conf;
	}
}
