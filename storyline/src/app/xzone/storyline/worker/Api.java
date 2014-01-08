package app.xzone.storyline.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import app.xzone.storyline.http.HttpUtil;
import app.xzone.storyline.http.HttpUtil.Method;
import app.xzone.storyline.interfaces.IApi;

import com.google.gson.Gson;

public class Api implements IApi {
	// routing backend api
	protected static final String DOMAIN_API_V1 = "http://10.0.2.2:4000/api/v1/";

	// Collection of API Key
	private final String REGISTER_API = "signin";

	// internal variable
	private static Api api = null;
	private ApiKey apik = null;
	protected StringBuffer apiUrl;
	private static BaseWorker worker = null;

	public enum ApiKey {
		REGISTER_API, UPDATE_TIMELINE
	}

	// variable from 3'rd party
	protected HttpUtil httpUtil;
	protected Gson gson = null;

	public Api() {
		this.apiUrl = new StringBuffer(DOMAIN_API_V1);
		this.gson = new Gson();
	}

	public static Api getInstance() {
		if (api == null) {
			api = new Api();
		}
		return api;
	}

	@Override
	public String registerApi(String param) throws ClientProtocolException,
			IOException {

		List<NameValuePair> pair = new ArrayList<NameValuePair>();
		pair.add(new BasicNameValuePair("token", param));

		httpUtil = new HttpUtil(apiUrl.append(REGISTER_API).toString(), pair,
				Method.POST);
		String responseApi = httpUtil.sendRequest();

		System.out.println("-------- responses register API: "
				+ responseApi.length());
		return responseApi;

	}

	public Object callApi(ApiKey apiKey, Object params)
			throws ClientProtocolException, IOException {

		switch (apiKey) {
		case REGISTER_API:
			return registerApi((String) params);

		case UPDATE_TIMELINE:
			break;

		}

		return null;
	}

}
