package com.wastedge.api.jasper.connection;

import java.util.Map;

public class WEConnection {
	private final String host;
	private final String company;
	private final String username;
	private final String password;

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
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> next() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Class<?>> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Class<?>> getFields(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSearch(String query) {
		// TODO Auto-generated method stub

	}
}
