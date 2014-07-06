package app.core.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import app.core.model.Story;


public interface IApi {
	
	public String registerApi(String  params) throws ClientProtocolException,IOException;
}
