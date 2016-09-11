package com.wastedge.api.jasper.datasource;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRValueParameter;

class SimpleValueParameter implements JRValueParameter {
	private Object value;
	
	public SimpleValueParameter(Object value) {
		this.value = value;
	}
	
	public JRValueParameter clone() {
		return new SimpleValueParameter(value);
	}

	@Override
	public JRExpression getDefaultValueExpression() {
		throw new IllegalStateException();
	}

	@Override
	public String getDescription() {
		throw new IllegalStateException();
	}

	@Override
	public String getName() {
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getNestedType() {
		throw new IllegalStateException();
	}

	@Override
	public String getNestedTypeName() {
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getValueClass() {
		throw new IllegalStateException();
	}

	@Override
	public String getValueClassName() {
		throw new IllegalStateException();
	}

	@Override
	public boolean isForPrompting() {
		throw new IllegalStateException();
	}

	@Override
	public boolean isSystemDefined() {
		throw new IllegalStateException();
	}

	@Override
	public void setDescription(String description) {
		throw new IllegalStateException();
	}

	@Override
	public JRPropertiesHolder getParentProperties() {
		throw new IllegalStateException();
	}

	@Override
	public JRPropertiesMap getPropertiesMap() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasProperties() {
		throw new IllegalStateException();
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}
}
