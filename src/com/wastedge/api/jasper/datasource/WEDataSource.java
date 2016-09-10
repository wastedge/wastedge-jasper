package com.wastedge.api.jasper.datasource;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class WEDataSource implements JRDataSource {
	private static final Logger logger = Logger.getLogger(WEDataSource.class);

	public static String QUERY_LANGUAGE = "wastedge";
	public static String WASTEDGE_SEARCH = "we_search";

	private WEConnection connection = null;
	private Map<String, Object> currentEvent = null;

	public WEDataSource(WEConnection connection) {
		this.connection = connection;
		logger.debug("Created a new Wastedge datasource connected to " + connection.getHost());
		
		this.connection.search();
		logger.debug("Search for " + this.connection.getSearch() + " has started....");
	}

	@Override
	public boolean next() throws JRException {
		currentEvent = connection.next();
		return currentEvent != null;
	}

	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		Object value = currentEvent.get(jrf.getName());
		if (value == null) {
			return "";
		}
		return value;
	}

	public void dispose() {
		connection.close();
		connection = null;
		currentEvent = null;
	}
}
