package com.wastedge.api.jasper.datasource;

import java.util.Map;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseField;

public class WEDataSourceProvider implements JRDataSourceProvider {
	private class ESField extends JRBaseField {
		private static final long serialVersionUID = 1L;

		ESField(String name, String description, Class<?> objType) {
			this.name = name;
			this.description = description;
			this.valueClass = objType;
			this.valueClassName = objType.getName();
		}

		@SuppressWarnings("unused")
		ESField(String name, String description) {
			this(name, description, String.class);
		}
	}

	@Override
	public boolean supportsGetFieldsOperation() {
		return true;
	}

	private WEConnection getESSearch(JasperReport jr) {
		WEConnection esSearch = null;

		if (jr == null) {
			return esSearch;
		}

		JRValueParameter[] reportParameters = (JRValueParameter[])jr.getParameters();
		for (JRValueParameter parameter : reportParameters) {
			if (parameter.getName().equals(JRParameter.REPORT_CONNECTION)) {
				esSearch = (WEConnection)parameter.getValue();
				break;
			}
		}

		return esSearch;
	}

	@Override
	public JRField[] getFields(JasperReport jr) throws JRException {
		WEConnection esSearch = getESSearch(jr);

		if (esSearch == null) {
			throw new JRException("No ElasticSearch connection for this report!!");
		}

		Map<String, Class<?>> fields = esSearch.getFields();

		JRField[] result = new JRField[fields.size()];
		int idx = 0;

		for (String field : fields.keySet()) {
			result[idx] = new ESField(field, field, fields.get(field));
			idx++;
		}

		return result;
	}

	@Override
	public JRDataSource create(JasperReport jr) throws JRException {
		WEConnection esSearch = getESSearch(jr);

		if (esSearch == null) {
			throw new JRException("No ElasticSearcg connection for this report!!");
		}

		return new WEDataSource(esSearch);
	}

	@Override
	public void dispose(JRDataSource jrds) throws JRException {
		((WEDataSource)jrds).dispose();
	}
}
