package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class FRUListener1
 *
 */
@WebListener
public class FRUListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, ServletRequestListener, ServletRequestAttributeListener {

    /**
     * Default constructor. 
     */

	private double totalCheckOutTime =0;
	private int totalCustomer =1;
	private int viewer=0;
	private double ave_fresh_checkout=0;
	private double totalAddItmeTime=0;
	final double MINISECOND_PER_SECOND = 1000;
	private Map<String,Integer> bag = new HashMap<String,Integer>();

	
    public FRUListener() {
        // TODO Auto-generated constructor stub
    	
    }

	/**
     * @see ServletRequestListener#requestDestroyed(ServletRequestEvent)
     */
    public void requestDestroyed(ServletRequestEvent sre) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent se) {
        // TODO Auto-generated method stub

    }

	/**
     * @see ServletRequestAttributeListener#attributeAdded(ServletRequestAttributeEvent)
     */
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        // TODO Auto-generated method stub
    	
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent se) {
        // TODO Auto-generated method stub
    	handle(se);
    }

	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent se) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent se) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent se) {
        // TODO Auto-generated method stub
    	handle(se);
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub
    	String id = se.getSession().getId();
    	if(se.getSession().getServletContext().getAttribute("map") == null)
    	{
    		se.getSession().getServletContext().setAttribute("map", bag);
    	}
    	if(se.getSession().getServletContext().getAttribute("viewer") == null)
    	{
    		se.getSession().getServletContext().setAttribute("viewer", viewer);
    	}
 
		this.bag = (HashMap<String, Integer>) se.getSession().getServletContext().getAttribute("map");
    	this.bag.put(id, 1);
    	se.getSession().getServletContext().setAttribute("map", this.bag);
        this.viewer= this.viewer +1;
        se.getSession().getServletContext().setAttribute("viewer", viewer);
    }

	/**
     * @see ServletRequestAttributeListener#attributeRemoved(ServletRequestAttributeEvent)
     */
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
    	String id = se.getSession().getId();
		this.bag = (HashMap<String, Integer>) se.getSession().getServletContext().getAttribute("map");
    	this.bag.remove(id);
    	se.getSession().getServletContext().setAttribute("map", this.bag);

    	
    }

	/**
     * @see ServletRequestAttributeListener#attributeReplaced(ServletRequestAttributeEvent)
     */
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletRequestListener#requestInitialized(ServletRequestEvent)
     */
    public void requestInitialized(ServletRequestEvent sre) {
        // TODO Auto-generated method stub

    }
    public void handle(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
         // this listener only respone to the first checkout
    	if(arg0.getName().equals("checkout"))
    	{
    		if(arg0.getSession().getAttribute("continue").equals("first"))
    		{
    		long startT = arg0.getSession().getCreationTime();
            double checkoutTime = arg0.getSession().getLastAccessedTime();
    	    double diffTime = (checkoutTime - startT)/MINISECOND_PER_SECOND;
             
             totalCheckOutTime = totalCheckOutTime + diffTime;
    	    ave_fresh_checkout = totalCheckOutTime /totalCustomer;
    	    this.totalCustomer = this.totalCustomer +1;
    	    
    	    arg0.getSession().getServletContext().setAttribute("ave_fresh_checkout", ave_fresh_checkout);
    		}
    	}
    	    if(arg0.getSession().getAttribute("newCart") != null && arg0.getSession().getAttribute("newCart").equals("new"))
    	    {
    	    	arg0.getSession().setAttribute("newCart", "old");
    	    	double st = arg0.getSession().getCreationTime();
    	    	double addItemTime = arg0.getSession().getLastAccessedTime();
    	    	double diff = (addItemTime - st)/MINISECOND_PER_SECOND;
    	    	this.totalAddItmeTime = this.totalAddItmeTime + diff;
    	    	double aveAddItemTime = this.totalAddItmeTime /this.totalCustomer;
    	    	arg0.getSession().getServletContext().setAttribute("aveAddItemTime", aveAddItemTime);
    	    	}
    		 	
    }
	
}
