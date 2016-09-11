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
	private WEQueryExecutor executor;
	private int maxRows;

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

	public void test() throws JRException {
		try {
			newApi().getSchema();
		} catch (IOException e) {
			throw new JRException(e);
		}
	}

	public String getSearch() {
		return query;
	}

	public void setSearch(String query) {
		this.query = query;
	}
	
	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public void search() throws JRException {
		executor = new WEQueryExecutor(newApi());
		try {
			executor.setMaxRows(maxRows);
			executor.search(query);
		} catch (IOException e) {
			throw new JRException(e);
		}
	}

	public Map<String, Object> next() throws JRException {
		try {
			return executor.next();
		} catch (IOException e) {
			throw new JRException(e);
		}
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

	public void close() {
		// Nothing to do.
	}
}
