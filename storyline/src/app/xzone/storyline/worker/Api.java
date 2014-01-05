package app.xzone.storyline.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;


import app.xzone.storyline.http.HttpUtil;
import app.xzone.storyline.http.HttpUtil.Method;
import app.xzone.storyline.interfaces.IApi;
import app.xzone.storyline.model.Story;

public class Api implements IApi{
	protected static final String DOMAIN_URL_API_V1 = "http://10.0.2.2:4000/api/v1/";
	
	private final String BIZ_JSON = "biz.json";
	private final String REGISTER_API = "signin";
	
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
		String responseApi = httpUtil.sendRequest();

		Story[] data = gson.fromJson(responseApi, Story[].class);
		return data;
	}
	
	
	@Override
	public String registerApi(String param) throws ClientProtocolException, IOException{
		
		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("token", param));

		
		httpUtil = new HttpUtil(apiUrl.append(REGISTER_API).toString(), pair, Method.POST);
		String responseApi = httpUtil.sendRequest();
		
		System.out.println("-------- responses register API: "+responseApi.length());
		return responseApi;
		
	}

	
	
	
	
	public Object callApi(String apiKey, Object params) throws ClientProtocolException, IOException{
		for(String key : apiKeys){
			if(key.equals(KEY_CALL_REGISTER_API)) return registerApi((String) params);
			
		}
		return null;
	}
	

}
