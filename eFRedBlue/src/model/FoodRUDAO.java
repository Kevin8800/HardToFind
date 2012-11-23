package model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FoodRUDAO {

	private DataSource dataSource;
	
	/**
	 * 
	 * @throws Exception
	 */
	public FoodRUDAO() throws Exception
	{
		this.dataSource = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/CSE");
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CategoryBean> retrieveCategory() throws SQLException 
	{
		List<CategoryBean> list = null;
		Connection con = null;
		Statement s = null;
		try {
			con = this.dataSource.getConnection();
			s = con.createStatement();
			String query = "Select * from roumani.category";
			ResultSet r = s.executeQuery(query);
			list = new ArrayList<CategoryBean>();
			while (r.next()) 
			{
				Blob picture = r.getBlob("PICTURE");
				CategoryBean cb = new CategoryBean(r.getInt("ID"), r.getString("NAME"), r.getString("DESCRIPTION"),	picture.getBytes(1,(int)picture.length() ));				
				list.add(cb);
			}
			
		} finally {
			if (con != null) con.close();
			if (s != null) s.close();
		}
		
		return list;
	}


	/**
	 * 
	 * @param catID
	 * @return
	 * @throws SQLException 
	 */
	public List<ItemBean> retrieveItems(int catID) throws SQLException 
	{
		List<ItemBean> list = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.item where catid=?";
			ps = con.prepareStatement(query);
			ps.setInt(1, catID);
	
			ResultSet r = ps.executeQuery();
			list = new ArrayList<ItemBean>();
			while (r.next()) {
				ItemBean ib = new ItemBean(r.getString("NUMBER"), r.getString("NAME"), r.getDouble("PRICE"), catID);
				list.add(ib);				 
			}
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return list;
	}


	/**
	 * the method will be called by model class to retrieve a single item
	 * @param itemNumber
	 * @return the item identified by the unique item number
	 * @throws SQLException
	 */
	public ItemBean retrieveItem(String itemNumber) throws SQLException 
	{
		ItemBean ib = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.item where number=?";
			ps = con.prepareStatement(query);
			ps.setString(1, itemNumber);
	
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				ib = new ItemBean(r.getString("NUMBER"), r.getString("NAME"), r.getDouble("PRICE"), r.getInt("CATID"));
			}
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return ib;
	}
	
	/*this method will be used for search.java*/
	public List<ItemBean> retrieveItemsPrice(String itemPrice) throws SQLException 
	{
		List<ItemBean> list = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.item where PRICE like '" +
					itemPrice +"%'" ;
			ps = con.prepareStatement(query);
	
			ResultSet r = ps.executeQuery();
			list = new ArrayList<ItemBean>();
			while (r.next()) {
				ItemBean ib = new ItemBean(
						r.getString("NUMBER"),
						r.getString("NAME"),
						r.getDouble("PRICE"),
						r.getInt("catID")
						);
				list.add(ib);				 
			}
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return list;
	}

	
	/*this method will be used for search.java*/
	public List<ItemBean> retrieveItemsName(String itemName) throws SQLException 
	{
		List<ItemBean> list = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.item where UPPER(NAME) like UPPER('%" +
				itemName +"%')" ;
			ps = con.prepareStatement(query);

	
			ResultSet r = ps.executeQuery();
			list = new ArrayList<ItemBean>();
			while (r.next()) {
				ItemBean ib = new ItemBean(
						r.getString("NUMBER"),
						r.getString("NAME"),
						r.getDouble("PRICE"),
						r.getInt("catID")
						);
				list.add(ib);				 
			}
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return list;
	}

	
	
	/*this method will be used for search.java*/
	public List<ItemBean> retrieveItemsNumber(String itemNumber) throws SQLException 
	{
		List<ItemBean> list = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.item where NUMBER like '%" +
				itemNumber +"%'" ;
			ps = con.prepareStatement(query);
		
	
			ResultSet r = ps.executeQuery();
			list = new ArrayList<ItemBean>();
			while (r.next()) {
				ItemBean ib = new ItemBean(
						r.getString("NUMBER"),
						r.getString("NAME"),
						r.getDouble("PRICE"),
						r.getInt("catID")
						);
				list.add(ib);				 
			}
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return list;
	}

	/**
	 * the method will validate client password
	 * @param ClientID, password
	 * @return the item identified by the unique item number
	 * @throws SQLException
	 */
	public ClientBean validatePassword(String ClientID, String password) throws SQLException 
	{
		Connection con = null;
		PreparedStatement ps = null;
		ClientBean client = null;
		try {
			con = this.dataSource.getConnection();

			String query = "Select * from roumani.client where number=?";
			ps = con.prepareStatement(query);
			ps.setString(1, ClientID);
			ResultSet r = ps.executeQuery();
			if (r.next())
			{
				if (password.equals(r.getString("PASSWORD")))
					{
					
						client = new ClientBean(Integer.parseInt(r.getString("NUMBER")),r.getString("NAME"),r.getString("PASSWORD"),r.getString("RATING"));
					}
			}
			
		} finally {
			if (con != null) con.close();
			if (ps != null) ps.close();
		}
		return client;
	}

}
