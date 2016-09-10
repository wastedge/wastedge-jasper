package com.wastedge.api.jasper.adapter;

import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterFactory;
import com.jaspersoft.studio.data.adapter.IDataAdapterCreator;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.engine.JasperReportsContext;
import com.wastedge.api.jasper.Activator;

import org.eclipse.swt.graphics.Image;
import org.apache.log4j.*;

public class WEAdapterFactory implements DataAdapterFactory {
	private static final Logger logger = Logger.getLogger(WEAdapterFactory.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#createDataAdapter()
	 */
	public DataAdapterDescriptor createDataAdapter() {
		WEAdapterDescriptor descriptor = new WEAdapterDescriptor();
		descriptor.getDataAdapter().setWastedgeCompany("");
		descriptor.getDataAdapter().setWastedgeHost("");
		descriptor.getDataAdapter().setWastedgeUsername(null);
		descriptor.getDataAdapter().setWastedgePassword(null);

		logger.info("Returning an WEAdapterDescriptor");

		return descriptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jaspersoft.studio.data.DataAdapterFactory#getDataAdapterClassName()
	 */
	public String getDataAdapterClassName() {
		logger.debug("Returning " + WEAdapterImplementation.class.getName() + " as adapter class name");
		return WEAdapterImplementation.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getDescription()
	 */
	public String getLabel() {
		logger.info("Returning our label");
		return "Wastedge DataSource";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getDescription()
	 */
	public String getDescription() {
		logger.info("Returning our description");
		return "Makes possible to retrieve data from Wastedge";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getIcon(int)
	 */
	public Image getIcon(int size) {
		logger.info("Was requested an icon of size " + size + " - returning the only one we have!");
		if (size == 16) {
			return Activator.getDefault().getImage(Activator.ICON_NAME);
		}
		return Activator.getDefault().getImage(Activator.ICON_NAME);
	}

	@Override
	public DataAdapterService createDataAdapterService(JasperReportsContext jasperReportsContext, DataAdapter dataAdapter) {
		logger.info("Returning a service for data adapter: " + dataAdapter.getClass().getName());
		if (dataAdapter instanceof WEAdapter) {
			return new WEAdapterService(jasperReportsContext, (WEAdapter)dataAdapter);
		}
		logger.info("Returning null, I don't know what the are talking about!");
		return null;
	}

	@Override
	public IDataAdapterCreator iReportConverter() {
		logger.info("Returning an Wastedge creator!");
		return new WECreator();
	}

	@Override
	public boolean isDeprecated() {
		logger.info("Returning false to isDeprecated");
		return false;
	}
}
