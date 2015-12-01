

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FBLoginServlet")
public class FBLoginServlet extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	
       
    public FBLoginServlet() 
    {
        super();

    }

	public void init(ServletConfig config) throws ServletException 
	{
	
	}

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter pw = response.getWriter();
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		 HttpSession session = request.getSession(true);
	     Date createTime = new Date(session.getCreationTime());
	     Date lastAccessTime = new Date(session.getLastAccessedTime());
	     Integer visitCount = new Integer(0);
	     String visitCoundKey = new String("visitCount");
	     String userIDKey = new String("userID");
	     String userID = session.getId();
	     
	     if(session.isNew())
	     {
	    	 session.setAttribute(userIDKey, userID);
	     }
	     else
	     {
	    	 visitCount = (Integer)session.getAttribute(visitCoundKey);
	    	 visitCount += 1;
	    	 userID = (String)session.getAttribute(userIDKey);
	     }
	     
	     session.setAttribute(visitCoundKey, visitCount);
	     response.setContentType("text/html");
	     pw.println("the session id is "+userID);
	     String appId = "1511366705857240";
	     
	     
	     // #############CHANGE THIS URL TO OPEN THE SECOND SERVLET
	     String redirectUrl = "http://localhost:8080/FBLogin/FBAfterLoginServlet";
	     
	     
	     String LoginURL = "https://www.facebook.com/dialog/oauth?client_id="
	                + appId + "&redirect_uri=" + redirectUrl
	                + "&scope=public_profile,user_friends,user_about_me,user_location,user_posts,user_status,user_photos,email,user_likes,user_birthday&state=" + userID;
	     pw.println("Redirecting to url");
	     try {
	    	    Thread.sleep(3000);                 //1000 milliseconds is one second.
	    	} catch(InterruptedException ex) {
	    	    Thread.currentThread().interrupt();
	    	}
	     response.sendRedirect(LoginURL);
	     
	}

}
