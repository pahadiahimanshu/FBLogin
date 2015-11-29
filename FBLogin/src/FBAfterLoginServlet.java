/*
 * @author =  himanshu pahadia
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import com.restfb.types.User;


@WebServlet("/FBAfterLoginServlet")
public class FBAfterLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FBAfterLoginServlet() 
    {
    	super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
		doPost(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	
		HttpSession session = request.getSession();
		Date createTime = new Date(session.getCreationTime());
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		Integer visitCount = new Integer(0);
		String visitCoundKey = new String("visitCount");
		String userIDKey = new String("userID");
		String userID = session.getId();
		
		
		
		pw.println("<br>The session id is "+userID);
		
		
		String fbCode = request.getParameter("code");
		String state = request.getParameter("state");
//		pw.println("<br>Code returned from fb == "+fbCode);
//		pw.println("<br>STATE IS "+state);
		
		String accessToken = getFBAccessToken(fbCode,pw);
		String appSecret = "c5faa6fab48f6a785f0598ec11b3dddd";
		
//		pw.println("<br>the access token is thisss "+accessToken);
		
//		pw.println(userID);
		pw.print("<br>");
//		pw.println(state);
		if(state.equals(userID))
		{
			FacebookClient facebookClient = new DefaultFacebookClient(accessToken, appSecret);
			User user = facebookClient.fetchObject("me", User.class);
			Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class);
//			JsonObject jsonObject = facebookClient.fetchObject("me/feed",JsonObject.class, Parameter.with("summary", true),Parameter.with("limit", 1));
//	        long count = jsonObject.getJsonObject("summary").getLong("total_count");
//	        System.out.println(jsonObject);
			
//			Connection<Post> myFeed = facebookClient.fetchConnection("me/posts", Post.class, Parameter.with("fields", "from,to,likes.summary(true),comments.summary(true)"));
			try
			{
				pw.println("<br> Hello, <h1>"+user.getName()+"</h1><br>");
				
				
				int i = 1;
				pw.println("<bold>Your Posts</bold>");
//				for (List<Post> myFeedConnectionPage : myFeed)
//				{
//					System.out.println("Getting list");
//					for (Post post : myFeedConnectionPage)
//					{
//						System.out.println("\n"+i+".\n"+post.getMessage()+"\n"+post.getId()+"\t"+post.getCreatedTime().toString());
////						System.out.println("\tLIKES="+post.getLikes().getData().size());
					
//						i++;
//						if(i>10)
//							break;
//					}
//					System.out.println("########################");
//					if(i>10)
//						break;
//				}
//				 while (myFeed.hasNext()) 
//				 {
//					 myFeed = facebookClient.fetchConnectionPage(myFeed.getNextPageUrl(),Post.class);
//					
//				 }
				int k = 0;
				for(List<Post> feed : myFeed)
				{
//					pw.println("Finding feed : "+feed.toString());
					for(Post post : feed)
					{
//						pw.println("Post id is " + post.getId().toString());
//						System.out.println("I am running k="+k);
						if(k++ > 1)
							break;
						JsonObject jsonObject = null;
						JsonObject jsonComm = null;
						try
						{
							try
							{
								jsonObject = facebookClient.fetchObject(post.getId() + "/likes",JsonObject.class, Parameter.with("summary", true),Parameter.with("limit", 10));
								
							}
							catch(Exception e)
							{
								System.out.println("error finding likes object");
							}
							try
							{
								jsonComm = facebookClient.fetchObject(post.getId()+"/comments",JsonObject.class,Parameter.with("summary", true),Parameter.with("limit", 10));
//								System.out.println(jsonComm);
							}
							catch(Exception e)
							{
								System.out.println("error finding comments json");
							}
							
							long comment = jsonComm.getJsonObject("summary").getLong("total_count");
							long count = jsonObject.getJsonObject("summary").getLong("total_count");
							
							
//							long id = jsonObject.getJsonObject("data").getLong("id");
//							System.out.println("iski id a"+id);
							System.out.println("\n"+k+".\nID == "+post.getId()+"\tMESSAGE == "+post.getMessage()+"\nLIKES == "+count+"\tCOMMENT == "+comment+"\t CREATION == "+post.getCreatedTime());
							System.out.println("\nLIKERS "+jsonObject.getString("data"));
							System.out.println("\nCOMMENTERS "+jsonComm.getString("data"));
							
						}
						catch(Exception e)
						{
//							System.out.println("Error occurred");
							k--;
						}
					}
					if(k > 1)
						break;
				}
			}
			catch(Exception e)
			{
			
			}
		}
		else
		{
			pw.println("<br>ERROR VALIDATION! SORRY!<br>");
		}
		
		pw.println("<br>END<br>");
	}
	
	private String readURL(URL url) throws IOException {
		 
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  InputStream is = url.openStream();
		 
		  int r;
		 
		  while ((r = is.read()) != -1) {
		   baos.write(r);
		  }
		 
		  return new String(baos.toByteArray());
		 
		 }

	
	private String getFBAccessToken(String fbCode,PrintWriter pw) throws IOException
	{
		String token = null;
//		pw.println("<br>INSIDE GETTING FBACCESS TOKEN<br>");
		if(fbCode != null && !(fbCode.equals("")))
		{
			 String appId = "1511366705857240";
		     String appSecret = "c5faa6fab48f6a785f0598ec11b3dddd";
		     
		     
		     
		     
		     // #############CHANGE THIS URL TO OPEN THE SECOND SERVLET
//		     String redirectUrl = "http://localhost:8080/examples/servlets/servlet/FBAfterLoginServlet";
		     String redirectUrl = "http://localhost:8080/FBLogin/FBAfterLoginServlet";
		     
		     
		     
		     String newUrl = "https://graph.facebook.com/oauth/access_token?client_id="
	                    + appId + "&redirect_uri=" + redirectUrl + "&client_secret="
	                    + appSecret + "&code=" + fbCode;
		     URL url = new URL(newUrl);
		     
		     String result = readURL(url);
		     String[] pairs = result.split("&");
//		     pw.println("<br>LEts iterate the list=====<br>");
		     for(String pair: pairs)
		     {
		    	 String[] kv = pair.split("=");
//		    	 pw.println("<br>values=="+pair);
		    	 if (kv[0].equals("access_token")) 
		    	 {
		    		 token = kv[1];
		    	 }
		     }
		     
		     
		     
		     
		     //print everything on the browser so to work or configure your eclipse to suprress
		     
		}
		
//		pw.println("<br>returning<br>");
		return token;
	}
}
