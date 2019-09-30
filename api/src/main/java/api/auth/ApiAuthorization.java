package api.auth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64.Encoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import api.ApiSender;

@Component
public class ApiAuthorization {
	
	@Autowired ApiSender sendApi;
	
	@Value("${api.clientKey}")
	private String clientKey;
	
	@Value("${api.clientSecret}")
	private String clientSecret;
	
	@Value("${api.address}")
	private String apiAddress;
	
	public JSONObject getAccessToken() {
		JSONObject jsonResult = null;
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
			con.setRequestProperty("Authorization", "Basic " + encoder.encodeToString((clientKey + ":" + clientSecret).getBytes("UTF-8")));
			con.setDoOutput(true); 
			con.setDoInput(true); 
			con.setRequestMethod("POST"); // "GET", "POST"
			con.getOutputStream().write(postData.toString().getBytes());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine, result = "";
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
