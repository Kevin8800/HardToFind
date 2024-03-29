package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;


/**
 * 
 * @author
 *	FoodsRUs model class, use this class to coordinate operations between DAO, BEAN, and Controller.
 */

public class FRUModel {

	//the FoodRUDAO Database access object to be instantiated. 
	private FoodRUDAO dao;
    private List<ItemBean> list;
	public FRUModel() throws Exception
	{
		
		this.dao = new FoodRUDAO();
	}
	
	/**
	 * The method called by controller, and provide caller the category information
	 * @return the List of existing categories in the database.
	 * @throws SQLException  -when encounter issue with database
	 */
	public List<CategoryBean> retrieveCategory() throws SQLException 
	{
		return this.dao.retrieveCategory();
	}
	
	/**
	 * For the controller to get all items belongs to a specific category
	 * @param catID - the category id
	 * @return The list of items that belong to this category
	 * @throws SQLException  -when encounter issue with database
	 */
	public List<ItemBean> retrieveItems(int catID) throws SQLException  
	{
		return this.dao.retrieveItems(catID);
	}
	
	/**
	 * The method return a single item, can be used for express order by item number.
	 * @param itemNumber
	 * @return -the item with specific item number.
	 * @throws SQLException  -when encounter issue with database
	 */
	public ItemBean retrieveItem(String itemNumber) throws SQLException 
	{
		return this.dao.retrieveItem(itemNumber);
	}
	
	/**
	 * The method return a single item, can be used for express order by item number.
	 * @param itemNumber
	 * @return -the validated client
	 * @throws SQLException  -when encounter issue with database
	 */
	public ClientBean validatePassword(String ClientID, String password) throws SQLException  
	{
		return this.dao.validatePassword(ClientID, password);
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<ItemBean> getSearch(String para) throws SQLException
	{
			String si = para;
			String regex1 = "[0-9]+[a-zA-Z]*[0-9]*";
			List<ItemBean> ibl;
			if(si.matches(regex1))
			{
			  ibl = this.searchItemNumber(si);		  
			}else
			{
			 ibl = this.searchItemName(si);
			}
	
		return ibl;
				
	}
    public List<ItemBean> searchItemName(String name) throws SQLException
    {
	    list = dao.retrieveItemsName(name);
	    return list;
    }
    
    
    /**
     * 
     * @param number - the item number to search
     * @return - the list of items match the number pattern
     * @throws SQLException - when ecountered unhanlded the database issue.
     */
    public List<ItemBean> searchItemNumber(String number) throws SQLException
    {
	    list = (List<ItemBean>) dao.retrieveItemsNumber(number);
	    return list;
    }
    
    /**
     * Add item, specified by itemNumber, and quantity to shopping cart.
     * @param shoppingCart
     * @param itemNumber
     * @param qty
     * @throws SQLException 
     * @throws Exception
     */
    public boolean addToCart(ShoppingCartHelper shoppingCart, String itemNumber, String qty) throws SQLException 
    {
    	boolean result = true;
    	
    	if(shoppingCart.hasItem(itemNumber))
    	{
    		shoppingCart.incrementQty(itemNumber, Integer.parseInt(qty));
    		shoppingCart.checkOutUpdate();
    	}
    	else
    	{
    		ItemBean item = this.retrieveItem(itemNumber);
    		if (item != null )
    		{
    	  		item.setQuantity(Integer.parseInt(qty));
        		shoppingCart.add(item);
        		shoppingCart.checkOutUpdate();
    		}else
    		{
    			result = false;
    		}
  
    	}
		return result;
    	
    }
    
    /**
     * update shopping with specified the qty for a particular item in cart.
     * @param shoppingCart - the cart working on
     * @param itemNumber - the item number for the itme to be updated
     * @param qty - the quantity for the item to be updated.
     * @throws SQLException - when encountered database issue
     */
    public void updateCart(ShoppingCartHelper shoppingCart, String itemNumber, String qty) throws SQLException
    {
    	//do we need to check the existence of the item(in cart)?
    	if (!shoppingCart.hasItem(itemNumber))
    	{
    		this.addToCart(shoppingCart, itemNumber, qty);
    	}else{
    		shoppingCart.updateQty(itemNumber, Integer.parseInt(qty));
    	}
    	shoppingCart.checkOutUpdate();
    }
    
    /**
     * 
     * @param shoppingCart
     */
    public void checkOut(ShoppingCartHelper shoppingCart)
    {
    	//more to be added, not sure how controller will use it.
    	
    	// to update total price, shipping, HST etc.
    	shoppingCart.checkOutUpdate();
    }
    
    /**
     * the checkOut method must be called before calling this method
     * @param shoppingCart
     * @param totalOrderPerUser
     * @param totalOrders
     * @throws JAXBException 
     * @throws IOException 
     */
    public void exportPO(String filename, int id, ClientBean customer, ShoppingCartHelper shoppingCart) throws JAXBException, IOException
    {
    		
		JAXBContext jc = JAXBContext.newInstance(OrderWrapper.class);
		
		String today = new java.sql.Date(System.currentTimeMillis()).toString();
		

		OrderWrapper ow = new OrderWrapper(id, today, customer, new ItemSBean(shoppingCart.getItems()), shoppingCart.getTotal(), shoppingCart.getShipping(), shoppingCart.getHST(), shoppingCart.getGrandTotal());
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		
		sw.write("<?xml version='1.0'?>\n");
		
		sw.write("<?xml-stylesheet type='text/xsl' href='../xsl/po.xsl'?>\n");
		marshaller.marshal(ow, new StreamResult(sw));

		FileWriter fw = new FileWriter(filename);
		fw.write(sw.toString());
		fw.close();
    }
    

}
