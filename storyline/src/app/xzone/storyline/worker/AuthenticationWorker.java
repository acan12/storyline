package app.xzone.storyline.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class AuthenticationWorker extends BaseWorker {
	private static AuthenticationWorker authWorker = null;
	private String apiKey;

	private Object params;
	private Object response;

	protected AuthenticationWorker(String apiKey, Object params) {
		this.apiKey = apiKey;
		this.params = params;
		this.start();
	}

	public static AuthenticationWorker getInstance(String apiKey, Object params) {
		if (authWorker == null) {
			authWorker = new AuthenticationWorker(apiKey, params);
		}

		return authWorker;
	}

	public synchronized Object callApi() {
		while (this.isAlive()) {}

		stopWorker();
		return getResponse();
	}

	public void run() {
		this.setResponse(runWorker(100));
	}

	protected Object runWorker(long sleepTime) {
		try {
			sleep(sleepTime);
			return Api.getInstance().callApi(getApiKey(), getParams());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}


	// send paramater via API
	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	// get response from API
	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	// set API Key as uniq identification
	public String getApiKey() {
		return apiKey;
	}

	public void setApi(String apiKey) {
		this.apiKey = apiKey;
	}

}
