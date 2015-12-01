/*
 * @author =  himanshu pahadia
 */

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
import com.restfb.types.Post.MessageTag;
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
		String comma = ",";
//		pw.println("<br>the access token is thisss "+accessToken);
		
//		pw.println(userID);
		pw.print("<br>");
//		pw.println(state);
		if(state.equals(userID))
		{
			FacebookClient facebookClient = new DefaultFacebookClient(accessToken, appSecret);
			User user = facebookClient.fetchObject("me", User.class);
			Connection<Post> myFeed = facebookClient.fetchConnection("me/feed", Post.class,Parameter.with("fields", "id,name,type,picture,tags,likes,shares,message_tags,privacy,comments,message,created_time,object_id"));
//			JsonObject jsonObject = facebookClient.fetchObject("me/feed",JsonObject.class, Parameter.with("summary", true),Parameter.with("limit", 1));
//	        long count = jsonObject.getJsonObject("summary").getLong("total_count");
//	        System.out.println(jsonObject);
			
//			Connection<Post> myFeed = facebookClient.fetchConnection("me/posts", Post.class, Parameter.with("fields", "from,to,likes.summary(true),comments.summary(true)"));
			try
			{
				pw.println("<br> Hello, <h1>"+user.getName()+"</h1><br>");
//				System.out.println("this is printed");
				String filename = "C:\\Users\\Himanshu\\git\\FBLoginRepository\\FBLogin\\userInfo\\";
				filename += user.getId();
				filename +=".csv";
				
//				System.out.println("FILE IS "+filename);
				BufferedWriter writer = new BufferedWriter( new FileWriter(filename,true));
//				System.out.println("made the buffer object");
				writer.write(user.getId() + comma + userID + comma +user.getName() );
				writer.newLine();
//				System.out.println("written first line");
				System.out.println(user.getId()+comma+userID);
				// fb id and session id in the first line
				
				
				
				int i = 1;
				pw.println("<bold>Your Posts</bold>");
			
				int k = 0;
//				System.out.println("i am here before for loop");
				for(List<Post> feed : myFeed)
				{
//					System.out.println("before second loop");
//					System.out.println("feed*****"+feed);
					for(Post post : feed)
					{
//						System.out.println("inside second loop yeah");
						if(k++ > 19)
							break;
						JsonObject jsonObject = null;
						JsonObject jsonComm = null;
//						System.out.println("before first try");
						try
						{
//							System.out.println("first try");
							try
							{
//								System.out.println("second try like object");
								jsonObject = facebookClient.fetchObject(post.getId() + "/likes",JsonObject.class, Parameter.with("summary", true),Parameter.with("limit", 10));
								
							}
							catch(Exception e)
							{
								System.out.println("error finding likes object");
							}
							try
							{
//								System.out.println("2nd try comment object");
								jsonComm = facebookClient.fetchObject(post.getId()+"/comments",JsonObject.class,Parameter.with("summary", true),Parameter.with("limit", 10));
//								System.out.println(jsonComm);
							}
							catch(Exception e)
							{
								System.out.println("error finding comments json");
							}
							
//							System.out.println("making comment and likes ");
							long comment = 0, likes = 0 ;
							
							comment = jsonComm.getJsonObject("summary").getLong("total_count");
							likes = jsonObject.getJsonObject("summary").getLong("total_count");

							
//							System.out.println("made comment and likes objects long");							
//							long id = jsonObject.getJsonObject("data").getLong("id");
//							System.out.println("iski id a"+id);
//							System.out.println("\n"+k+".\nID == "+post.getId()+"\tMESSAGE == "+post.getMessage()+"\nLIKES == "+count+"\tCOMMENT == "+comment+"\t CREATION == "+post.getCreatedTime());
//							System.out.println("\nLIKERS "+jsonObject.getString("data"));
//							System.out.println("\nCOMMENTERS "+jsonComm.getString("data"));
							
							
							String type = "status";
							
							
							String li =jsonObject.getString("data");
							String listLikers = "";
							for(int index = 0; index < li.length(); index++)
							{
								if(li.charAt(index) != '[' && li.charAt(index) != ']' && li.charAt(index) != '{' && li.charAt(index) != '}' && li.charAt(index) != '\"'&& li.charAt(index) != ':'&& li.charAt(index) != 'i'&& li.charAt(index) != 'd')
								{
									listLikers += li.charAt(index);
								}	
							}
							listLikers = listLikers.replace(',', '+');
							String status = post.getMessage();
							if(status != null)
							{
								status = status.replace(',', ' ');
								status = status.replace('\n', ' ');
							}
							if(listLikers.equals(""))
								listLikers = null;
							String date ="";
							int year = post.getCreatedTime().getYear() + 1900;
							date += year+"-"+post.getCreatedTime().getMonth()+"-"+post.getCreatedTime().getDate()+" "+post.getCreatedTime().getHours()+":"+post.getCreatedTime().getMinutes()+":"+post.getCreatedTime().getSeconds();
							
							int postPrivacy = 2;
							if(post.getPrivacy() != null)
							{
								if(post.getPrivacy().getDescription().equals(""))
									postPrivacy = 2;
								else if(post.getPrivacy().getDescription().equals("Public"))
									postPrivacy = 2;
								else if(post.getPrivacy().getDescription().equals("Only Me"))
									postPrivacy = 0;
								else
									postPrivacy = 1;
							}
							long shares;
							try{
								shares = post.getSharesCount();
							}
							catch(Exception e){
								shares = 0;
							}
							List<MessageTag> tagCount;
							try{
								tagCount = post.getMessageTags();
							}
							catch(Exception e){
								tagCount = null;
							}
							type = post.getType();
//							System.out.println("post type : "+post.getType());
//							System.out.println("post message : "+post.getMessage()+"\t"+tagCount+"\t"+post.getWithTags()+"\tLikes : "+ likes+"\tShares : "+shares+" post privacy : "+postPrivacy);
//							System.out.println(date);
//							System.out.println(post.getLikes());
//							System.out.println(listLikers);
//							System.out.println(post.getPicture() +"\n###"+post.getObjectId());
//							System.out.println("im here");
//							System.out.println("\t\tMAIN"+post.getObjectId()+comma+post.getPicture()+comma+post.getAttachments()+comma);
//							System.out.println(type+comma+status+comma+likes+comma+date+comma+comment+comma+listLikers);
//							System.out.println(type+comma+status+comma+likes+comma+date+comma+comment+comma+shares+comma+postPrivacy+comma+listLikers);
							writer.write(type+comma+status+comma+likes+comma+date+comma+comment+comma+shares+comma+postPrivacy+comma+listLikers);
							writer.newLine();
							
						}
						catch(Exception e)
						{
							System.out.print(".");
//							System.out.println("Error occurred");
							k--;
						}
					}
					if(k > 19)
						break;
				}
				writer.close();
				
			}
			catch(Exception e)
			{
				System.out.println("exception occurred at begin first try");
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
