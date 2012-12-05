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
		/*there is some thing to add to shopping car*/
		if (add != null)
		{
			/* first time add an item*/
			if (add.equals("Add"))
			{
				
				ShoppingCartHelper cart = (ShoppingCartHelper)session.getAttribute("cart");
				if (cart == null)
				{	
					cart = new ShoppingCartHelper();

				}
				
				/* used for tacking sessions*/
				if(session.getAttribute("newCart") == null)
				{		
				    session.setAttribute("newCart", "new"); 
				   
				}else
				{
					session.setAttribute("newCart", "old"); 
				  
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
			
			/* update an existing item*/
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
			/* customer checkout orders*/
			else if (add.equalsIgnoreCase("Checkout"))
			{
				if ((ClientBean)session.getAttribute("client") != null)
				{
					target = "/checkout.jspx";
				}
				else
				{
					session.setAttribute("page","cart");
					target = "/login.jspx";
				}
			}
			/* customer want to add more item to cart*/
			else if (add.equalsIgnoreCase("Continue Shopping"))
			{
				target ="/index.jspx";
			}
			
			/* unknown request , go to error page*/
			else
			{					
				target = "/my404.jspx";
			}
		}
		/* unknown request , go to error page*/
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
