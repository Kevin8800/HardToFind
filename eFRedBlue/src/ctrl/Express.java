package ctrl;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ClientBean;
import model.FRUModel;
import model.ShoppingCartHelper;

/**
 * Servlet implementation class Express
 */
public class Express extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Express() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String express = request.getParameter("doexpress");
		String target ;
		HttpSession session = request.getSession();
		if (express != null)
		{
			if (express.equals("Add More")|| express.equals("Add"))
			{
				
				ShoppingCartHelper cart = (ShoppingCartHelper)session.getAttribute("cart");
				if (cart == null)
				{	
					cart = new ShoppingCartHelper();

				}
				if(session.getAttribute("newCart") == null)
				{		
				    session.setAttribute("newCart", "new"); // for listener
				   
				}else
				{
					session.setAttribute("newCart", "old"); // for listener
				  
				}
				try 
				{
					FRUModel fru = (FRUModel) this.getServletContext().getAttribute("fru");
			
					if(!fru.addToCart(cart, request.getParameter("itemToAdd"),request.getParameter("qtyToAdd")))
					{
						request.setAttribute("expressError", "Item not found");	
					}
					session.setAttribute("cart", cart);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					response.sendError(500);
				}
				target ="/express1.jspx";
			}
			else if (express.equalsIgnoreCase("remove"))
			{
				if ((ShoppingCartHelper)session.getAttribute("cart")!= null)
				{
					try 
					{
						FRUModel fru = (FRUModel) this.getServletContext().getAttribute("fru");
						ShoppingCartHelper cart = (ShoppingCartHelper) session.getAttribute("cart");
						fru.updateCart(cart, request.getParameter("itemToAdd"),"0");
						session.setAttribute("cart", cart);
						target = "/express1.jspx";

					}
					catch(Exception e)
					{
						e.printStackTrace();
						response.sendError(500);	
						target = "/my500.jspx";
					}
	
				}
				else
				{
					response.sendError(500);	
					target = "/my500.jspx";
				}
			}
			else if (express.equalsIgnoreCase("Checkout"))
			{
				if ((ClientBean)session.getAttribute("client") != null)
				{
					target = "/cart.jspx";
				}
				else
				{
					session.setAttribute("page", "express1");
					target = "/login.jspx";
				}
			}
			else if (express.equalsIgnoreCase("Cancel"))
			{
				target ="/index.jspx";
			}
			else
			{					
				target = "/my404.jspx";
			}
		}
		else
		{
			target = "/my404.jspx";
			}
		
		request.getRequestDispatcher(target).forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
