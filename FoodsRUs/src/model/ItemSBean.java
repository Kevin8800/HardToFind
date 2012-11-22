package model;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

/**
 * For xml order Wrapper
 * @author 
 *
 */
public class ItemSBean {
	
	private Collection<ItemBean> items;
	
	
	
	/**
	 * 
	 */
	public ItemSBean() {
		// TODO Auto-generated constructor stub
	}

	public ItemSBean(Collection<ItemBean> items) 
	{
		this.items = items;
	}

	/**
	 * @return the items
	 */
	@XmlElement(name="item")
	public Collection<ItemBean> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection<ItemBean> items) {
		this.items = items;
	}
	
	

}
