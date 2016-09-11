package com.wastedge.api.jasper.query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fill.JRFillParameter;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;

import com.wastedge.api.jasper.connection.WEConnection;
import com.wastedge.api.jasper.datasource.WEDataSource;

public class WEQueryExecuter extends JRAbstractQueryExecuter {
	private final String DATE_FORMAT = "yyyy-MM-dd";
	private final String DATE_TIME_FORMAT = DATE_FORMAT + "'T'HH:mm:ss.SSS";
	
	private final Map<String, ? extends JRValueParameter> reportParameters;
	private final Map<String, Object> parameters;
	private final boolean directParameters;
	private WEConnection connection;
	
	private SimpleDateFormat dateFormat;
	private SimpleDateFormat dateTimeFormat;

	private static Logger logger = Logger.getLogger(WEQueryExecuter.class);

	public WEQueryExecuter(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String, ? extends JRValueParameter> parameters) throws JRException {
		this(jasperReportsContext, dataset, parameters, false);
	}

	public WEQueryExecuter(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String, ? extends JRValueParameter> parameters, boolean directParameters) {
		super(jasperReportsContext, dataset, parameters);

		if (logger.isDebugEnabled() && parameters.get(JRFillParameter.JASPER_REPORT) != null) {
			JasperReport report = (JasperReport)parameters.get(JRFillParameter.JASPER_REPORT).getValue();
			if (report != null) {
				logger.debug("WEQueryExecuter for report: " + report.getName());
				logger.debug("Report query: " + report.getQuery().getText());
			}
		}
		if (logger.isTraceEnabled()) {
			for (String param : parameters.keySet()) {
				JRFillParameter paramVal = (JRFillParameter)parameters.get(param);
				logger.trace("  queryParam[" + param + "]: " + paramVal.getValue());
			}

			Map<String, String> jrCtx = jasperReportsContext.getProperties();
			for (String propName : jrCtx.keySet()) {
				logger.trace("  ctxParam[" + propName + "]: " + jrCtx.get(propName));
			}
		}

		logger.trace("Dataset Query: " + dataset.getQuery().getText());

		this.directParameters = directParameters;
		this.reportParameters = parameters;
		this.parameters = new HashMap<String, Object>();

		logger.debug("Started a query executer for Wastedge");

		parseQuery();
	}

	/**
	 * Method not implemented
	 */
	@Override
	public boolean cancelQuery() throws JRException {
		return false;
	}

	@Override
	public void close() {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	private WEConnection processConnection(JRValueParameter valueParameter) throws JRException {
		if (valueParameter == null) {
			throw new JRException("No Wastedge connection");
		}
		return (WEConnection)valueParameter.getValue();
	}

	@Override
	public JRDataSource createDatasource() throws JRException {
		WEConnection connection = (WEConnection)((Map<?, ?>)getParameterValue(JRParameter.REPORT_PARAMETERS_MAP)).get(JRParameter.REPORT_CONNECTION);
		if (connection == null) {
			connection = processConnection(reportParameters.get(JRParameter.REPORT_CONNECTION));
			if (connection == null) {
				throw new JRException("No WE connection");
			}
		}

		// We create a new connection for the datasource based on the one that
		// was handed over to us.

		WEConnection newConnection = connection.clone();
		newConnection.setSearch(getQueryString());

		JRValueParameter maxCount = reportParameters.get("REPORT_MAX_COUNT");
		if (maxCount != null && maxCount.getValue() instanceof Number) {
			newConnection.setMaxRows(((Number)maxCount.getValue()).intValue());
		}

		this.connection = connection;

		logger.debug("Create new DataSource with a clone of the current connection.");
		logger.debug("Setting the search to query: " + getQueryString());

		return new WEDataSource(newConnection);
	}

	/**
	 * Replacement of parameters
	 */
	@Override
	protected String getParameterReplacement(String parameterName) {
		logger.debug("Getting replacement for: " + parameterName);

		Object parameterValue = reportParameters.get(parameterName);
		if (parameterValue == null) {
			throw new JRRuntimeException("Parameter \"" + parameterName + "\" does not exist.");
		}
		if (parameterValue instanceof JRValueParameter) {
			parameterValue = ((JRValueParameter)parameterValue).getValue();
		}

		return processParameter(parameterName, parameterValue);
	}

	private String processParameter(String parameterName, Object parameterValue) {
		if (parameterValue == null) {
			return "NULL";
		}
		if (parameterValue instanceof Number) {
			return parameterValue.toString();
		}
		if (parameterValue instanceof Boolean) {
			return (boolean)parameterValue ? "TRUE" : "FALSE";
		}

		String string;

		if (parameterValue instanceof Date) {
			string = getDateTimeFormat().format((Date)parameterValue);
		} else if (parameterValue instanceof java.sql.Date) {
			string = getDateFormat().format((java.sql.Date)parameterValue);
		} else if (parameterValue instanceof java.sql.Timestamp) {
			string = getDateTimeFormat().format((java.sql.Timestamp)parameterValue);
		} else {
			string = parameterValue.toString();
		}

		return escape(string);
	}
	
	private SimpleDateFormat getDateFormat() {
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat(DATE_FORMAT);
		}
		return dateFormat;
	}
	
	private SimpleDateFormat getDateTimeFormat() {
		if (dateTimeFormat == null) {
			dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		}
		return dateTimeFormat;
	}

	private String escape(String value) {
		StringBuilder sb = new StringBuilder();

		sb.append('\'');
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '\'':
				sb.append("\'\'");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append('\'');

		return sb.toString();
	}

	public String getProcessedQueryString() {
		return getQueryString();
	}

	@Override
	protected Object getParameterValue(String parameterName, boolean ignoreMissing) {
		try {
			return super.getParameterValue(parameterName, ignoreMissing);
		} catch (Exception e) {
			if (e.getMessage().endsWith("cannot be cast to net.sf.jasperreports.engine.JRValueParameter") && directParameters) {
				return reportParameters.get(parameterName);
			}
		}
		return null;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
}
