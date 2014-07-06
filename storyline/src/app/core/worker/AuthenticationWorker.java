package app.core.worker;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import app.core.worker.Api.ApiKey;

public class AuthenticationWorker extends BaseWorker {
	private static AuthenticationWorker authWorker = null;
	private ApiKey apiKey;

	private Object params;
	private Object response;

	protected AuthenticationWorker(ApiKey apiKey, Object params) {
		this.apiKey = apiKey;
		this.params = params;
		this.start();
	}

	public static AuthenticationWorker getInstance(ApiKey apiKey, Object params) {
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

	// set API Key as Uniq
	public ApiKey getApiKey() {
		return apiKey;
	}

	public void setApi(ApiKey apiKey) {
		this.apiKey = apiKey;
	}

}
