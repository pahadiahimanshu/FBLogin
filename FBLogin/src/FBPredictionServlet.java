

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/FBPredictionServlet")
public class FBPredictionServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public FBPredictionServlet() 
	{
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
//		FBLike.currentUser = request.getParameter("state");
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	
		HttpSession session = request.getSession();
		Date createTime = new Date(session.getCreationTime());
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		Integer visitCount = new Integer(0);
		String visitCoundKey = new String("visitCount");
		String userIDKey = new String("userID");
		String userID = session.getId();
		
		String state = request.getParameter("state");
		
		System.out.println("user id = "+userID);
		System.out.println("state = "+state);
		String fbuserid = request.getParameter("fbuserid");
//		System.out.println("MAIN ;;;;"+fbuserid);
		FBLike.currentUser = fbuserid;
		FBLike.data();
		
		FBLike.statuslikeprediction1();
		
		
		FBLike.medialikeprediction1();
		
		System.out.println("\n\n\nSUPER MAIN PREDICTION"+MultipleLinearRegression.ypredicted);
		
		
		
		String nameUser = FBLike.firstlinebreak[2];
		Float totalLikes = FBLike.sumoflikes + FBLike.likesbefore + FBLike.photolikesbefore + FBLike.photosumoflikes;
		Float totalComments = FBLike.commentsbefore + FBLike.photocommentsbefore + FBLike.photosumofcomments + FBLike.sumofcomments;
		
		
		String mediaPrediction = "";
		String statusPrediction = "";
		
		if(FBLike.ar != null)
		{
			for(int index = 0; index < FBLike.ar.length; index ++)
			{
				double a = Math.ceil(FBLike.ar[index]);
				statusPrediction += a+" | ";
			}
		}
		else
		{
			statusPrediction = "Not sufficient to test model";
		}
		if(MultipleLinearRegression.ypredicted != null)
		{
			for(int index = 0; index < MultipleLinearRegression.ypredicted.length; index ++)
			{
				double a = Math.ceil(MultipleLinearRegression.ypredicted[index]);
				mediaPrediction += a +" | ";
			}
		}
		else
		{
			mediaPrediction = "Not sufficient to test model";
		}
		System.out.println("###########prediction is this " + mediaPrediction);

		System.out.println("###########prediction is this " + statusPrediction);
		
		
		int maxLikes = -1;
		String maxLikesID = "";
		
//		for(int som = 0; som < FBLike.likerList.size(); som++)
//		{
//			if(FBLike.likerList)
//		}
		
		for (Map.Entry<String, Integer> entry : FBLike.likerList.entrySet()) {
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    if(value > maxLikes)
		    {
		    	maxLikes = value;
		    	maxLikesID = key;
		    }
		}
		
		int maxLikesPost = FBLike.maxLikesPost;
		String maxLikesPostID = FBLike.maxlikesPostID;
		
		
		pw.flush();
		pw.println("\r\n" + 
				"<!doctype html>\r\n" + 
				"<html>\r\n" + 
				"  <head>\r\n" + 
				"    <title>Detective Facebook</title>\r\n" + 
				"    <link href='https://fonts.googleapis.com/css?family=Oswald:400,700' rel='stylesheet' type='text/css'>\r\n" + 
				"    <link rel=\"stylesheet\" href=\"main.css\" type=\"text/css\">\r\n" + 
				"\r\n" + 
				"    <style type=\"text/css\">\r\n" + 
				"      \r\n" + 
				"      html, body {\r\n" + 
				"        margin: 0;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      h1, h2, a {\r\n" + 
				"        font-family: 'Oswald', sans-serif;\r\n" + 
				"        text-transform: uppercase;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      p ,h3,h4 {\r\n" + 
				"        font-family: 'Open Sans', sans-serif; \r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* nav */\r\n" + 
				"      .nav\r\n" + 
				"      {\r\n" + 
				"        background: #000;\r\n" + 
				"        height:80px;\r\n" + 
				"        width:100%;\r\n" + 
				"        position: fixed;\r\n" + 
				"        z-index: 1;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .nav ul \r\n" + 
				"      {\r\n" + 
				"        list-style: none;\r\n" + 
				"        /*margin: 0 auto; */\r\n" + 
				"        padding:  0;\r\n" + 
				"        text-align: center;\r\n" + 
				"        height:80px; \r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .nav ul li\r\n" + 
				"      {\r\n" + 
				"        color : #fff;\r\n" + 
				"        display:inline-block;\r\n" + 
				"        height: 80px;\r\n" + 
				"        line-height: 80px;\r\n" + 
				"        list-style: none;\r\n" + 
				"        padding: 0 10px;\r\n" + 
				"        transition: background .5s;\r\n" + 
				"        transition-property: background;\r\n" + 
				"        transition-duration: 0.5s;\r\n" + 
				"        transition-timing-function: initial;\r\n" + 
				"        transition-delay: initial;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      li\r\n" + 
				"      {\r\n" + 
				"        text-align: -webkit-match-parent;\r\n" + 
				"      }\r\n" + 
				"      .containerB{\r\n" + 
				"        max-width: 100%;\r\n" + 
				"        color:white;\r\n" + 
				"        background-color: #3b5998;\r\n" + 
				"        text-align: center; \r\n" + 
				"        z-index: 4;\r\n" + 
				"      }\r\n" + 
				"      .containerB h1{\r\n" + 
				"      }\r\n" + 
				"      .container {\r\n" + 
				"        max-width: 940px;\r\n" + 
				"        margin: 0 auto;\r\n" + 
				"      }\r\n" + 
				"      .container p{\r\n" + 
				"        color: black;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* Profile picture*/\r\n" + 
				"      .team{\r\n" + 
				"        position:absolute;\r\n" + 
				"        width:100%;\r\n" + 
				"        /*height:200px;*/\r\n" + 
				"        z-index:0;\r\n" + 
				"        top:50%;\r\n" + 
				"        left:50%;\r\n" + 
				"        margin:-100px 0 0 -150px;\r\n" + 
				"     /*background:red;*/\r\n" + 
				"      }\r\n" + 
				"      .team h2{\r\n" + 
				"        text-align: center;\r\n" + 
				"      }\r\n" + 
				"      \r\n" + 
				"      .team .col {\r\n" + 
				"          /*float: left;*/\r\n" + 
				"          width: 30%;\r\n" + 
				"          padding: 10px;\r\n" + 
				"      }\r\n" + 
				"      .col .newimg{\r\n" + 
				"          border-radius: 50%;\r\n" + 
				"          height:300px;\r\n" + 
				"          width:300px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .bgcol{\r\n" + 
				"        width:100%;\r\n" + 
				"        height:100px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* Main */\r\n" + 
				"      .main {\r\n" + 
				"        text-align: center;\r\n" + 
				"        background: url(images/bg.jpg) no-repeat center center;\r\n" + 
				"        background-size: cover;\r\n" + 
				"        height: 600px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .main .container {\r\n" + 
				"        position: relative;\r\n" + 
				"        top: 100px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .main h1 {\r\n" + 
				"        color: #fff;\r\n" + 
				"        margin: 0;\r\n" + 
				"        font-size: 150px;\r\n" + 
				"      }\r\n" + 
				"      .main h2{\r\n" + 
				"        color:#fff;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .main p {\r\n" + 
				"        color: #fff;\r\n" + 
				"        margin: 0 0 0 0;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .main .btn {\r\n" + 
				"        background-color: #1c1c1c;\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"        padding: 8px 30px;\r\n" + 
				"        text-decoration: none;\r\n" + 
				"        display: inline-block;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* Supporting */\r\n" + 
				"      .supporting {\r\n" + 
				"        background-color: #1c1c1c;\r\n" + 
				"        text-align: center;\r\n" + 
				"        padding: 50px 0 80px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .supporting .col {\r\n" + 
				"        float: left;\r\n" + 
				"        width: 28%;\r\n" + 
				"        padding: 10px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .supporting h2 {\r\n" + 
				"        color: #3b5998;\r\n" + 
				"        font-size: 20px;\r\n" + 
				"        margin-bottom: 10px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .clearfix {\r\n" + 
				"        clear: both;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .supporting p {\r\n" + 
				"        color: #efefef;\r\n" + 
				"        margin-bottom: 20px;\r\n" + 
				"        line-height: 20px;\r\n" + 
				"        font-size: 12px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .supporting .btn {\r\n" + 
				"        background-color: #eee;\r\n" + 
				"        color: #1c1c1c;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"        padding: 8px 30px;\r\n" + 
				"        text-decoration: none;\r\n" + 
				"        display: inline-block;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* Feature */\r\n" + 
				"      .feature {\r\n" + 
				"        background: url(images/feature.jpg) no-repeat center center;\r\n" + 
				"        /*background-color: #3b5998;*/\r\n" + 
				"        background-size: cover;\r\n" + 
				"        height: 600px;\r\n" + 
				"        text-align: center;\r\n" + 
				"        margin-top: 0px;\r\n" + 
				"        z-index: 10;\r\n" + 
				"      }\r\n" + 
				"      .featureB {\r\n" + 
				"        /*background: url(images/feature.jpg) no-repeat center center;*/\r\n" + 
				"        background-color: #3b5998;\r\n" + 
				"        background-size: cover;\r\n" + 
				"        height: 600px;\r\n" + 
				"        text-align: center;\r\n" + 
				"      }\r\n" + 
				"      .featureB .container {\r\n" + 
				"        position: relative;\r\n" + 
				"        top: 200px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .featureB h2 {\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 40px;\r\n" + 
				"        margin:0;\r\n" + 
				"        letter-spacing: 5px;\r\n" + 
				"        padding:50px 0 0;\r\n" + 
				"\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .feature .container {\r\n" + 
				"        position: relative;\r\n" + 
				"        top: 200px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .feature h2 {\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 40px;\r\n" + 
				"        margin:0;\r\n" + 
				"        letter-spacing: 5px;\r\n" + 
				"        padding:50px 0 0;\r\n" + 
				"\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      /* Footer */\r\n" + 
				"      .footer {\r\n" + 
				"        background: url(images/1main.jpg) no-repeat center center;\r\n" + 
				"        background-size: cover;\r\n" + 
				"        height: 600px;\r\n" + 
				"        text-align: center;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footer .container {\r\n" + 
				"        position: relative;\r\n" + 
				"        top: 200px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footer h2 {\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 40px;\r\n" + 
				"        margin: 0 0 20px 0;\r\n" + 
				"        padding:50px 0 0;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footer p {\r\n" + 
				"        color: #fff;\r\n" + 
				"        margin: 0 0 20px 0;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footer .btn {\r\n" + 
				"        background-color: #1c1c1c;\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"        padding: 8px 30px;\r\n" + 
				"        text-decoration: none;\r\n" + 
				"        display: inline-block;\r\n" + 
				"      }\r\n" + 
				"      .footerB {\r\n" + 
				"        /*background: url(images/1main.jpg) no-repeat center center;*/\r\n" + 
				"        background-color: white;\r\n" + 
				"        background-size: cover;\r\n" + 
				"        height: 600px;\r\n" + 
				"        text-align: center;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footerB .container {\r\n" + 
				"        position: relative;\r\n" + 
				"        top: 200px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footerB h2 {\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 40px;\r\n" + 
				"        margin: 0 0 20px 0;\r\n" + 
				"        padding:50px 0 0;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footerB p {\r\n" + 
				"        color: #fff;\r\n" + 
				"        margin: 0 0 20px 0;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      .footerB .btn {\r\n" + 
				"        background-color: #1c1c1c;\r\n" + 
				"        color: #fff;\r\n" + 
				"        font-size: 18px;\r\n" + 
				"        padding: 8px 30px;\r\n" + 
				"        text-decoration: none;\r\n" + 
				"        display: inline-block;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      a.btn:hover {\r\n" + 
				"        background:#3b5998;\r\n" + 
				"        color:#000;\r\n" + 
				"      }\r\n" + 
				"      .topfan:hover{\r\n" + 
				"        color:#3b5998;\r\n" + 
				"      }\r\n" + 
				"\r\n" + 
				"      @media (min-width:600px) {\r\n" + 
				"        .main h1 {\r\n" + 
				"          font-size: 200px;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .supporting .col {\r\n" + 
				"          width: 30%;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .supporting h2 {\r\n" + 
				"          font-size: 40px;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .supporting p {\r\n" + 
				"          font-size: 14px;\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        .feature h2 {\r\n" + 
				"          font-size: 60px;\r\n" + 
				"        }\r\n" + 
				"      }\r\n" + 
				"    </style>\r\n" + 
				"    <link rel=\"icon\" \r\n" + 
				"      type=\"image/jpg\" \r\n" + 
				"      href=\"http://i.stack.imgur.com/kNPKJ.gif\"/>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <div class=\"nav\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <ul>\r\n" + 
				"          <h2 style=\"color:white\">Detective <div style=\"color:#3b5998;display:inline-block\">Facebook</div></h2>\r\n" + 
				"        </ul>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"   \r\n" + 
				"    <div class=\"team\">\r\n" + 
				"      <div class=\"col\">\r\n" + 
				"        <img class=\"newimg\" src=\"https://towson.givecorps.com/assets/user-icon-silhouette-08553e35ff46c59f99c559bf4fa47e34.png\">\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"    \r\n" + 
				"   \r\n" + 
				"\r\n" + 
				"    <div class=\"supporting\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <div class=\"col\">\r\n" + 
				"          <h2>"+ totalLikes+"</h2>\r\n" + 
				"          <p>Likes</p>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col\">\r\n" + 
				"          <h2>"+totalComments+"</h2>\r\n" + 
				"          <p>Comments</p>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"col\">\r\n" + 
				"          <h2>94%</h2>\r\n" + 
				"          <p>Profile Rate</p>\r\n" + 
				"        </div>\r\n" + 
				"        <div class=\"clearfix\"></div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"    <div class=\"featureB\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <h2>Hello, "+nameUser+"</h2>\r\n" + 
				"        <br><br><br>\r\n" + 
				"        <h3 style=\"color:white\">Likes Prediction is </h3>\r\n" + 
				"        \r\n" + 
				" <h3>Status Prediction</h3>"+
				"       <h3 style=\"letter-spacing:5px\">"+statusPrediction+"</h2>\r\n" +
				" <h3>Media Prediction</h3>"+ 
				"       <h3 style=\"letter-spacing:5px\">"+mediaPrediction+"</h2>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"    <div class=\"supporting\" >\r\n" + 
				"      <div class=\"container\" style=\"display:inline-block;margin-right:20px;\">\r\n" + 
				"        <h2>Your Top Fan is</h2>\r\n" + 
				"        <a href=\"https://www.facebook.com/"+maxLikesID+"\" style=\"text-decoration:none\"><h1 class=\"topFan\" style=\"color:white\">Click to find out</h1></a><h3 style=\"color:white\">"+maxLikes+" Likes</h3>\r\n" + 
				"        <!-- <a class=\"btn\" href=\"#\">Learn More</a> -->\r\n" + 
				"      </div>\r\n" + 
				"      <div class=\"container\" style=\"display:inline-block;margin-left:20px;\">\r\n" + 
				"        <h2>Your Top Post is</h2>\r\n" + 
				"        <a href=\"https://www.facebook.com/"+maxLikesPostID+"\" style=\"text-decoration:none\"><h1 class=\"topFan\" style=\"color:white\">Click to find out</h1></a><h3 style=\"color:white\">"+maxLikesPost+" Likes</h3>\r\n" + 
				"        <!-- <a class=\"btn\" href=\"#\">Learn More</a> -->\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"    <div class=\"footerB\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <h1>Calender Chart - Likes</h1>\r\n" + 
				"        <div id=\"calendar_basic\" style=\"width: 1000px; height: 350px;\"></div>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"    <!-- <div class=\"footerB\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <h1>Calender Chart - Likes</h1>\r\n" + 
				"        \r\n" + 
				"      </div>\r\n" + 
				"    </div> -->\r\n" + 
				"    <div class=\"supporting\">\r\n" + 
				"      <div class=\"container\">\r\n" + 
				"        <h2>Like it</h2>\r\n" + 
				"        <p>Please give full marks to keep the good work coming. </p>\r\n" + 
				"        <a class=\"btn\" href=\"goodGrades.html\">Learn More</a>\r\n" + 
				"      </div>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>\r\n" + 
				"    <script type=\"text/javascript\">\r\n" + 
				"      google.load(\"visualization\", \"1.1\", {packages:[\"calendar\"]});\r\n" + 
				"      google.setOnLoadCallback(drawChart);\r\n" + 
				"\r\n" + 
				"   function drawChart() {\r\n" + 
				"       var dataTable = new google.visualization.DataTable();\r\n" + 
				"       dataTable.addColumn({ type: 'date', id: 'Date' });\r\n" + 
				"       dataTable.addColumn({ type: 'number', id: 'Likes' });\r\n" + 
				"       dataTable.addRows([\r\n" );
				int year, month, date;
			for (Map.Entry<String, Integer> entry : FBLike.cMap.entrySet()) 
			{
			    String key = entry.getKey();
			    Integer value = entry.getValue();
			    String[] a = key.split("-");
			    year = Integer.parseInt(a[0]);
			    month = Integer.parseInt(a[1])+1;
			    date = Integer.parseInt(a[2]);
			    pw.print("[new Date("+year+","+month+","+date+"),"+value+"],\r\n");
			}
				
//		pw.print("          [ new Date(2012, 3, 13), 2 ],\r\n" + 
//				"          [ new Date(2012, 3, 14), 4 ],\r\n" + 
//				"          [ new Date(2012, 3, 15), 15 ],\r\n" + 
//				"          [ new Date(2012, 3, 16), 20 ],\r\n" + 
//				"          [ new Date(2012, 3, 17), 12 ],\r\n" + 
//				"          // Many rows omitted for brevity.\r\n" + 
//				"          // [ new Date(2013, 9, 4), 38177 ],\r\n" + 
//				"          // [ new Date(2013, 9, 5), 38705 ],\r\n" + 
//				"          // [ new Date(2013, 9, 12), 38210 ],\r\n" + 
//				"          // [ new Date(2013, 9, 13), 38029 ],\r\n" + 
//				"          // [ new Date(2013, 9, 19), 38823 ],\r\n" + 
//				"          // [ new Date(2013, 9, 23), 38345 ],\r\n" + 
//				"          // [ new Date(2013, 9, 24), 38436 ],\r\n" + 
//				"          // [ new Date(2013, 9, 30), 38447 ]\r\n" + 
				pw.print("        ]);\r\n" + 
				"\r\n" + 
				"       var chart = new google.visualization.Calendar(document.getElementById('calendar_basic'));\r\n" + 
				"\r\n" + 
				"       var options = {\r\n" + 
				"         title: \"Likes\",\r\n" + 
				"         height: 350,\r\n" + 
				"       };\r\n" + 
				"\r\n" + 
				"       chart.draw(dataTable, options);\r\n" + 
				"   }\r\n" + 
				"    </script>\r\n" + 
				"  </body>\r\n" + 
				"</html>");
		pw.flush();
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
