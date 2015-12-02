
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class FBLike {

	public static HashMap<String,ArrayList<Float>> WordScore = new HashMap<String,ArrayList<Float>>();
	
	public static ArrayList<Float> AverageLikes = new ArrayList<Float>();
	
	public static ArrayList<Float> AverageComments = new ArrayList<Float>();
	
	public static ArrayList<Integer> Posttime = new ArrayList<Integer>();
	
	public static ArrayList<Long> sincelaststatus = new ArrayList<Long>();
	
	public static ArrayList<Float> PhotoAverageLikes = new ArrayList<Float>();
	
	public static ArrayList<Float> PhotoAverageComments = new ArrayList<Float>();
	
	public static ArrayList<Integer> PhotoPosttime = new ArrayList<Integer>();
	
	public static ArrayList<Long> Photosincelast = new ArrayList<Long>();
	
	public static HashMap<String,Integer> MediaScore = new HashMap<String,Integer>();
	
	public static ArrayList<ArrayList<String>> statusarray = new ArrayList<ArrayList<String>>();
	
	public static ArrayList<Integer> statuslikes = new ArrayList<Integer>(), medialikes = new ArrayList<Integer>();
	
	public static ArrayList<String> mediaposttype = new ArrayList<String>();
	
	public static ArrayList<Integer> hashtag = new ArrayList<Integer>();
	
	public static ArrayList<Integer> mediahashtag = new ArrayList<Integer>();
	
	public static ArrayList<ArrayList<String>> mediastatusarray = new ArrayList<ArrayList<String>>();
	
	public static ArrayList<Integer> statusshare = new ArrayList<Integer>();
	
	public static ArrayList<Integer> mediashare = new ArrayList<Integer>();
	
	public static ArrayList<Integer> privacy = new ArrayList<Integer>();
	
	public static ArrayList<Integer> mediaprivacy = new ArrayList<Integer>();
	
	public static HashMap<String, Integer> likerList = new HashMap<String,Integer>();
	
	public static HashMap<String, Integer> cMap = new HashMap<String,Integer>(); 			//calender map
	
	public static int maxLikesPost;
	public static String maxlikesPostID;
	
	
	public static double[] ar;
	static float likesbefore = 0;

	static float sumoflikes = 0;
	

    static float commentsbefore = 0, sumofcomments = 0;
    
    static int sharesbefore = 0;
    
    static String currentUser = null;
    static float photolikesbefore = 0, photosumoflikes = 0;
    static float photocommentsbefore = 0, photosumofcomments = 0;
    
    
    static String firstLine = null;
    static String[] firstlinebreak =null;
    
    static int mediasharesbefore = 0;
    static Date lastpostdate = null;
	
	public static int noofposts = 0;
	
	public static int statuscount = 0, photocount = 0;
	public static void data()
	{
		maxLikesPost = -1;
		
		
		int poi =0,testing = 0;
		String posttype,message;
		int posttime;
		int ncomments, nlikes = 0;
		int nshares = 0;
		int nprivacy = 0;
		
		//float avglikes, avgcomments;
		
		ArrayList<String> Stopwords = new ArrayList<String>();
		try {
			
			//for reading stopwords 
			
			FileReader fin = new FileReader("C:/itext/stopwordslist.txt");
	        BufferedReader bin = new BufferedReader(fin);
	        String stwrd;
	        while((stwrd = bin.readLine())!=null)
	        {
	        	Stopwords.add(stwrd);
	        }
	        bin.close();
	        
	       /*
	        * for reading Every Post Data 
	        */
			
		   FileReader fr = new FileReader("C:\\Users\\Himanshu\\git\\FBLoginRepository\\FBLogin\\userInfo\\"+currentUser+".csv");
           BufferedReader br = new BufferedReader(fr);
           
           String verify = br.readLine();
           firstLine = verify;
           firstlinebreak = firstLine.split(",");
           
           while( (verify = br.readLine()) != null )
           {
        	                
               String[] k = verify.split(",");
               posttype = k[0];												//type of post
               nlikes = Integer.parseInt(k[2]);								//number of likes on that post
               
               if(nlikes > maxLikesPost)
               {
            	   maxLikesPost = nlikes;
            	   maxlikesPostID = k[8];
               }
               
               
               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       	       Date parsedDate = dateFormat.parse(k[3]);					//time of that post
//       	   Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
       	       
       	       String cdate = "";
       	       int cyear = parsedDate.getYear() + 1900; 
       	       cdate += cyear + "-" + parsedDate.getMonth() + "-" +parsedDate.getDate();
       	       
       	       System.out.println(" %%%%%%%%%%%%% DATE IS "+cdate);
       	       
       	       
       	       if(!cMap.containsKey(cdate))
       	       {
       	    	   cMap.put(cdate, nlikes);
       	       }
       	       else
       	       {
       	    	   int current = cMap.get(cdate);
       	    	   current += nlikes;
       	    	   cMap.put(cdate, nlikes);
       	       }
       	       
       	       
       	       String ij = k[7].replaceAll("\\+", " ");
       	       String[] likep = ij.split(" ");
       	       for(int g=0; g < likep.length;g++)
       	       {
       	    	   if(!likerList.containsKey(likep[g]))
       	    	   {
       	    		   likerList.put(likep[g], 1);
       	    		   
       	    	   }
       	    	   else
       	    	   {
       	    		   int u = likerList.get(likep[g]);
       	    		   u++;
       	    		likerList.put(likep[g], u);
       	    		
       	    	   }
       	       }
       	       
       	       
       	       ncomments = Integer.parseInt(k[4]);
       	      
       	    nshares = Integer.parseInt(k[5]);
       	    
       	    nprivacy = Integer.parseInt(k[6]);
       	    /*
       	     *   Word Score calculation
       	     */
       	       
       	       if(k[0].equals("status"))
       	       {
       	    	statuscount++;   
       	    	
       	    	statuslikes.add(nlikes);
       	    	
       	    	
       	    	message = k[1];												// message of post
       	    	message=message.trim();
       	    	
       	    	if(message.contains("#"))
       	    	{
       	    		hashtag.add(1);
       	    		
       	    	}
       	    	else
       	    	{
       	    		hashtag.add(0);
       	    	}
       	    	
                //message = message.toLowerCase();
                
				
				String my=message;
				String newmsg=my.replaceAll("[.,)('!]"," ");
                
                String[] msgarray = newmsg.split(" ");
                ArrayList<String> ast = new ArrayList<String>();
                ArrayList<Float> af = new ArrayList<Float>();
                int s=0,wscore;
                for(int i=0;i<msgarray.length;i++)
                {
                	
                	if(!msgarray[i].isEmpty())
                	{
                		if(msgarray[i].contains("#"))
                		{
                			String si = msgarray[i].replace("#","");
                			String[] r = si.split("(?=\\p{Lu})");

                		    for(int p=0; p<r.length;p++){
                		    	String sop = r[p].toLowerCase();	
                		    	
                		    	if(!Stopwords.contains(sop))
                            		{
                            			ast.add(sop);
                            			s++;
                            		}
                		        
                		        
                		    }
                		}
                		else
                		{
                			String sop = msgarray[i].toLowerCase();
                			if(!Stopwords.contains(sop))
                        		{
                        			ast.add(sop);
                        			s++;
                        		}
                		}
                	}      	                	
                }
                statusarray.add(ast);
                if(s==0)
                	wscore=0;
                else wscore = nlikes/s;
                
                for(String w : ast)
                {
                	
                	if(!WordScore.containsKey(w))
                	{
                		af.add((float) 1.0);af.add((float) wscore);
                		WordScore.put(w, af);
                	}
                	else
                	{
                		af = WordScore.get(w);
                		float count = af.get(0),sc = af.get(1);
                		af.clear();
                		af.add(count+1); 
                		af.add(sc+ (float)wscore);
                		WordScore.put(w, af);
                 	}
                }
                
                
                /*
                 * 	Counting Average likes before that Post
                 */
                sumoflikes = sumoflikes + likesbefore;
                
                if(statuscount == 1)
                {
             	   AverageLikes.add((float) 0);
             	   
                }
                else
                {
             	   float b = sumoflikes/((float)(statuscount - 1));
             	   AverageLikes.add(b);
                }
                
                /*
                 * Counting Average comments before that Post
                 */
                sumofcomments  = sumofcomments + commentsbefore;
                
                if(statuscount == 1)
                {
             	   AverageComments.add((float) 0);
                }
                else
                {
             	   float b = sumofcomments/((float)(statuscount - 1));
             	  AverageComments.add(b);
                }
                
                
                
                
                /*
                 * Time of post 
                 */
                
                posttime = parsedDate.getMonth();
                Posttime.add(posttime);
                
                /*
                 * Time Since Last Status
                 */
                
                
                if(statuscount == 1)
                {
                	sincelaststatus.add((long) 0);
                	poi++;
                }
                else
                {
                	long diffInMillies = lastpostdate.getTime() - parsedDate.getTime();
                	long newtime = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
                	System.out.println( newtime +"  hours  1");
                	newtime = newtime;
                	sincelaststatus.add(newtime);
                	poi++;
                }
                
                /*
                 * shares
                 */
                if(statuscount == 1)
                {
                	statusshare.add((int) 0);
                }
                else
                {
                	statusshare.add(sharesbefore);
                }
                
                /*
                 * Privacy
                 */
                privacy.add(nprivacy);
                
                sharesbefore = nshares;
                commentsbefore = ncomments;
                likesbefore = nlikes;
                
       	       }
       	       
       	       
       	       
       	       /*
       	        * 		for Photos / Videos
       	        */
       	       
       	       else //if(k[0].equals("Photo"))
       	       {
       	    	   
       	    	   message = k[1];
       	    	   
       	    	   if(message.contains("#"))
       	    	   {
       	    		   mediahashtag.add(1);
       	    		
       	    	   }
       	    	   else
       	    	   {
       	    		   mediahashtag.add(0);
       	    	   }
       	    	 
       	    	   if(!message.equals("null"))
       	    	   {
       	    		String my=message;
       	    		String newmsg=my.replaceAll("[.,)('!:\"]"," ");
                    
                    String[] msgarray = newmsg.split(" ");
                    ArrayList<String> ast = new ArrayList<String>();
                    ArrayList<Float> af = new ArrayList<Float>();
                    int s=0,wscore;
                    for(int i=0;i<msgarray.length;i++)
                    {
                    	
                    	if(!msgarray[i].isEmpty())
                    	{
                    		if(msgarray[i].contains("#"))
                    		{
                    			String si = msgarray[i].replace("#","");
                    			String[] r = si.split("(?=\\p{Lu})");

                    		    for(int p=0; p<r.length;p++){
                    		    	String sop = r[p].toLowerCase();	
                    		    	
                    		    	if(!Stopwords.contains(sop))
                                		{
                                			ast.add(sop);
                                			s++;
                                		}
                    		        
                    		        
                    		    }
                    		}
                    		else
                    		{
                    			String sop = msgarray[i].toLowerCase();
                    			if(!Stopwords.contains(sop))
                            		{
                            			ast.add(sop);
                            			s++;
                            		}
                    		}
                    	}      	                	
                    }
                    mediastatusarray.add(ast);
                    if(s==0)
                    	wscore=0;
                    else wscore = nlikes/s;
                    
                    for(String w : ast)
                    {
                    	
                    	if(!WordScore.containsKey(w))
                    	{
                    		af.add((float) 1.0);af.add((float) wscore);
                    		WordScore.put(w, af);
                    	}
                    	else
                    	{
                    		af = WordScore.get(w);
                    		float count = af.get(0),sc = af.get(1);
                    		af.clear();
                    		af.add(count+1); 
                    		af.add(sc+ (float)wscore);
                    		WordScore.put(w, af);
                     	}
                    }
       	    	   }
       	    	   else
       	    	   {
       	    		mediastatusarray.add(null);
       	    	   }
       	    	   
       	    	   
       	    	   photocount++;
       	    	   
       	    	   medialikes.add(nlikes);
       	    	   
       	    	// could be changed   
       	    	   
       	    	   mediaposttype.add(k[0]);					
       	    	   
       	    	// could be changed
       	    	   
       	    	   /*
                    * Counting Average comments before this Photo/Video
                    */
                   photosumofcomments  = photosumofcomments + photocommentsbefore;
                   
                   if(photocount == 1)
                   {
                	   PhotoAverageComments.add((float) 0);
                	   testing++;
                   }
                   else
                   {
                	  float b = photosumofcomments/((float)(photocount - 1));
                	  PhotoAverageComments.add(b);
                	  testing++;
                   }
       	       
       	       
                   /*
                    * 	Counting Average likes before that Photo/Video
                    */
                   photosumoflikes = photosumoflikes + photolikesbefore;
                   
                   if(photocount == 1)
                   {
                	   PhotoAverageLikes.add((float) 0);
                   }
                   else
                   {
                	   float b = photosumoflikes/((float)(photocount - 1));
                	   PhotoAverageLikes.add(b);
                   }
                   
                   
                   /*
                    * Time of Photo/Video 
                    */
                   posttime = parsedDate.getMonth();
                   PhotoPosttime.add(posttime);
                   
                   
                   /*
                    * Time Since Last Post
                    */
                   
                   if(photocount == 1)
                   {
                	   Photosincelast.add((long) 0);
                   }
                   else
                   {
                   	long diffInMillies = lastpostdate.getTime() - parsedDate.getTime() ;
                   	long newtime = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
                   	System.out.println(newtime +"  hours  1");
                   	Photosincelast.add(newtime);
                   }
                   
                   /*
                    * Time Since Last Status
                    */
                   if(photocount == 1)
                   {
                   	mediashare.add((int) 0);
                   }
                   else
                   {
                   	mediashare.add(sharesbefore);
                   }
                   
                   /*
                    * Privacy
                    */
                   mediaprivacy.add(nprivacy);
                   
                   mediasharesbefore = nshares;
                   
                   photocommentsbefore = ncomments;
                   photolikesbefore = nlikes;
                   
                   
                   
                   
                   /*
                    * MediaScore Calculation
                    */
                   
                   if(k[0].equals("photo"))
                   {
                	   if(!MediaScore.containsKey("photo"))
                	   {
                		  MediaScore.put("photo", nlikes);
                		               		   
                	   }
                	   else
                	   {
                		   int ls = MediaScore.get("photo");
                		   ls = ls + nlikes;
                		   MediaScore.put("photo", ls);
                	   }
                   }
                   else if(k[0].equals("link"))
                   {
                	   if(!MediaScore.containsKey("link"))
                	   {
                		  MediaScore.put("link", nlikes);
                		               		   
                	   }
                	   else
                	   {
                		   int ls = MediaScore.get("link");
                		   ls = ls + nlikes;
                		   MediaScore.put("link", ls);
                	   }
                   }
                   else if(k[0].equals("video"))
                   {
                	   if(!MediaScore.containsKey("video"))
                	   {
                		  MediaScore.put("video", nlikes);
                		               		   
                	   }
                	   else
                	   {
                		   int ls = MediaScore.get("video");
                		   ls = ls + nlikes;
                		   MediaScore.put("video", ls);
                	   }
                   }
                   
                   
       	       }
       	       
       	    lastpostdate  = parsedDate;
       	   
       	    
       	    noofposts++;
          }
           
           
          br.close();
			
			
           
           System.out.println("testing   "+testing);
           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void statuslikeprediction()
	{
//		int op = Math.max(statuscount, 8);
		double[][] x = new double[statuscount][8];
		
		double[] y = new double[statuscount];
		

			
		int f=0;
		
		for(int i=statuscount - 1; i >= 0;i--)
		{
			// int co = 0;
			float w = 0;
			ArrayList<Float> a;
			for(int j=0; j < statusarray.get(i).size();j++)
			{
				a = WordScore.get(statusarray.get(i).get(j));
				w = w + (a.get(1)/a.get(0));
				
			}
			//x[f][0] = 1;
			x[f][0] = AverageLikes.get(i);
			x[f][1] = AverageComments.get(i);
			x[f][2] = Posttime.get(i);
			x[f][3] = sincelaststatus.get(i);
			x[f][4] = w;
			x[f][5] = hashtag.get(i);
			x[f][6] = statusshare.get(i);
			x[f][7] = privacy.get(i);
			
			f++;
		}
		
		f=0;
		for(int i=statuscount - 1; i >= 0;i--)
		{
			y[f] = statuslikes.get(i);
			f++;
		}
		
		MultipleLinearRegression regression = new MultipleLinearRegression(x, y, x, y);
		
	}
	

	
	
	
	
	
	public static void medialikeprediction()
	{
		double[][] x = new double[photocount][9];
		
		
		for(int i=0; i < photocount;i++)
		{
			// int co = 0;
			float w = (float) ((1 + (0.1*i)) * MediaScore.get(mediaposttype.get(i)));
			
			x[i][0] = PhotoAverageLikes.get(i);
			x[i][1] = PhotoAverageComments.get(i);
			x[i][2] = PhotoPosttime.get(i);
			x[i][3] = Photosincelast.get(i);
			x[i][4] = w;
			x[i][5] = mediahashtag.get(i);
			
			ArrayList<Float> a;
			for(int j=0; j < mediastatusarray.get(i).size();j++)
			{
				a = WordScore.get(mediastatusarray.get(i).get(j));
				w = w + (a.get(1)/a.get(0));
				
			}
			
			x[i][6] = w;
			x[i][7] = mediashare.get(i);
			x[1][8] = mediaprivacy.get(i);
			
		}
		
		double[] y = new double[photocount];
		for(int i=0; i < photocount;i++  )
		{
			y[i] = medialikes.get(i);
		}
		
		MultipleLinearRegression mediaregression = new MultipleLinearRegression(x, y, x, y);
		
	}

	
	
	
	
	

	public static void statuslikeprediction1()
	{
		if(statuscount != 0)
		{
			int s = statuscount;
	    	float k = (float) (s*0.8);
	    	float train = statuscount -1;
	    	 	
	    	
	    	float test = 1;
	    	
	    	
			System.out.println("test "+test);
	    	System.out.println("Tran       ++++"+train);
	    	
			double[][] x = new double[(int) train][9];
			
			int f = 0;
			for(int i=statuscount-1; i > test-1;i--)
			{
				// int co = 0;
				float w = 0;
				ArrayList<Float> a;
				for(int j=0; j < statusarray.get(i).size();j++)
				{
					a = WordScore.get(statusarray.get(i).get(j));
					w = w + (a.get(1)/a.get(0));
					
				}
				x[f][0] = 1;
				x[f][1] = AverageLikes.get(i);
				x[f][2] = AverageComments.get(i);
				x[f][3] = Posttime.get(i);
				x[f][4] = sincelaststatus.get(i);
				x[f][5] = w;
				x[f][6] = hashtag.get(i);
				x[f][7] = statusshare.get(i);
				x[f][8] = privacy.get(i);
				
				f++;
			}
			
			double[] y = new double[(int) train];
			f=0;
			for(int i=statuscount-1; i > test-1;i--)
			{
				y[f] = statuslikes.get(i);
				f++;
			}
			
			
			double[] ytest = new double[(int) test];
			double[][] xtest = new double[(int) test][9];
			
			for(int i=0; i < test;i++)
			{
				float w = 0;
				ArrayList<Float> a;
				for(int j=0; j < statusarray.get(i).size();j++)
				{
					a = WordScore.get(statusarray.get(i).get(j));
					w = w + (a.get(1)/a.get(0));
					
				}
				xtest[i][0] = 1;
				xtest[i][1] = AverageLikes.get(i);
				xtest[i][2] = AverageComments.get(i);
				xtest[i][3] = Posttime.get(i);
				xtest[i][4] = sincelaststatus.get(i);
				xtest[i][5] = w;
				xtest[i][6] = hashtag.get(i);
				xtest[i][7] = statusshare.get(i);
				xtest[i][8] = privacy.get(i);
			}
			
			for(int i=0; i < test;i++)
			{
				ytest[i] = statuslikes.get(i);
			}
			
			MultipleLinearRegression regression = new MultipleLinearRegression(x, y , xtest, ytest);
			
			ar = regression.ypredicted;
			
	}
	}
	
	
	
	
	
	
	public static void medialikeprediction1()
	{
	
		int s = photocount;
    	float k = (float) (s*0.8);
    	float train = (float) Math.ceil(k);
    	 	
    	
    	float test = (float) Math.floor((float) (s*0.2));
    	
    	
    	
		
		double[][] x = new double[(int) train][10];
		
		System.out.println(PhotoAverageComments.size());
		
		int f = 0;
		for(int i=photocount-1; i > test-1;i--)
		{
			// int co = 0;
			System.out.println("hsshds " + mediaposttype.get(i));
			float w = (float) ((1 + (0.1*i)) * MediaScore.get(mediaposttype.get(i)));
			x[f][0] = 1;
			x[f][1] = PhotoAverageLikes.get(i);
			x[f][2] = PhotoAverageComments.get(i);
			x[f][3] = PhotoPosttime.get(i);
			x[f][4] = Photosincelast.get(i);
			x[f][5] = w;
			x[f][6] = mediahashtag.get(i);
			
			ArrayList<Float> a;
			if(!(mediastatusarray.get(i)==null))
			for(int j=0; j < mediastatusarray.get(i).size();j++)
			{
				a = WordScore.get(mediastatusarray.get(i).get(j));
				w = w + (a.get(1)/a.get(0));
				
			}
			
			x[f][7] = w;
			x[f][8] = mediashare.get(i);
			x[f][9] = mediaprivacy.get(i);
			
			f++;
		}
		
		double[] y = new double[(int) train];
		f=0;
		for(int i=photocount-1; i > test-1;i--)
		{
			y[f] = medialikes.get(i);
			f++;
		}
		

		
//		System.out.println(" a + beta1 + beta2  (R^2 =)\n" +
//		
//mediaregression.beta(0) +"  "+ mediaregression.beta(1) +"  "+  mediaregression.beta(2) +"  "+  mediaregression.beta(3) +"  "+  mediaregression.beta(4) +"  "+  mediaregression.beta(5) +"  "+  mediaregression.beta(6) +"  "+ mediaregression.beta(7) + "  "+ mediaregression.beta(8)+"  "+ mediaregression.beta(9)+ mediaregression.R2());
		
             
		
		double[] ytest = new double[(int) test];
		double[][] xtest = new double[(int) test][10];
		
		for(int i=0; i < test;i++)
		{
			float w = (float) ((1 + (0.1*i)) * MediaScore.get(mediaposttype.get(i)));
			
			xtest[i][0] = 1;
			xtest[i][1] = PhotoAverageLikes.get(i);
			xtest[i][2] = PhotoAverageComments.get(i);
			xtest[i][3] = PhotoPosttime.get(i);
			xtest[i][4] = Photosincelast.get(i);
			xtest[i][5] = w;
			xtest[i][6] = mediahashtag.get(i);
			
			
			ArrayList<Float> a;
			if(!(mediastatusarray.get(i)==null))
			for(int j=0; j < mediastatusarray.get(i).size();j++)
			{
				a = WordScore.get(mediastatusarray.get(i).get(j));
				w = w + (a.get(1)/a.get(0));
				
			}
			
			x[i][7] = w;
			x[i][8] = mediashare.get(i);
			x[1][9] = mediaprivacy.get(i);
			
			
		}
		
		for(int i=0; i < test;i++)
		{
			ytest[i] = medialikes.get(i);
		}
		
		
		MultipleLinearRegression mediaregression = new MultipleLinearRegression(x, y, xtest, ytest);
		
		double[] ypredictedtest = new double[(int) test];
		for(int i=0; i < test;i++)
		{
//			ypredictedtest[i] = mediaregression.beta(0) + mediaregression.beta(1)*xtest[i][0] + mediaregression.beta(2)*xtest[i][1] + mediaregression.beta(3)*xtest[i][2] + mediaregression.beta(4)*xtest[i][3] + mediaregression.beta(5)*xtest[i][4] + mediaregression.beta(6)*xtest[i][5] + mediaregression.beta(7)*xtest[i][6] + mediaregression.beta(8)*xtest[i][7] + mediaregression.beta(9)*xtest[i][6];
		}
		
		//double cor = Correlation(ytest,ypredictedtest);
		
		//System.out.println("correlation   :"+cor);
		
//		System.out.println("shrinkage   :"+(mediaregression.R2() - cor));
	}
	
	public static double Correlation(double[] ytest, double[] ypredictedtest) {
	    //TODO: check here that arrays are not null, of the same length etc

	    double sx = 0.0;
	    double sy = 0.0;
	    double sxx = 0.0;
	    double syy = 0.0;
	    double sxy = 0.0;

	    int n = ytest.length;

	    for(int i = 0; i < n; ++i) {
	      double x = ytest[i];
	      double y = ypredictedtest[i];

	      sx += x;
	      sy += y;
	      sxx += x * x;
	      syy += y * y;
	      sxy += x * y;
	    }

	    // covariation
	    double cov = sxy / n - sx * sy / n / n;
	    // standard error of x
	    double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);
	    // standard error of y
	    double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);

	    // correlation is just a normalized covariation
	    return cov / sigmax / sigmay;
	  }
	

}
