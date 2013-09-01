package app.xzone.storyline.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class HttpUtil {

	private HttpResponse httpResponse = null;

	private String url = null;
	private Map<String, String> params = new HashMap<String, String>();
	private Method requestMethod = null;
	private HttpRequestBase requestBase = null;

	public enum Method {
		GET, POST, PUT, DELETE
	}

	public HttpUtil(String url, Map<String, String> params, Method requestMethod) {
		this.url = url;
		this.params = params;
		this.requestMethod = requestMethod;
	}

	public String sendRequest() throws ClientProtocolException, IOException {

		HttpEntity httpEntity = null;
		HttpClient httpClient = new DefaultHttpClient();

		switch (requestMethod) {
		case GET:
			requestBase = new HttpGet(url);
			break;
		case POST:
			requestBase = new HttpPost(url);
			break;
		case PUT:
			requestBase = new HttpPut(url);
			break;
		case DELETE:
			requestBase = new HttpDelete(url);
			break;
		default:
			break;
		}

		HttpResponse response = httpClient.execute(requestBase);
		httpEntity = response.getEntity();
		String responses = getApiResponseBody(httpEntity);

		return responses;

	}

	/**
	 * dependency create instance HTTP Request (POST, PUT, GET, DELETE)
	 * 
	 * @param entity
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getApiResponseBody(final HttpEntity entity)
			throws IOException, ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();
		if (instream == null) {
			return "";
		}
		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
			"HTTP entity too large to be buffered in memory");
		}
		String charset = getContentCharSet(entity);

		if (charset == null) {
			charset = HTTP.DEFAULT_CONTENT_CHARSET;
		}
		Reader reader = new InputStreamReader(instream, charset);
		StringBuilder buffer = new StringBuilder();

		try {
			char[] tmp = new char[1024];
			int l;

			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}

	private static String getContentCharSet(HttpEntity entity) {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		String charset = null;

		if (entity.getContentType() != null) {

			HeaderElement values[] = entity.getContentType().getElements();
			if (values.length > 0) {

				NameValuePair param = values[0].getParameterByName("charset");
				if (param != null) {
					charset = param.getValue();
				}
			}
		}
		return charset;
	}

}
