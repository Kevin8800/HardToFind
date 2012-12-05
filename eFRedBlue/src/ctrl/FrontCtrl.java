package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryBean;
import model.FRUModel;
import util.Constants;

/**
 * Servlet implementation class FontCtrl
 */
public class FrontCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontCtrl() {
        super();
        // TODO Auto-generated constructor stub
    }

    	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		//the singleton model need to be initialized here
		FRUModel fru;
		try {
			fru = new FRUModel();
			List<CategoryBean> cat = null;
			/* try 5 times if DB connection is not avialiable */
			for (int i = 0 ; i < Constants.number_of_retry; i++)
			{
				try 
				{
					cat =  fru.retrieveCategory();
					break;
				}
				catch (java.sql.SQLNonTransientConnectionException sql)
				{
					synchronized(this)	
					{
						this.wait(5000);
					}
				}
			}
			
			this.getServletContext().setAttribute("fru", fru);
			// Get all the categories and items from the DB and set them the application scope
			this.getServletContext().setAttribute("categories", cat);
			for (CategoryBean c : cat )
			{
				String itemName = "item" + c.getCatID();
				this.getServletContext().setAttribute(itemName,fru.retrieveItems(c.getCatID()));
			}
			/*Set Id that will be used for tracking different sessions */
			int id = 0;
			this.getServletContext().setAttribute("id", id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String info = request.getPathInfo();
		/* check for URL and use name based dispatcher to redirect to the right servelet*/
		
		/*First Visit or request for categories */
		if (info.matches("/Start")|| info.matches("/FRU")||info.equals("/")|| info.matches("/category"))
		{
			this.getServletContext().getNamedDispatcher("FRU").forward(request, response);
		}
		/*if it is the request for shopping cart*/
		else if (info.matches("/ShoppingCart"))
		{
			this.getServletContext().getNamedDispatcher("ShoppingCart").forward(request, response);
		}
		/*if it is the request for login */
		else if (info.matches("/Login"))
		{
			this.getServletContext().getNamedDispatcher("Login").forward(request, response);
		}
		/*if it is the request for Express*/
		else if (info.matches("/Express"))
		{
			this.getServletContext().getNamedDispatcher("Express").forward(request, response);
		}
		/*if it is the request for Express*/
		else if (info.matches("/CheckOut"))
		{
			this.getServletContext().getNamedDispatcher("CheckOut").forward(request, response);
		}
		/*if it is the request for Statistic*/
		else if (info.matches("/Statistic"))
		{
			this.getServletContext().getNamedDispatcher("Statistic").forward(request, response);
		}
		/*if it is none of the case above, sent to 404 page*/
		else
		{
			response.sendError(404);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request,response);
	}
}
