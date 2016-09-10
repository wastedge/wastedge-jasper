package com.wastedge.api.jasper.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JRDesignField;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

public class WEFieldsProvider {
	private static final Lock lock = new ReentrantLock();
	private static final Logger logger = Logger.getLogger(WEFieldsProvider.class);

	private static WEFieldsProvider instance;

	private WEFieldsProvider() {
	}

	public boolean supportsGetFieldsOperation(Object jConfig) {
		return true;
	}

	public static WEFieldsProvider getInstance() {
		lock.lock();
		try {
			if (instance == null) {
				instance = new WEFieldsProvider();
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}

	public List<JRDesignField> getFields(JasperReportsContext context, JRDataset dataset,
			Map<String, Object> parameters, WEConnection connection) throws JRException {
		logger.debug("Providing fields a query.");

		String query = "{ query: { match_all: {} } }";

		if (dataset.getQuery() != null) {
			query = dataset.getQuery().getText();
		}

		logger.debug("Passing query to connection: " + query);
		connection.setSearch(query);
		Map<String, Class<?>> queryFields = connection.getFields(query);

		List<JRDesignField> fields = new ArrayList<JRDesignField>();

		for (String fieldName : queryFields.keySet()) {
			JRDesignField field = new JRDesignField();
			field.setName(fieldName);
			field.setValueClass(queryFields.get(fieldName));
			field.setDescription(fieldName);
			fields.add(field);
		}

		return fields;
	}

}
