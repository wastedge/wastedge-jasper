package com.wastedge.api.jasper.server;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceProperty;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.datasource.MRDatasourceCustom;
import com.jaspersoft.studio.server.utils.ResourceDescriptorUtil;
import com.jaspersoft.studio.server.wizard.resource.APageContent;
import com.jaspersoft.studio.utils.Misc;

import com.wastedge.api.jasper.adapter.WEAdapterService;

@SuppressWarnings("deprecation")
public class DatasourceWEPageContent extends APageContent {

	private Text hostField;
	private Text companyField;
	private Text usernameField;
	private Text passwordField;

	public DatasourceWEPageContent(ANode parent, AMResource resource, DataBindingContext bindingContext) {
		super(parent, resource, bindingContext);
	}

	public DatasourceWEPageContent(ANode parent, AMResource resource) {
		super(parent, resource);
	}

	@Override
	public String getPageName() {
		return "com.wastedge.api.jasper.server.page.datasource.elasticsearch";
	}

	@Override
	public String getName() {
		return "Wastedge DataSource";
	}

	private void createLabel(Composite composite, String text) {
		Label label = new Label(composite, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	}

	private Text createTextField(Composite composite, boolean password) {
		Text textField = new Text(composite, !password ? SWT.BORDER : SWT.BORDER | SWT.PASSWORD);
		textField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return textField;
	}

	public Control createContent(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		createLabel(composite, "Hostname");
		hostField = createTextField(composite, false);
		createLabel(composite, "Company");
		companyField = createTextField(composite, false);
		createLabel(composite, "Username");
		usernameField = createTextField(composite, false);
		createLabel(composite, "Password");
		passwordField = createTextField(composite, true);

		rebind();

		return composite;
	}

	@Override
	protected void rebind() {

		ResourceProperty resprop = ResourceDescriptorUtil.getProperty(MRDatasourceCustom.PROP_DATASOURCE_CUSTOM_PROPERTY_MAP, res.getValue().getProperties());

		ResourceProperty rsp = ResourceDescriptorUtil.getProperty(WEAdapterService.ES_HOST_PARAM, resprop.getProperties());
		rsp.setValue(Misc.nvl(rsp.getValue()));
		bindingContext.bindValue(SWTObservables.observeText(hostField, SWT.Modify), PojoObservables.observeValue(rsp, "value"));

		rsp = ResourceDescriptorUtil.getProperty(WEAdapterService.ES_COMPANY_PARAM, resprop.getProperties());
		rsp.setValue(Misc.nvl(rsp.getValue()));
		bindingContext.bindValue(SWTObservables.observeText(companyField, SWT.Modify), PojoObservables.observeValue(rsp, "value"));

		rsp = ResourceDescriptorUtil.getProperty(WEAdapterService.ES_USER_PARAM, resprop.getProperties());
		rsp.setValue(Misc.nvl(rsp.getValue()));
		bindingContext.bindValue(SWTObservables.observeText(usernameField, SWT.Modify), PojoObservables.observeValue(rsp, "value"));

		rsp = ResourceDescriptorUtil.getProperty(WEAdapterService.ES_PASSWORD_PARAM, resprop.getProperties());
		rsp.setValue(Misc.nvl(rsp.getValue()));
		bindingContext.bindValue(SWTObservables.observeText(passwordField, SWT.Modify), PojoObservables.observeValue(rsp, "value"));
	}

	@Override
	public String getHelpContext() {
		return "com.wastedge.api.jasper.toc";
	}
}
