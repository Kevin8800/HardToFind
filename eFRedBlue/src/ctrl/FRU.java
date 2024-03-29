package ctrl;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.*;

/**
 * Servlet implementation class FRU
 */
public class FRU extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FRU() {
        super();
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* In the main page, we have four buttons, login , shopping cart, check out and express check out
		 * the name of the button is called doit.
		 * Customer can also select a category, In this case, doit will be null
		 */
		String target;
		String doit = request.getParameter("doit");
		HttpSession session= request.getSession();
		FRUModel model = (FRUModel) this.getServletContext().getAttribute("fru"); 
		/* customer select a catagory */
		if (doit == null)
		{
			try
			{
				/* check if customer select a category */
				if(request.getParameter("selectedCategory")!= null)
				{
					String itemName = "item" + request.getParameter("selectedCategory");
					request.setAttribute("item",this.getServletContext().getAttribute(itemName));
					target = "/category.jspx";
				}
				
				/* check if customer adding items to shopping cart*/
				else if (request.getParameter("add")!= null)
				{	
					String add = request.getParameter("add");
					if (add.equals("AddToCart"))
					{
					
						ShoppingCartHelper cart = (ShoppingCartHelper)session.getAttribute("cart");
						if (cart == null)
						{	
							cart = new ShoppingCartHelper();
						}
						
						try 
						{
							FRUModel fru = (FRUModel) this.getServletContext().getAttribute("fru");
							fru.addToCart(cart, request.getParameter("itemToAdd"),request.getParameter("qtyToAdd"));	
							
							session.setAttribute("cart", cart);
						}
						catch (SQLException e)
						{
							e.printStackTrace();
							response.sendError(500);
						}
						target ="/cart.jspx";
					}
					/* if it is none of the above , send to index page*/

					else
					{
						target ="/index.jspx";

					}
				}
				/* if URL does not match , send to index page*/
				else
				{		
					target = "/index.jspx";
				}
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				target = "/exception.jspx";
			}
		}
		/* cusotmer click on one of the buttons */
		else 
		{	
			if (doit.equals("login"))
			{
				target = "/login.jspx";
			}
			else if (doit.equals("cart"))
			{
				target = "/cart.jspx";
			}
			else if (doit.equals("checkout"))
			{
				target = "/checkout.jspx";
			}
			else if (doit.equals("search"))
			{   //search according the part of the item name.

				try
				{
					request.setAttribute("item", model.getSearch(request.getParameter("searchItem")));
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				target="/category.jspx";
			}
			else if (doit.equals("logout"))
			{
				request.getSession(true).invalidate();
				target = "/index.jspx";
			}
			else if (doit.equals("express"))
			{
				target = "/express1.jspx";
			}
			/*if URL does not match one of the buttons above , send to page not found*/
			else
			{
				response.sendError(404);
				target ="/myError.jspx";
			}
		}
		RequestDispatcher rd= request.getRequestDispatcher(target);
		rd.forward(request, response);	
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet (request, response);
	}

}
