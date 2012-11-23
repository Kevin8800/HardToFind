/**
 * 
 */
package B2B;

import model.*;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Vicky
 * 
 */
public class ProcureOrder {

	/**
	 * 
	 */
	public ProcureOrder() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		HashMap<String, ItemBean> procurementOrder = new HashMap<String, ItemBean>();
		;
		if (args.length != 1) {
			System.out.println("Wrong number of arguments");
		} else {

			File folder = new File(args[0]);
			File[] listOfFiles = folder.listFiles();

			for (File i : listOfFiles) {

				// Create the unmarshaller, this is the nifty little thing that
				// will actually transform the XML back into an object
				JAXBContext context = JAXBContext
						.newInstance(OrderWrapper.class);

				Unmarshaller unmarshaller = context.createUnmarshaller();

				// Unmarshal the XML in the stringWriter back into an object
				OrderWrapper ow = (OrderWrapper) unmarshaller.unmarshal(i);

				for (ItemBean item : ow.getItems().getItems()) {
					produceOrder(item, procurementOrder);
				}
				// Print out the contents of the JavaObject we just unmarshalled
				// from the XML
			}

			// put all items to be order in a collection
			Collection<ItemBean> orders = procurementOrder.values();
			WebServiceClient.order(orders);

			generateReport(orders);
		}
	}

	/**
	 * 
	 * @param item
	 * @param procurementOrder
	 */
	private static void produceOrder(ItemBean item,
			HashMap<String, ItemBean> procurementOrder) {

		String itemNumber = item.getItemNumber();
		if (procurementOrder.containsKey(itemNumber))// the map already has the
														// item
		{
			ItemBean it = procurementOrder.get(itemNumber);
			int qty = it.getQuantity() + item.getQuantity();
			it.setQuantity(qty);
		} else // the map does not have the item
		{
			procurementOrder.put(itemNumber, item);
		}

	}

	private static void generateReport(Collection<ItemBean> orders)
			throws IOException {
		FileWriter fw = new FileWriter("report.html");
		BufferedWriter out = new BufferedWriter(fw);

		out.write("<html xmlns='http://www.w3.org/1999/xhtml'><head><title>Order Report</title><style> th {text-align:left;} </style></head>");
		out.write("<body><h1>Order report</h1><table><tr><th width='2%'>Item</th><th width='8%'>Name</th><th width='3%'>Quantity</th><th width='3%'>Wholesaler</th><th width='4%'>Price</th><th width='15%'>Order Confirm</th></tr>");

		for (ItemBean i: orders)
		{
			out.write("<tr><td>"+i.getItemNumber()+"</td><td>"+i.getName()+"</td><td>"+i.getQuantity()+"</td><td>"+i.getSupID()+"</td><td>"+i.getCost()+"</td><td>"+i.getOrderConfirm()+"</td></tr>");
		}
		
		out.write("</table></body></html>");
		out.close();
		File report = new File("report.html");
		Desktop.getDesktop().open(report);
	
	}

}
