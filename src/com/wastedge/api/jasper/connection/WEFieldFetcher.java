package com.wastedge.api.jasper.connection;

import java.io.IOException;
import java.util.*;

import com.wastedge.api.*;
import com.wastedge.api.jdbc.weql.WeqlQueryParser;

class WEFieldFetcher {
	private final Api api;

	public WEFieldFetcher(Api api) {
		this.api = api;
	}

	public Map<String, Class<?>> getFields(String queryText) throws IOException {
		ApiQuery query = new WeqlQueryParser().parse(api, queryText, null);

        EntitySchema entity = query.getEntity();
        Map<String, Class<?>> fields = new HashMap<>();

        for (Map.Entry<String, EntityMember> entry : entity.getMembers().entrySet()) {
            if (entry.getValue() instanceof EntityTypedField) {
                EntityTypedField member = (EntityTypedField)entry.getValue();

                fields.put(member.getName(), getValueClass(member.getDataType()));
            }
        }

        return fields;
	}

    private Class<?> getValueClass(EntityDataType dataType) {
        switch (dataType) {
            case BYTES:
                return byte[].class;
            case STRING:
                return String.class;
            case DATE:
                return java.sql.Date.class;
            case DATE_TIME:
            case DATE_TIME_TZ:
                return java.sql.Timestamp.class;
            case DECIMAL:
                return Double.class;
            case LONG:
                return Long.class;
            case INT:
                return Integer.class;
            case BOOL:
                return Boolean.class;
            default:
                throw new IllegalArgumentException("dataType");
        }
    }
}
