/****
 * 
 * Copyright 2013-2016 Wedjaa <http://www.wedjaa.net/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.wastedge.api.jasper.query;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactoryBundle;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRSingletonCache;
import com.wastedge.api.jasper.datasource.ESDataSource;

import org.apache.log4j.*;

/**
 * 
 * @author Fabio Torchetti
 * 
 */
public class WEQueryExecuterFactoryBundle implements JRQueryExecuterFactoryBundle {
	private static final JRSingletonCache<QueryExecuterFactory> cache = new JRSingletonCache<QueryExecuterFactory>(
			QueryExecuterFactory.class);

	private static final WEQueryExecuterFactoryBundle instance = new WEQueryExecuterFactoryBundle();

	private static final String[] languages = new String[] { WEDataSource.QUERY_LANGUAGE };

	private static final Logger logger = Logger.getLogger(WEQueryExecuterFactoryBundle.class);
	
			
	private WEQueryExecuterFactoryBundle() {
			if ( logger != null ) {
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

	public QueryExecuterFactory getQueryExecuterFactory(String language)
			throws JRException {
                        logger.debug("Begin asked for a factory for: " +language);
		if (WEDataSource.QUERY_LANGUAGE.equals(language)) {
			logger.debug("Returning a ESQueryExecuterFactory");
			return (QueryExecuterFactory) cache
					.getCachedInstance(WEQueryExecuterFactory.class
							.getName());
		}
		return null;
	}
}

