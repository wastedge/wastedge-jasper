package com.wastedge.api.jasper.adapter;

import net.sf.jasperreports.data.DataAdapter;

public interface WEAdapter extends DataAdapter {
    public void setWastedgeHost(String host);

    public String getWastedgeHost();

    public void setWastedgeCompany(String company);

    public String getWastedgeCompany();

    public void setWastedgeUsername(String username);

    public String getWastedgeUsername();

    public void setWastedgePassword(String password);

    public String getWastedgePassword();
}
