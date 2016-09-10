package com.wastedge.api.jasper.adapter;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.data.AbstractDataAdapterService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class WEAdapterService extends AbstractDataAdapterService {
	private static Logger logger = Logger.getLogger(WEAdapterService.class);

	public final static String WE_HOST_PARAM = "wastedgeHost";
	public final static String WE_COMPANY_PARAM = "wastedgeCompany";
	public final static String WE_USER_PARAM = "wastedgeUsername";
	public final static String WE_PASSWORD_PARAM = "wastedgePassword";

	private WEConnection connection;
	private final WEAdapter dataAdapter;

	public WEAdapterService(JasperReportsContext jrContext, WEAdapter dataAdapter) {
		super(jrContext, dataAdapter);

		this.dataAdapter = dataAdapter;
		this.connection = null;

		logger.debug("Just created a new WEAdapterService with a dataAdapter");
	}

	@Override
	public void contributeParameters(Map<String, Object> parameters) throws JRException {
		logger.debug("Contributing a couple of parameters to the report.");
		if (connection != null) {
			dispose();
		}
		if (dataAdapter != null) {
			try {
				createConnection();
				parameters.put(JRParameter.REPORT_CONNECTION, connection);
				parameters.put(WEAdapterService.WE_HOST_PARAM, dataAdapter.getWastedgeHost());
				parameters.put(WEAdapterService.WE_COMPANY_PARAM, dataAdapter.getWastedgeCompany());
				parameters.put(WEAdapterService.WE_USER_PARAM, dataAdapter.getWastedgeUsername());
				parameters.put(WEAdapterService.WE_PASSWORD_PARAM, dataAdapter.getWastedgePassword());
			} catch (Exception e) {
				throw new JRException(e);
			}
		}
	}

	private void createConnection() throws JRException {
		connection = new WEConnection(dataAdapter.getWastedgeHost(), dataAdapter.getWastedgeCompany(), dataAdapter.getWastedgeUsername(), dataAdapter.getWastedgePassword());
	}

	@Override
	public void dispose() {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	@Override
	public void test() throws JRException {
		try {
			if (connection == null) {
				createConnection();
			}
			connection.test();
		} finally {
			dispose();
		}
	}
}
