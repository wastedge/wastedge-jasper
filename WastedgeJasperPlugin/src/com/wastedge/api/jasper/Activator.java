package com.wastedge.api.jasper;

import java.net.URL;

import net.sf.jasperreports.eclipse.AbstractJRUIPlugin;
import com.wastedge.api.jasper.datasource.WEIconDescriptor;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractJRUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "Wastedge";
	public static final String ICON_NAME = "icons/we_icon_32.png";

	// The shared instance
	private static Activator plugin;

	// Implementation Specific Properties
	private static Logger logger = null;

	public static WEIconDescriptor iconDescriptor = null;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		ConsoleAppender console = new ConsoleAppender(); // create appender
		// configure the appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.DEBUG);
		console.activateOptions();
		// add appender to any Logger (here is root)
		Logger.getRootLogger().addAppender(console);
		logger = Logger.getRootLogger();
		logger.info("Starting Wastedge Plugin - Activator called!");
		plugin = this;
		logger.info("Plugin reference: " + plugin);
		Bundle bundle = plugin.getBundle();
		logger.info("Bundle: " + bundle);
		URL bundleLogConfig = bundle.getEntry("log4j.xml");
		if (bundleLogConfig != null) {
			logger.info("bundleLogConfig: " + bundleLogConfig + " " + bundleLogConfig.getPath());
			URL resolvedURL = FileLocator.resolve(bundleLogConfig);
			logger.info("Resolved URL: " + resolvedURL);
			PropertyConfigurator.configure(resolvedURL);
		}
		logger.debug("Started the Wastedge JR bundle - and configured logging");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		logger.debug("Stopping the Wastedge JR bundle");
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		logger.debug("Returning the current Wastedge JR activator: " + plugin);
		if (plugin == null) {
			logger.warn("This plugin is null - returning a new activator!");
			plugin = new Activator();
			return plugin;
		}
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public ImageDescriptor getImageDescriptor(String path) {
		// We need to instantiate our own logger, since
		// this method can be called before we are instantiated.
		/*
		 * Note: We could make the logger available by initializing it in a
		 * static portion of the class declaration.
		 */
		Logger.getLogger(Activator.class).debug("Getting image descriptor from: " + path);

		if (iconDescriptor == null) {
			iconDescriptor = new WEIconDescriptor("datasource-wastedge");
		}

		return iconDescriptor.getIcon16();
	}

	/**
	 * Returns an Image for the image file at the given plug-in relative path.
	 * 
	 * @param path
	 *            The path to the image file
	 * @return an Image object
	 */
	public Image getImage(String path) {
		ImageDescriptor imageDesc = getImageDescriptor(path);
		return imageDesc.createImage();
	}

	/**
	 * Returns the ID of this plug-in
	 */
	@Override
	public String getPluginID() {
		return PLUGIN_ID;
	}

}
