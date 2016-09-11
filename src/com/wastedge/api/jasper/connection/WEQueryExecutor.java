package com.wastedge.api.jasper.connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wastedge.api.*;
import com.wastedge.api.jdbc.weql.WeqlQueryParser;

class WEQueryExecutor {
	private final Api api;
	private int maxRows;
	private ResultSet reader;
	private ApiQuery query;

	public WEQueryExecutor(Api api) {
		this.api = api;
	}
	
	public int getMaxRows() {
		return maxRows;
	}
	
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public void search(String queryText) throws IOException {
		query = new WeqlQueryParser().parse(api, queryText, null);
		
		if (maxRows > 0) {
			query.setCount(maxRows);
        }

        reader = query.executeReader();
	}

	public Map<String, Object> next() throws IOException {
		if (reader == null) {
			return null;
		}
		
		if (!reader.next()) {
			if (reader.isHasMore()) {
				query.setStart(reader.getNextResult());
				reader = query.executeReader();
				
				return next();
			} else {
				reader = null;
				
				return null;
			}
		}
		
		Map<String, Object> values = new HashMap<>();
		
		for (int i = 0; i < reader.getFieldCount(); i++) {
			values.put(reader.getFieldName(i), reader.get(i));
		}
		
		return values;
	}
}
