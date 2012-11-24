package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ClientBean;
import model.FRUModel;
import model.ItemBean;
import model.ShoppingCartHelper;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String add = request.getParameter("docart");
		String target ;
		HttpSession session = request.getSession();
		if (add != null)
		{
			if (add.equals("AddToCart"))
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
			else if (add.equalsIgnoreCase("Update"))
			{
				if ((ShoppingCartHelper)session.getAttribute("cart")!= null)
				{
					try 
					{
						FRUModel fru = (FRUModel) this.getServletContext().getAttribute("fru");
						ShoppingCartHelper cart = (ShoppingCartHelper) session.getAttribute("cart");
						fru.updateCart(cart, request.getParameter("itemToAdd"),request.getParameter("qtyToAdd"));
						session.setAttribute("cart", cart);
						target = "/cart.jspx";

					}
					catch(Exception e)
					{
						e.printStackTrace();
						target = "/my500.jspx";
					}
	
				}
				else
				{
					System.out.println("Error: Shopping cart does not exsit when try to modify it");
					target = "/my500.jspx";
				}
			}
			else if (add.equalsIgnoreCase("Checkout"))
			{
				if ((ClientBean)session.getAttribute("client") != null)
				{
					target = "/checkout.jspx";
				}
				else
				{
					target = "/login.jspx";
				}
			}
			else if (add.equalsIgnoreCase("Continue"))
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
		doGet(request,response);
	}

}
