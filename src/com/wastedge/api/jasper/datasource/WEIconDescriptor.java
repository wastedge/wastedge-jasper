package com.wastedge.api.jasper.datasource;

import java.util.ResourceBundle;

import com.wastedge.api.jasper.Activator;

import com.jaspersoft.studio.model.util.NodeIconDescriptor;

public class WEIconDescriptor extends NodeIconDescriptor {
	private static ResourceBundle resourceBundleIcons;

	public WEIconDescriptor(String name) {
		super(name, Activator.getDefault());
	}

	@Override
	public ResourceBundle getResourceBundleIcons() {
		return resourceBundleIcons;
	}

	@Override
	public void setResourceBundleIcons(ResourceBundle resourceBundleIcons) {
		WEIconDescriptor.resourceBundleIcons = resourceBundleIcons;
	}
}
