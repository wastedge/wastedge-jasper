package com.wastedge.api.jasper.adapter;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.data.AbstractDataAdapterService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class WEAdapterService extends AbstractDataAdapterService {

	public final static String ES_HOST_PARAM = "wastedgeHost";
	public final static String ES_COMPANY_PARAM = "wastedgeCompany";
	public final static String ES_USER_PARAM = "wastedgeUsername";
	public final static String ES_PASSWORD_PARAM = "wastedgePassword";

	private WEConnection esSearch;

	private final WEAdapter dataAdapter;
	private static Logger logger = Logger.getLogger(WEAdapterService.class);

	public WEAdapterService(JasperReportsContext jrContext, WEAdapter dataAdapter) {
		super(jrContext, dataAdapter);
		this.dataAdapter = dataAdapter;
		this.esSearch = null;
		logger.debug("Just created a new WEAdapterService with a dataAdapter");
	}

	@Override
	public void contributeParameters(Map<String, Object> parameters) throws JRException {
		logger.debug("Contributing a couple of parameters to the report.");
		if (esSearch != null) {
			dispose();
		}
		if (dataAdapter != null) {
			try {
				createESSearch();
				parameters.put(JRParameter.REPORT_CONNECTION, esSearch);
				parameters.put(WEAdapterService.ES_HOST_PARAM, dataAdapter.getWastedgeHost());
				parameters.put(WEAdapterService.ES_COMPANY_PARAM, dataAdapter.getWastedgeCompany());
				parameters.put(WEAdapterService.ES_USER_PARAM, dataAdapter.getWastedgeUsername());
				parameters.put(WEAdapterService.ES_PASSWORD_PARAM, dataAdapter.getWastedgePassword());
			} catch (Exception e) {
				throw new JRException(e);
			}
		}
	}

	private void createESSearch() throws JRException {
		esSearch = new WEConnection(dataAdapter.getWastedgeHost(), dataAdapter.getWastedgeCompany(), dataAdapter.getWastedgeUsername(), dataAdapter.getWastedgePassword());
	}

	@Override
	public void dispose() {
		if (esSearch != null) {
			esSearch.close();
			esSearch = null;
		}
	}

	@Override
	public void test() throws JRException {
		try {
			if (esSearch == null) {
				createESSearch();
			}
			esSearch.test();
		} finally {
			dispose();
		}
	}
}
