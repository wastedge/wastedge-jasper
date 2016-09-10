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
	private Text weHostField;
	private Text wePortField;
	private Text weClusterField;
	private Text weIndexesField;
	private Text weTypesField;
	private Text weUsernameField;
	private Text wePasswordField;

	public WEAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);

		initComponents();
	}

	private void initComponents() {
		setLayout(new GridLayout(2, false));
		createLabel("Indexes - comma separated");
		weIndexesField = createTextField(false);
		createLabel("Types - comma separated");
		weTypesField = createTextField(false);
		createLabel("Hostname");
		weHostField = createTextField(false);
		createLabel("Port");
		wePortField = createTextField(false);
		createLabel("Cluster");
		weClusterField = createTextField(false);
		createLabel("Username");
		weUsernameField = createTextField(false);
		createLabel("Password");
		wePasswordField = createTextField(true);

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
		bindingContext.bindValue(SWTObservables.observeText(weHostField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchHost"));
		bindingContext.bindValue(SWTObservables.observeText(wePortField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchPort"));
		bindingContext.bindValue(SWTObservables.observeText(weClusterField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchCluster"));
		bindingContext.bindValue(SWTObservables.observeText(weIndexesField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchIndexes"));
		bindingContext.bindValue(SWTObservables.observeText(weTypesField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchTypes"));
		bindingContext.bindValue(SWTObservables.observeText(weUsernameField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchUsername"));
		bindingContext.bindValue(SWTObservables.observeText(wePasswordField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "elasticSearchPassword"));
	}

	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_elasticsearch");
	}

}