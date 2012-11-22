package model;


import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="order")
@XmlType(propOrder = {"customer", "items", "total", "shipping", "HST", "grandTotal"} )
public class OrderWrapper {

	private int id;
	
	private String submitted;
	
	private ClientBean customer;
	
	private ItemSBean items;
	//private ShoppingCartHelper shoppingCart;
	
	private double total;
	
	private double shipping;
	
	private double HST;
	
	private double grandTotal;
	
	
	public OrderWrapper() {
		// TODO Auto-generated constructor stub
	}

	@XmlAttribute
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public String getSubmitted() {
		return submitted;
	}


	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	@XmlElement
	public ClientBean getCustomer() {
		return customer;
	}


	public void setCustomer(ClientBean customer) {
		this.customer = customer;
	}

	@XmlElement
	public ItemSBean getItems() {
		return items;
	}


	public void setItems(ItemSBean items) {
		this.items = items;
	}

	@XmlElement
	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}

	@XmlElement
	public double getShipping() {
		return shipping;
	}


	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	@XmlElement
	public double getHST() {
		return HST;
	}


	public void setHST(double hST) {
		HST = hST;
	}

	@XmlElement
	public double getGrandTotal() {
		return grandTotal;
	}


	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}


	/**
	 * @param id
	 * @param submitted
	 * @param customer
	 * @param items
	 * @param total
	 * @param shipping
	 * @param hST
	 * @param grandTotal
	 */
	public OrderWrapper(int id, String submitted, ClientBean customer,
			ItemSBean items, double total, double shipping, double hST,
			double grandTotal) {
		this.id = id;
		this.submitted = submitted;
		this.customer = customer;
		this.items = items;
		this.total = total;
		this.shipping = shipping;
		HST = hST;
		this.grandTotal = grandTotal;
	}
	
	
	
	

}
