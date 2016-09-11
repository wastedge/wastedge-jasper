package com.wastedge.api.jasper.adapter;

import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.adapter.IDataAdapterCreator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WECreator implements IDataAdapterCreator {
	@Override
	public DataAdapterDescriptor buildFromXML(Document docXML) {
		WEAdapterImpl result = new WEAdapterImpl();

		NamedNodeMap rootAttributes = docXML.getChildNodes().item(0).getAttributes();
		String connectionName = rootAttributes.getNamedItem("name").getTextContent();
		result.setName(connectionName);

		NodeList children = docXML.getChildNodes().item(0).getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeName().equals("connectionParameter")) {
				switch (node.getAttributes().getNamedItem("name").getTextContent()) {
				case "wastedgeHost":
					result.setWastedgeHost(node.getTextContent());
					break;
				case "wastedgeCompany":
					result.setWastedgeCompany(node.getTextContent());
					break;
				case "wastedgeUsername":
					result.setWastedgeUsername(node.getTextContent());
					break;
				case "wastedgePassword":
					result.setWastedgePassword(node.getTextContent());
					break;
				}
			}
		}

		WEAdapterDescriptor desc = new WEAdapterDescriptor();
		desc.setDataAdapter(result);

		return desc;
	}

	@Override
	public String getID() {
		return "com.wastedge.api.jasper.WEConnection";
	}
}
