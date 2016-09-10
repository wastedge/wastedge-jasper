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

package com.wastedge.api.jasper.adapter;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.data.AbstractDataAdapterService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReportsContext;

/**
 *
 * @author Fabio Torchetti
 */
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
        logger.debug("Just created a new ESAdapterService with a dataAdapter");
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
    	esSearch = new WEConnection(
			dataAdapter.getWastedgeHost(),
			dataAdapter.getWastedgeCompany(),
			dataAdapter.getWastedgeUsername(),
			dataAdapter.getWastedgePassword()
		);
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
