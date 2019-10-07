package com.uprism.address.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uprism.api.ApiSender;
import com.uprism.api.auth.ApiAuthorization;


@Controller
public class AddressController {
	
	@Autowired ApiAuthorization auth;
	@Autowired ApiSender apiSender;
	
	@GetMapping("/")
	public String root(Model model) {
		return "/address/home";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		JSONObject accessToken = auth.getAccessToken();
		JSONObject result = apiSender.sendGet("GET", "v1/users/uprism.good1768/address", (String) accessToken.get("access_token"));
		model.addAttribute("result", result);
		return "/address/address";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public String addAddress(
			@RequestParam("addressUserName") String addressUserName,
			@RequestParam("email") String email,
			@RequestParam("className") String className,
			@RequestParam("post") String post,
			@RequestParam("groupName") String groupName) {
		Map<String, Object> params = new HashMap<>();
		params.put("address_user_name", addressUserName);
		params.put("email", email);
		params.put("class_name", className);
		params.put("post", post);
		params.put("group_name", groupName);
		
		JSONObject accessToken = auth.getAccessToken();
		apiSender.sendPost("POST", "v1/users/uprism.good1768/address", (String) accessToken.get("access_token"), params);
		return "success";
	}
	
	@PostMapping("/delete")
	public String deleteAddress(@RequestParam("addressIdx") String addressIdx) {
		JSONObject accessToken = auth.getAccessToken();
		apiSender.sendPost("DELETE", "v1/users/uprism.good1768/address/" + addressIdx, (String) accessToken.get("access_token"), new HashMap<String, Object>());
		return "redirect:/";
	}
}