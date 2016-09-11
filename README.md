# Wastedge Jasper Studio plugin

The Wastedge Jasper Studio plugin adds support to Jasper Studio for adding
data adapters that connect to the Wastedge REST API.

## Installation

Complete the steps below to install the Wastedge Jasper Studio plugin into
Jasper Studio:

* In Jasper Studio, open the menu **Help | Install New Software...**;
* Click the **Add...** button;
* Enter the following information:
  * **Name**: Wastedge Update Site;
  * **Location**: https://raw.githubusercontent.com/wastedge/wastedge-jasper/master/WastedgeJasperUpdateSite/;
* Click **OK**;
* The available plugins will now load. Expand the category and check the checkbox
  before **Wastedge Jasper Plugin**;
* Click **Next >**;
* Click **Next >**;
* Accept the license agreement and click **Finish**;
* Click **OK** in the warning dialog;
* Click **Yes** when asked to restart Jasper Studio.

You will now be able to add a new data adapter to your report:

* Open the menu **File | New | Data Adapter**;
* Enter a file name, e.g. "Wastedge Data Adapter.xml" and click **Next >**;
* Select **Wastedge DataSource** and click **Next >**;
* Enter the following information:
  * **Hostname**: The URL to Wastedge, e.g. http://wastedge.com/;
  * **Company**: The name of the company you use to log on;
  * **Username**: Your username;
  * **Password**: Your password;
* Click **Test** to make sure your credentials are correct;
* Click **Finish**.

Now that you've added a data source, you can use it in your report:

* In **Outline**, right click on your report and select **Dataset and Query...**;
* In the drop down at the top, select the data adapter you've just created;
* Change the **Language** to **wastedge**;
* Enter a query, e.g.:

```
from "booking/booking" where customer.cust_number = 5
```

* Click **Read Fields**. The fields you can use in your report will be loaded
  into the lower half of the dialog;
* Switch to the tab **Data preview** at the bottom of the screen;
* Click **Refresh Preview Data**. The results of your query will now be loaded
  into the lower half of the dialog;
* Click **OK**.

In the outline, all fields of the query will now appear. You can drag/drop these
onto your report.

Once you've finished your report, you can switch to the **Preview** tab to
preview the results of your report.
