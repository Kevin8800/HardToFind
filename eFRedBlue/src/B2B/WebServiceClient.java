package B2B;

import javax.xml.soap.*;

import model.ItemBean;

import java.net.*;
import java.util.Collection;

/**
 * @author
 * 
 */
public class WebServiceClient {

	private static final String torontoTns = "http://red.cse.yorku.ca:4413/axis/YYZ.jws";
	private static final String vancouverTns = "http://red.cse.yorku.ca:4413/axis/YVR.jws";
	private static final String halifaxTns = "http://red.cse.yorku.ca:4413/axis/YHZ.jws";

	private static final String tSupplier = "Toronto";
	private static final String vSupplier = "Vancouver";
	private static final String hSupplier = "Halifax";

	private static final String key = "cse83150p4";

	/**
	 * 
	 */
	public WebServiceClient() {
		// TODO Auto-generated constructor stub
	}

	public static void order(Collection<ItemBean> items) throws SOAPException,
			MalformedURLException {

		SOAPConnection sc = SOAPConnectionFactory.newInstance()
				.createConnection();
		for (ItemBean item : items) {
			SOAPMessage msg = MessageFactory.newInstance().createMessage();
			MimeHeaders header = msg.getMimeHeaders();
			header.addHeader("SOAPAction", "");

			SOAPPart soap = msg.getSOAPPart();

			SOAPEnvelope envelope = soap.getEnvelope();
			SOAPBody body = envelope.getBody();

			SOAPElement element = body.addChildElement("quote")
					.addChildElement("itemNumber");

			element.addTextNode(item.getItemNumber());

			SOAPMessage toronto = sc.call(msg, new URL(torontoTns));
			SOAPMessage vancouver = sc.call(msg, new URL(vancouverTns));
			SOAPMessage halifax = sc.call(msg, new URL(halifaxTns));

			org.w3c.dom.Node tnode = toronto.getSOAPPart().getEnvelope()
					.getBody().getElementsByTagName("quoteReturn").item(0);
			org.w3c.dom.Node vnode = vancouver.getSOAPPart().getEnvelope()
					.getBody().getElementsByTagName("quoteReturn").item(0);
			org.w3c.dom.Node hnode = halifax.getSOAPPart().getEnvelope()
					.getBody().getElementsByTagName("quoteReturn").item(0);
			double tquote = Double.parseDouble(tnode.getTextContent());
			double vquote = Double.parseDouble(vnode.getTextContent());
			double hquote = Double.parseDouble(hnode.getTextContent());

			System.out
					.println("Toronto " + tquote + " " + item.getItemNumber());
			System.out.println("Vancouver " + vquote + " "
					+ item.getItemNumber());
			System.out
					.println("Halifax " + hquote + " " + item.getItemNumber());

			item.setCost(0);
			if (tquote > 0) {
				item.setCost(tquote);
				item.setSupID(tSupplier);
			}

			if (vquote > 0) {
				if (item.getCost() == 0 || vquote < item.getCost()) {
					item.setCost(vquote);
					item.setSupID(vSupplier);
				}
			}

			if (hquote > 0) {
				if (item.getCost() == 0 || hquote < item.getCost()) {
					item.setCost(hquote);
					item.setSupID(hSupplier);
				}
			}

		}

		for (ItemBean item : items) {

			SOAPMessage msg = MessageFactory.newInstance().createMessage();

			MimeHeaders header = msg.getMimeHeaders();
			header.addHeader("SOAPAction", "");

			SOAPPart soap = msg.getSOAPPart();
			SOAPEnvelope envelope = soap.getEnvelope();
			SOAPBody body = envelope.getBody();

			SOAPElement element = body.addChildElement("order");
			element.addChildElement("itemNumber").addTextNode(item.getItemNumber());
			element.addChildElement("quantity").addTextNode(String.valueOf(item.getQuantity()));
			element.addChildElement("key").addTextNode(key);

			String tns = halifaxTns;
			if (item.getSupID().equals(tSupplier)) {
				tns = torontoTns;
			} else if (item.getSupID().equals(vSupplier)) {

				tns = vancouverTns;
			}

			SOAPMessage resp = sc.call(msg, new URL(tns));

			org.w3c.dom.Node node = resp.getSOAPPart().getEnvelope().getBody()
					.getElementsByTagName("orderReturn").item(0);
//			System.out.println(item.getItemNumber() + " supplier "
//					+ item.getSupID() + " oder response ");
			if (node != null) item.setOrderConfirm(node.getTextContent());
		}
		sc.close();
	}

}
