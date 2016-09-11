package com.wastedge.api.jasper.adapter;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("deprecation")
public class WEAdapterComposite extends ADataAdapterComposite {
	private WEAdapterDescriptor dataAdapterDescriptor;
	private Text hostField;
	private Text companyField;
	private Text usernameField;
	private Text passwordField;

	public WEAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);

		initComponents();
	}

	private void initComponents() {
		setLayout(new GridLayout(2, false));
		createLabel("Hostname");
		hostField = createTextField(false);
		createLabel("Company");
		companyField = createTextField(false);
		createLabel("Username");
		usernameField = createTextField(false);
		createLabel("Password");
		passwordField = createTextField(true);
	}

	private void createLabel(String text) {
		Label label = new Label(this, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	}

	private Text createTextField(boolean password) {
		Text textField = new Text(this, !password ? SWT.BORDER : SWT.BORDER | SWT.PASSWORD);
		textField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return textField;
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDescriptor == null) {
			dataAdapterDescriptor = new WEAdapterDescriptor();
		}
		return dataAdapterDescriptor;
	}

	@Override
	public void setDataAdapter(DataAdapterDescriptor dataAdapterDescriptor) {
		super.setDataAdapter(dataAdapterDescriptor);

		this.dataAdapterDescriptor = (WEAdapterDescriptor)dataAdapterDescriptor;
		WEAdapter dataAdapter = (WEAdapter)dataAdapterDescriptor.getDataAdapter();
		bindWidgets(dataAdapter);
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(SWTObservables.observeText(hostField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "wastedgeHost"));
		bindingContext.bindValue(SWTObservables.observeText(companyField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "wastedgeCompany"));
		bindingContext.bindValue(SWTObservables.observeText(usernameField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "wastedgeUsername"));
		bindingContext.bindValue(SWTObservables.observeText(passwordField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "wastedgePassword"));
	}

	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_wastedge");
	}
}
