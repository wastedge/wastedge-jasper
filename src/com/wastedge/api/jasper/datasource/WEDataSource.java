package com.wastedge.api.jasper.datasource;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.connection.WEConnection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class WEDataSource implements JRDataSource {
    private static final Logger logger = Logger.getLogger(WEDataSource.class);
    
    public static String QUERY_LANGUAGE = "wastedge";
    public static String WASTEDGE_SEARCH = "we_search";
    
    private WEConnection esSearch = null;
    private Map<String,Object> currentEvent = null;
    
    public WEDataSource(WEConnection esSearch) {
        this.esSearch = esSearch;
        logger.debug("Created a new elasticsearch datasource connected to " + esSearch.getHost());
        this.esSearch.search();
        logger.debug("Search for " + this.esSearch.getSearch() + " has started....");
    }
    
    @Override
    public boolean next() throws JRException {
        currentEvent = esSearch.next();
        return currentEvent != null;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object value = currentEvent.get(jrf.getName());
        if (value == null) {
            return "";
        }
        return value;
    }
    
    public void dispose() {
        esSearch.close();
        esSearch = null;
        currentEvent = null;
    }
    
}
