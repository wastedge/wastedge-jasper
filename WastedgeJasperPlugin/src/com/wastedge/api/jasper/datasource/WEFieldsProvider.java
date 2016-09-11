package com.wastedge.api.jasper.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JRDesignField;

import org.apache.log4j.Logger;

import com.jaspersoft.studio.utils.parameter.SimpleValueParameter;
import com.wastedge.api.jasper.connection.WEConnection;
import com.wastedge.api.jasper.query.WEQueryExecuter;

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

	public List<JRDesignField> getFields(JasperReportsContext context, JRDataset dataset, Map<String, Object> parameters, WEConnection connection) throws JRException {
		logger.debug("Providing fields a query.");

		List<JRDesignField> fields = new ArrayList<JRDesignField>();

		if (dataset.getQuery() == null) {
			return fields;
		}
		
		logger.debug("Getting fields for query: " + dataset.getQuery().getText());
		
		WEQueryExecuter queryExecutor = new WEQueryExecuter(context, dataset, getReportParameters(parameters));
		String query = queryExecutor.getProcessedQueryString();

		logger.debug("Passing query to connection: " + query);
		
		connection.setSearch(query);
		Map<String, Class<?>> queryFields = connection.getFields(query);

		for (String fieldName : queryFields.keySet()) {
			JRDesignField field = new JRDesignField();
			field.setName(fieldName);
			field.setValueClass(queryFields.get(fieldName));
			field.setDescription(fieldName);
			fields.add(field);
		}

        Collections.sort(fields, new Comparator<JRDesignField>() {
            @Override
            public int compare(JRDesignField lhs, JRDesignField rhs) {
            	return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });

		return fields;
	}

	private Map<String, JRValueParameter> getReportParameters(Map<String, Object> parameters) {
		 Map<String, JRValueParameter> reportParameters = new HashMap<>();
		 
		 for (Entry<String, Object> entry : parameters.entrySet()) {
			 reportParameters.put(entry.getKey(), new SimpleValueParameter(entry.getValue()));
		 }
		 
		 return reportParameters;
	}
}
