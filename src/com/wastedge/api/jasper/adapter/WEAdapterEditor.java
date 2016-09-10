package com.wastedge.api.jasper.adapter;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterEditor;
import net.sf.jasperreports.engine.JasperReportsContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

public class WEAdapterEditor implements DataAdapterEditor {

	protected WEAdapterComposite composite = null;

	public ADataAdapterComposite getComposite(Composite parent, int style, WizardPage wizardPage, JasperReportsContext jrContext) {
		if (composite == null)
			composite = new WEAdapterComposite(parent, style, jrContext);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterEditor#getDataAdapter()
	 */
	public DataAdapterDescriptor getDataAdapter() {
		return composite.getDataAdapter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterEditor#getHelpContextId()
	 */
	public String getHelpContextId() {
		return composite.getHelpContextId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterEditor#setDataAdapter(com.
	 * jaspersoft .studio.data.DataAdapter)
	 */
	public void setDataAdapter(DataAdapterDescriptor dataAdapter) {
		if (dataAdapter instanceof WEAdapterDescriptor)
			composite.setDataAdapter((WEAdapterDescriptor)dataAdapter);
	}

}
