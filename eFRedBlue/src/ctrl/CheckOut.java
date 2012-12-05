package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import util.*;

import model.ClientBean;
import model.FRUModel;
import model.ShoppingCartHelper;

/**
 * Servlet implementation class CheckOut
 */
public class CheckOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOut() {   
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 *  
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*In this page , customer can do check out or continue shopping which is go back to the index page
		 */
		
		String checkout = request.getParameter("checkout");
		String target;
		HttpSession session =  request.getSession(false);
		
		// used for statistics 
		if(request.getSession().getAttribute("continue") == null)
		{
			request.getSession().setAttribute("continue", "first");
		}
		
		/*The first time come into check out */
		if (checkout==null)
		{
			if (session == null || (ClientBean)session.getAttribute("client") ==null)
			{
				session.setAttribute("page","checkout");
				target = "/login.jspx";
			}
			else
			{
				target="/checkout.jspx";	
			}
		}
		
		/* Customer click submit button on checkout page */

		else
		{
			if (checkout.equals("Submit") )
			{
				ShoppingCartHelper cart = (ShoppingCartHelper) request.getSession().getAttribute("cart");

				//do check out 
				if (session == null || (ClientBean)session.getAttribute("client") ==null)
				{
					request.setAttribute("checkoutError", "You haven't logged in, please login before check out");
					target = "/login.jspx";
				}
				else if (cart== null  || cart.getItems().size() == 0) 
				{
					request.setAttribute("checkoutError", "Can not check out, your cart is empty");
					target="/checkout.jspx";	
				}
				else
				{
									
					FRUModel model = (FRUModel)this.getServletContext().getAttribute("fru");
					ClientBean client = (ClientBean)request.getSession().getAttribute("client") ;
					String filename = Constants.FOLDERTOEXPORT + client.getClientName()+"_"+(Integer)this.getServletContext().getAttribute("id") 
								+ Constants.XMLEXTENSION;
					request.setAttribute("filename", filename); 

					request.getSession().setAttribute("checkout", "checkout");// for counting the access time before checkout 
					try {
						cart = (ShoppingCartHelper) request.getSession().getAttribute("cart");
						model.checkOut(cart);
						model.exportPO(this.getServletContext().getRealPath(filename), (Integer)this.getServletContext().getAttribute("id"), 
								(ClientBean)request.getSession().getAttribute("client"), cart);
						request.getSession().setAttribute("continue", "again"); // for listener
						synchronized(this)
						{
							this.getServletContext().setAttribute("id", (Integer)getServletContext().getAttribute("id") +1);
						}
					} catch (JAXBException e) {
						e.printStackTrace();
						response.sendError(500);
					}
					finally
					{
						request.getSession().setAttribute("cart",null);

					}
					target= "/confirm.jspx";
				}
			}
			/* click on cancel button , go back to the main page*/
			else
			{
				target = "/index.jspx";
				request.getSession().setAttribute("continue", "again"); // for listener
				

			}
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
