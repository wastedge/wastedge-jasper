package com.wastedge.api.jasper.adapter;

import java.util.List;

import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignField;
import com.wastedge.api.jasper.Activator;
import com.wastedge.api.jasper.datasource.WEDataSource;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.data.AWizardDataEditorComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.IWizardDataEditorProvider;
import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.data.ui.WizardQueryEditorComposite;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class WEAdapterDescriptor extends DataAdapterDescriptor implements IFieldsProvider, IWizardDataEditorProvider {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	private static final Logger logger = Logger.getLogger(WEAdapterDescriptor.class);

	private IFieldsProvider fieldsProvider;

	@Override
	public WEAdapter getDataAdapter() {
		if (dataAdapter == null) {
			dataAdapter = new WEAdapterImplementation();
		}

		return (WEAdapter)dataAdapter;
	}

	@Override
	public WEAdapterEditor getEditor() {
		return new WEAdapterEditor();
	}

	@Override
	public Image getIcon(int size) {
		logger.debug("Fetching an image of size: " + size);

		Activator activator = Activator.getDefault();
		logger.debug("Activator: " + activator);
		if (activator == null) {
			logger.debug("Activator is null");
			return null;
		}

		Image image = Activator.getDefault().getImage(Activator.ICON_NAME);

		if (image == null) {
			logger.warn("Could not find image for: " + Activator.ICON_NAME);
			return null;
		}

		logger.debug("Returning an icon.");
		return image;
	}

	public List<JRDesignField> getFields(DataAdapterService con, JasperReportsConfiguration jConfig, JRDataset reportDataset) throws JRException, UnsupportedOperationException {
		getFieldProvider();
		return fieldsProvider.getFields(con, jConfig, reportDataset);
	}

	private void getFieldProvider() {
		if (fieldsProvider == null) {
			fieldsProvider = new WEFieldsProvider();
		}
	}

	public boolean supportsGetFieldsOperation(JasperReportsConfiguration jConfig) {
		getFieldProvider();
		return fieldsProvider.supportsGetFieldsOperation(jConfig);
	}

	@Override
	public AWizardDataEditorComposite createDataEditorComposite(Composite parent, WizardPage page) {
		return new WizardQueryEditorComposite(parent, page, this, WEDataSource.QUERY_LANGUAGE);
	}
}
