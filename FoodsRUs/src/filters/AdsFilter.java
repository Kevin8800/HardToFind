package filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FRUModel;
import model.ItemBean;
import model.ShoppingCartHelper;

/**
 * Servlet Filter implementation class Ads
 */
@WebFilter(dispatcherTypes =
{DispatcherType.FORWARD }, urlPatterns =
{ "/cart.jspx" },  initParams =
{ @WebInitParam(name = "baseItem", value = "1409S413"),
		@WebInitParam(name = "adItem", value = "2002H712") })
public class AdsFilter implements Filter
{

	private String baseItem;
	private String adItem;

	/**
	 * Default constructor.
	 */
	public AdsFilter()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{

		ShoppingCartHelper cart = (ShoppingCartHelper) ((HttpServletRequest) request)
				.getSession().getAttribute("cart");

		if (cart!=null && !cart.getItems().isEmpty()&& cart.hasItem(baseItem) && !cart.hasItem(adItem))
		{

			CharArrayReponseWrapper resWrapper = new CharArrayReponseWrapper(
					(HttpServletResponse) response);

			// pass the request along the filter chain
			chain.doFilter(request, resWrapper);

			String resp = resWrapper.toString();

			FRUModel fru = (FRUModel) request.getServletContext().getAttribute(
					"fru");
			try
			{
				ItemBean item = fru.retrieveItem(adItem);
				String replacement = "<br/> <div id='ad-hoc'> You may be interested in this item: <a href='FRU?doit=search&searchItem="
						+ this.adItem
						+ "' >"
						+ item.getName()
						+ "</a> </div> <br/>";

				String result = resp.replace("<div id=\"footer\">", replacement
						+ "<div id='footer'>");
				response.setContentLength(result.length());
				response.getWriter().write(result);

			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
		this.adItem = fConfig.getInitParameter("adItem");
		this.baseItem = fConfig.getInitParameter("baseItem");
	}

}
