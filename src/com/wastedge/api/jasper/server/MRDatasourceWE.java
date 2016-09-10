package com.wastedge.api.jasper.server;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRConstants;
import com.wastedge.api.jasper.adapter.WEAdapterService;
import com.wastedge.api.jasper.datasource.WEIconDescriptor;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceProperty;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.util.IIconDescriptor;
import com.jaspersoft.studio.server.model.datasource.MRDatasourceCustom;
import com.jaspersoft.studio.server.protocol.restv2.DiffFields;

public class MRDatasourceWE extends MRDatasourceCustom {
	public static final String CUSTOM_CLASS = "com.wastedge.api.jasper.adapter.WEAdapterService";
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MRDatasourceWE(ANode parent, ResourceDescriptor rd, int index) {
		super(parent, rd, index);
	}

	private static IIconDescriptor iconDescriptor;

	public static IIconDescriptor getIconDescriptor() {
		if (iconDescriptor == null) {
			iconDescriptor = new WEIconDescriptor("datasource-wastedge");
		}
		return iconDescriptor;
	}

	@Override
	public IIconDescriptor getThisIconDescriptor() {
		return getIconDescriptor();
	}

	public static ResourceDescriptor createDescriptor(ANode parent) {
		ResourceDescriptor rd = MRDatasourceCustom.createDescriptor(parent);
		ResourceProperty rp = new ResourceProperty(MRDatasourceCustom.PROP_DATASOURCE_CUSTOM_PROPERTY_MAP);

		List<ResourceProperty> props = new ArrayList<ResourceProperty>();

		// Connection Parameters
		props.add(new ResourceProperty(WEAdapterService.WE_HOST_PARAM, ""));
		props.add(new ResourceProperty(WEAdapterService.WE_COMPANY_PARAM, ""));
		// Authentication Parameters
		props.add(new ResourceProperty(WEAdapterService.WE_USER_PARAM, ""));
		props.add(new ResourceProperty(WEAdapterService.WE_PASSWORD_PARAM, ""));

		props.add(new ResourceProperty("_cds_name", "WEDataSource"));
		rp.setProperties(props);
		rd.setResourceProperty(rp);

		rp = new ResourceProperty(ResourceDescriptor.PROP_DATASOURCE_CUSTOM_SERVICE_CLASS, CUSTOM_CLASS);
		rd.setResourceProperty(rp);
		rd.setResourceProperty(DiffFields.DATASOURCENAME, "WEDataSource");

		return rd;
	}
}
