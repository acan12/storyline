package app.xzone.storyline.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;


import app.xzone.storyline.http.HttpUtil;
import app.xzone.storyline.http.HttpUtil.Method;
import app.xzone.storyline.interfaces.IApi;
import app.xzone.storyline.model.Story;

public class Api implements IApi{
	private static final String PREFIX_API_V1_URL = "http://mimocore.heroku.com/api/v1/";
	private final String BIZ_JSON = "biz.json";
	
	private HttpUtil httpUtil;
	private String apiUrl;
	private Gson gson = null;
	
	
	public Api() {
		this.apiUrl = PREFIX_API_V1_URL;
		this.gson = new Gson();
	}
	
	@Override
	public Story[] getMyStories() throws ClientProtocolException, IOException {
		httpUtil = new HttpUtil(apiUrl + BIZ_JSON, null, Method.GET);
		String responses = httpUtil.sendRequest();

		Story[] data = gson.fromJson(responses, Story[].class);
		return data;
	}

}
