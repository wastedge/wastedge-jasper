package com.wastedge.api.jasper.query;

import net.sf.jasperreports.engine.design.JRDesignQuery;

import org.apache.log4j.Logger;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.data.designer.QueryDesigner;
import com.jaspersoft.studio.wizards.ContextHelpIDs;

public class WEQueryDesigner extends QueryDesigner {
	/* Text area where enter the query */
	protected StyledText queryTextArea;

	private static Logger logger = Logger.getLogger(WEQueryDesigner.class);

	public Control createControl(Composite parent) {
		control = (StyledText)super.createControl(parent);
		return control;
	}

	protected void queryTextAreaModified() {
		((JRDesignQuery)jDataset.getQuery()).setText(queryTextArea.getText());
	}

	@Override
	public String getContextHelpId() {
		logger.debug("Testing in Query designer");
		return ContextHelpIDs.PREFIX.concat("query_wastedge");
	}
}
