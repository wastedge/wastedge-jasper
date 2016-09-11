package com.wastedge.api.jasper.datasource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
	private class WEField extends JRBaseField {
		private static final long serialVersionUID = 1L;

		WEField(String name, String description, Class<?> objType) {
			this.name = name;
			this.description = description;
			this.valueClass = objType;
			this.valueClassName = objType.getName();
		}

		@SuppressWarnings("unused")
		WEField(String name, String description) {
			this(name, description, String.class);
		}
	}

	@Override
	public boolean supportsGetFieldsOperation() {
		return true;
	}

	private WEConnection getConnection(JasperReport jr) {
		WEConnection connection = null;

		if (jr == null) {
			return connection;
		}

		JRValueParameter[] reportParameters = (JRValueParameter[])jr.getParameters();
		for (JRValueParameter parameter : reportParameters) {
			if (parameter.getName().equals(JRParameter.REPORT_CONNECTION)) {
				connection = (WEConnection)parameter.getValue();
				break;
			}
		}

		return connection;
	}

	@Override
	public JRField[] getFields(JasperReport jr) throws JRException {
		WEConnection connection = getConnection(jr);

		if (connection == null) {
			throw new JRException("No Wastedge connection for this report!!");
		}

		Map<String, Class<?>> fields = connection.getFields();

		JRField[] result = new JRField[fields.size()];
		int idx = 0;

		for (String field : fields.keySet()) {
			result[idx] = new WEField(field, field, fields.get(field));
			idx++;
		}
		
		Arrays.sort(result, new Comparator<JRField>() {
            @Override
            public int compare(JRField lhs, JRField rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
		
		return result;
	}

	@Override
	public JRDataSource create(JasperReport jr) throws JRException {
		WEConnection connection = getConnection(jr);

		if (connection == null) {
			throw new JRException("No Wastedge connection for this report!!");
		}

		return new WEDataSource(connection);
	}

	@Override
	public void dispose(JRDataSource jrds) throws JRException {
		((WEDataSource)jrds).dispose();
	}
}
