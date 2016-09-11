package com.wastedge.api.jasper.connection;

import java.io.IOException;
import java.util.Map;
import com.wastedge.api.*;

import net.sf.jasperreports.engine.JRException;

public class WEConnection {
	private final String host;
	private final String company;
	private final String username;
	private final String password;
	private String query;

	public WEConnection(String host, String company, String username, String password) {
		this.host = host;
		this.company = company;
		this.username = username;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public String getCompany() {
		return company;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public WEConnection clone() {
		return new WEConnection(host, company, username, password);
	}

	public void test() {
		// TODO: Implement
	}

	public void close() {
		// TODO: Implement
	}

	public void search() {
		// TODO Auto-generated method stub
	}

	public String getSearch() {
		return query;
	}

	public void setSearch(String query) {
		this.query = query;
	}

	public Map<String, Object> next() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Class<?>> getFields() throws JRException {
		return getFields(query);
	}

	public Map<String, Class<?>> getFields(String query) throws JRException {
		try {
			return new WEFieldFetcher(newApi()).getFields(query);
		} catch (IOException e) {
			throw new JRException(e);
		}
	}
	
	private Api newApi() {
		return new Api(new ApiCredentials(host, company, username, password));
	}
}
