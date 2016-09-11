package com.wastedge.api.jasper.query;

import java.util.Collections;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactoryBundle;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

import org.apache.log4j.Logger;

public class WEQueryExecuterExtensionRegistry implements ExtensionsRegistryFactory {
	private static final Logger logger = Logger.getLogger(WEQueryExecuterExtensionRegistry.class);

	private static final ExtensionsRegistry defaultExtensionsRegistry = new ExtensionsRegistry() {
		@SuppressWarnings("unchecked")
		public <T> List<T> getExtensions(Class<T> extensionType) {
			if (JRQueryExecuterFactoryBundle.class.equals(extensionType)) {
				logger.debug("Registering the WEQueryExecuterFactoryBundle");
				return (List<T>)Collections.singletonList(WEQueryExecuterFactoryBundle.getInstance());
			}
			return null;
		}
	};

	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) {
		logger.debug("Returning our default extension registry handler");
		return defaultExtensionsRegistry;
	}
}
