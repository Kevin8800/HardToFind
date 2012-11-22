package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Express
 */
@WebServlet("/Express")
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
		String express = request.getParameter("express");
		String target;
		HttpSession session = request.getSession();
		System.out.println("express" + express);
		if (express==null)
		{
			target = "/index.jspx";
		}
		else
		{
				if (express.equals("Order"))
				{
					System.out.println("order");
					target="/checkout.jspx";
				}
				else// cancel
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
