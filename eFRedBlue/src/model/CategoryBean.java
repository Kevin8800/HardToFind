/**
 * 
 */
package model;

import java.sql.Blob;

/**
 * @author
 *
 */
public class CategoryBean {
	
	private int catID;
	private String name;
	private String description;
	private byte[] picture; 
	private String pic_name;
	


	public String getPic_name() {
		return pic_name;
	}


	public void setPic_name() {
		if (this.catID == 3)
		{
			pic_name= "meat.jpg";
		}
		else if (this.catID == 4)
		{
			pic_name= "cheese.jpg";
		}
		else if (this.catID ==5)
		{
			pic_name= "icecream.jpg";
		}
		else 
		{
			pic_name= "cereal.jpg";
		}		
	}


	/**
	 * The constructor for Category Bean
	 * @param catID
	 * @param name
	 * @param description
	 */
	public CategoryBean(int catID, String name, String description, byte[] picture) {
		super();
		this.catID = catID;
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.setPic_name();
	}


	/**
	 * @return the catID
	 */
	public int getCatID() {
		return catID;
	}


	/**
	 * @param catID the catID to set
	 */
	public void setCatID(int catID) {
		this.catID = catID;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		
    	byte[] bufPic = new byte[picture.length];
    	System.arraycopy(picture, 0, bufPic, 0, picture.length);
    	String base64Image = new sun.misc.BASE64Encoder().encode(bufPic);
    	String cleanedImage = base64Image.replace("\\s", "");
    	
		return cleanedImage;

	}


	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
}
