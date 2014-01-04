package app.xzone.storyline.worker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;


import app.xzone.storyline.http.HttpUtil;
import app.xzone.storyline.http.HttpUtil.Method;
import app.xzone.storyline.interfaces.IApi;
import app.xzone.storyline.model.Story;

public class Api implements IApi{
	protected static final String DOMAIN_URL_API_V1 = "http://mimocore.heroku.com/api/v1/";
	
	private final String BIZ_JSON = "biz.json";
	private final String REGISTER_API = "register";
	
	protected HttpUtil httpUtil;
	protected StringBuffer apiUrl;
	protected Gson gson = null;
	
	private static Api api = null;
	private static BaseWorker worker = null;
	
	public static String KEY_CALL_REGISTER_API = "call_register";
	
	private String[] apiKeys = {KEY_CALL_REGISTER_API};
	
	public Api() {
		this.apiUrl = new StringBuffer(DOMAIN_URL_API_V1);
		this.gson = new Gson();
	}
	
	public static Api getInstance(){
		if(api == null){
			api = new Api();
		}
		return api;
	}
	
	@Override
	public Story[] getMyStories() throws ClientProtocolException, IOException {
		httpUtil = new HttpUtil(apiUrl.append(BIZ_JSON).toString(), null, Method.GET);
		String responses = httpUtil.sendRequest();

		Story[] data = gson.fromJson(responses, Story[].class);
		return data;
	}
	
	
	@Override
	public String registerApi(Object params) throws ClientProtocolException, IOException{
		Map<String, String> paramsMap = (Map<String, String>) params;
		httpUtil = new HttpUtil(apiUrl.append(REGISTER_API).toString(), paramsMap, Method.POST);
		String responses = httpUtil.sendRequest();
		
		System.out.println("-------- responses register API: "+responses);
		return "false";
		
	}

	
	public Object callApi(String apiKey, Object params) throws ClientProtocolException, IOException{
		for(String key : apiKeys){
			if(key.equals(KEY_CALL_REGISTER_API)) return registerApi(params);
			
		}
		return null;
	}
	

}
