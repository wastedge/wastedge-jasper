package com.wastedge.api.jasper.query;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactoryBundle;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRSingletonCache;
import com.wastedge.api.jasper.datasource.WEDataSource;

import org.apache.log4j.*;

public class WEQueryExecuterFactoryBundle implements JRQueryExecuterFactoryBundle {
	private static final JRSingletonCache<QueryExecuterFactory> cache = new JRSingletonCache<QueryExecuterFactory>(QueryExecuterFactory.class);

	private static final WEQueryExecuterFactoryBundle instance = new WEQueryExecuterFactoryBundle();

	private static final String[] languages = new String[] { WEDataSource.QUERY_LANGUAGE };

	private static final Logger logger = Logger.getLogger(WEQueryExecuterFactoryBundle.class);

	private WEQueryExecuterFactoryBundle() {
		if (logger != null) {
			logger.debug("This is the query executer for ES");
		}
	}

	public static WEQueryExecuterFactoryBundle getInstance() {
		logger.debug("Someone asked for an instance??");
		return instance;
	}

	public String[] getLanguages() {
		logger.debug("Someone asked for languages??");
		return languages;
	}

	public QueryExecuterFactory getQueryExecuterFactory(String language) throws JRException {
		logger.debug("Begin asked for a factory for: " + language);
		if (WEDataSource.QUERY_LANGUAGE.equals(language)) {
			logger.debug("Returning a ESQueryExecuterFactory");
			return (QueryExecuterFactory)cache.getCachedInstance(WEQueryExecuterFactory.class.getName());
		}
		return null;
	}
}
