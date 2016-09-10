package com.wastedge.api.jasper.adapter;

import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.utils.parameter.ParameterUtil;
import com.wastedge.api.jasper.connection.WEConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignField;

public class WEFieldsProvider implements IFieldsProvider {

	private static Logger logger = Logger.getLogger(WEFieldsProvider.class);

	public boolean supportsGetFieldsOperation(JasperReportsConfiguration jConfig) {
		return true;
	}

	public List<JRDesignField> getFields(DataAdapterService dataAdapterService, JasperReportsConfiguration jasperReportsConfiguration, JRDataset dataset)
			throws JRException, UnsupportedOperationException {

		logger.debug("Was asked to provide a list of fields.");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(JRParameter.REPORT_MAX_COUNT, 0);
		dataAdapterService.contributeParameters(parameters);
		ParameterUtil.setParameters(jasperReportsConfiguration, dataset, parameters);
		logger.debug("Getting fields for query: " + dataset.getQuery().getText());
		return com.wastedge.api.jasper.datasource.WEFieldsProvider.getInstance().getFields(jasperReportsConfiguration, dataset, parameters,
				(WEConnection)parameters.get(JRParameter.REPORT_CONNECTION));
	}

}
