/**
 * 
 */
package model;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import util.*;

/**
 * @author 
 *
 */
@XmlRootElement(name="item")
@XmlType(name="item", propOrder = {"name", "price", "quantity", "extended"})
public class ItemBean {

	public ItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}

		
	private String itemNumber;
	

	private String name;
	

	private double price;
	

	private int quantity;
	

	private double extended;
	
	//Category ID;

	private int catID;

	private int onOrder;

	private int reOrder;

	private double cost;

	private int supID;
		
	/**
	 * @param itemNumber
	 * @param itemName
	 * @param price
	 * @param catID
	 */
	public ItemBean(String itemNumber, String itemName, double price, int catID) 
	{
		this.itemNumber = itemNumber;
		this.name = itemName;
		this.price = Double.valueOf(Constants.twoDForm.format(price));
		this.catID = catID;
	}

	/**
	 * @return the itemNumber
	 */
	@XmlAttribute(name="number")
	public String getItemNumber()
	{
		return itemNumber;
	}

	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(String itemNumber)
	{
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price)
	{
		this.price = price;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity()
	{
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
		this.extended = Double.valueOf(Constants.twoDForm.format(this.quantity * this.price));
		
	}

	/**
	 * @return the extended
	 */
	@XmlElement
	public double getExtended()
	{
		return extended;
	}

	/**
	 * @return the catID
	 */
	@XmlTransient
	public int getCatID()
	{
		return catID;
	}

	/**
	 * @return the cost
	 */
	@XmlTransient
	public double getCost()
	{
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost)
	{
		this.cost = cost;
	}

	/**
	 * @return the supID
	 */
	@XmlTransient
	public int getSupID()
	{
		return supID;
	}

	/**
	 * @param supID the supID to set
	 */
	public void setSupID(int supID)
	{
		this.supID = supID;
	}

	

}
