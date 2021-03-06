package com.wastedge.api.jasper.adapter;

import org.apache.log4j.Logger;

import com.wastedge.api.jasper.adapter.WEAdapter;
import net.sf.jasperreports.data.AbstractDataAdapter;

public class WEAdapterImpl extends AbstractDataAdapter implements WEAdapter {
	private static final Logger logger = Logger.getLogger(WEAdapterImpl.class);

	private String wastedgeHost;
	private String wastedgeCompany;
	private String wastedgeUsername;
	private String wastedgePassword;

	public WEAdapterImpl() {
		logger.debug("Providing our implementation of DataAdapter");
	}

	@Override
	public String getWastedgeHost() {
		return wastedgeHost;
	}

	@Override
	public void setWastedgeHost(String wastedgeHost) {
		logger.debug("Set Host: " + wastedgeHost);
		this.wastedgeHost = wastedgeHost;
	}

	@Override
	public String getWastedgeCompany() {
		return wastedgeCompany;
	}

	@Override
	public void setWastedgeCompany(String wastedgeCompany) {
		logger.debug("Set Company: " + wastedgeCompany);
		this.wastedgeCompany = wastedgeCompany;
	}

	@Override
	public String getWastedgeUsername() {
		return wastedgeUsername;
	}

	@Override
	public void setWastedgeUsername(String wastedgeUsername) {
		logger.debug("Set Username: " + wastedgeUsername);
		this.wastedgeUsername = wastedgeUsername;
	}

	@Override
	public String getWastedgePassword() {
		return wastedgePassword;
	}

	@Override
	public void setWastedgePassword(String wastedgePassword) {
		logger.debug("Set Password: " + wastedgePassword);
		this.wastedgePassword = wastedgePassword;
	}
}
