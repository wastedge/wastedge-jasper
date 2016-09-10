package com.wastedge.api.jasper.datasource;

import java.util.ResourceBundle;

import com.wastedge.api.jasper.Activator;

import com.jaspersoft.studio.model.util.NodeIconDescriptor;

public class WEIconDescriptor extends NodeIconDescriptor {
	/**
	 * Instantiates a new node icon descriptor.
	 * 
	 * @param name
	 *            the name
	 */
	public WEIconDescriptor(String name) {
		super(name, Activator.getDefault());
	}

	/** The resource bundle icons. */
	private static ResourceBundle resourceBundleIcons;

	@Override
	public ResourceBundle getResourceBundleIcons() {
		return resourceBundleIcons;
	}

	@Override
	public void setResourceBundleIcons(ResourceBundle resourceBundleIcons) {
		WEIconDescriptor.resourceBundleIcons = resourceBundleIcons;
	}

}
