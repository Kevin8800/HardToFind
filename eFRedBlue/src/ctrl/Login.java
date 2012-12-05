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

/**
 * Servlet implementation class Login, used for login 
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String target;
		HttpSession session = request.getSession();
		/* first time come into the login page*/
		if (login==null)
		{
			target = "/login.jspx";
		}
		
		/* first time come into the login page*/
		else
		{
			if (login.equals("login"))
			{
				String clientID = request.getParameter("ClientID");
				String password =request.getParameter("Password");
				request.setAttribute("ClientID", clientID);
				request.setAttribute("Password", password);

				//check db, if user exists then login
				FRUModel model = (FRUModel) this.getServletContext().getAttribute("fru"); 
				
				if(clientID!=null && password!=null)
				{
					try 
					{
						/* check if the input is a string or not*/
						Integer.parseInt(clientID);
						Integer.parseInt(password);
						
						ClientBean client = model.validatePassword(clientID, password);
						if (! (client == null))
						{
							session.setAttribute("client", client);	
							/* remeber the page and go back after login*/
							if (session.getAttribute("page") != null)
							{
								target = "/" +session.getAttribute("page")+".jspx";
								session.setAttribute("page", null);
							}
							else
							{
								target = "/index.jspx";
								session.setAttribute("page", null);
							}
						}
						/* can not login due to wrong credentials, show errors */
						else
						{
							request.setAttribute("loginError", "Login credential is incorrect");
							target = "/login.jspx";
						}
					}
					/* input are not numbers &*/
					catch (NumberFormatException e)
					{
						e.printStackTrace();
						request.setAttribute("loginError", "Both username and password should be numbers");
						target = "/login.jspx";
					}
					/* input are not numbers */
					catch (Exception e) 
					{
						e.printStackTrace();
						request.setAttribute("loginError", e.getMessage());
						target = "/login.jspx";
					}
				}
				/* if user name or password is empty */
				else
				{
					request.setAttribute("loginError", "ClientID or Password can not be empty");
					target = "/login.jspx";
				}
			}
			/* come from somewhere else, go back to index page*/
			else
			{
				target = "/index.jspx";		
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
