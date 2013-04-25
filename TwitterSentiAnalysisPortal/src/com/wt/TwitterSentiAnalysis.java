package com.wt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.util.List;

import com.wt.twitter.AnalysisResult;
import com.wt.twitter.GeoCode;
import com.wt.twitter.SentiAnalyzor;
import com.wt.twitter.SentiAnalyzorFactory;

/**
 * Servlet implementation class TwitterSentiAnalysis
 */
public class TwitterSentiAnalysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TwitterSentiAnalysis() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        
        String  searchType = request.getParameter("searchType");
        String  searchkeyword = request.getParameter("searchkey");
        String  language = request.getParameter("lang");
        String  startDate = request.getParameter("startdate");
        String  maxTweets = request.getParameter("selectMax");
        String  lat = request.getParameter("latitude");
        String  lng = request.getParameter("longtitude");
        String  radius = request.getParameter("radius");
        
        //out.println("searchkeyword = " + searchkeyword+ "\n");
        //out.println("searchType =" + searchType + "\n");
        //out.println("language = " + language+ "\n");
       // out.println("startDate = " + startDate+ "\n");
       // out.println("maxTweets = " + maxTweets+ "\n");
       // out.println("lat = " + lat+ "\n");
       // out.println("lng = " + lng+ "\n");
       // out.println("radius = " + radius+ "\n");
        
        int startDateType = 0;
        Calendar cal = Calendar.getInstance();
        String DATE_FORMAT_NOW = "yyyy-MM-dd";   	    
   	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);  
   	    
   	    SentiAnalyzor analyzor = SentiAnalyzorFactory.getInstance(); 
   	    List<AnalysisResult> results = null;
   	 
   	    if ( searchType.equalsIgnoreCase("typical"))
   	    {
   	    	results = analyzor.analyze(searchkeyword, 15, sdf.format(cal.getTime()),null,"en",null,null);
		}
   	    else if (searchType.equalsIgnoreCase("advanced"))
        {
        
	   	    if (startDate.length() ==0)
	   	    {
	   	    	startDateType =1;
	   	    }
	
	   	    startDateType = Integer.valueOf(startDate);	   	    
	   	 
	   	    cal.roll(Calendar.WEEK_OF_YEAR, -startDateType);	   	   
	   	    
	   	   // out.println("cal = " + sdf.format(cal.getTime()));
	   	    
	   	    if (language.length() == 0)
	   	    {
	   	    	language=null;
	   	    }
	   	    else if ( language.equalsIgnoreCase("english"))
	   	    {
	   	    	language = "en";
	   	    }
	   	    else if ( language.equalsIgnoreCase("france"))
		    {
		    	language = "fr";
		    }
	   	    
	        int maxtw = 0;   	    
	   	    if (maxTweets == null || maxTweets.length() ==0)
	   	    {
	   	    	maxtw = 15;
	   	    }
	   	    else
	   	    {
	   	    	maxtw = Integer.valueOf(maxTweets);
	   	    }
	   	    
	        if (lat == null || lat.length() ==0 || lng == null || lng.length() ==0)
	        {
	        	lat="43.652527";
	        	lng="-79.381961";
	        }
	        
	        if (radius == null || radius.length() ==0) 	radius="15000";
	        
	        GeoCode gc = new GeoCode(Double.valueOf(lat), Double.valueOf(lng), Double.valueOf(radius), "mi");
	   	    
	       // out.println("searchkeyword = " + searchkeyword+ "\n");
	       // out.println("language = " + language+ "\n");
	       // out.println("startDate = " + sdf.format(cal.getTime())+ "\n");
	       // out.println("maxTweets = " + maxtw+ "\n");
	       // out.println("lat = " + lat+ "\n");
	       // out.println("lng = " + lng+ "\n");
	       // out.println("radius = " + radius+ "\n");
	       // out.println("gc = " + gc);
	        results = analyzor.analyze(searchkeyword, 
	        							maxtw,	
	        							sdf.format(cal.getTime()),
	        							null,
	        							language,
	        							null,
	        							gc);
	    	   
        }
        
        
        out.println("<html>");
        out.println("<head> <style type=\"text/css\" media=\"all\"> " 
        		+   "body { padding:50px 100px; font:13px/150% Verdana, Tahoma, sans-serif; } " 
        		+   "table.hovertable {"
        		+ 	"font-family: verdana,arial,sans-serif;" 
        		+   "font-size:12px;"
        		+   "color:#333333;"
        		+   "border-width: 1px;"
        		+   "border-color: #999999;"
        		+   "border-collapse: collapse;}"
        		+	"table.hovertable th {"
        		+   "background-color:#c3dde0;"
        		+	"border-width: 1px;"
        		+	"padding: 8px;"
        		+   "border-style: solid;"
        		+   "border-color: #a9c6c9;}"
        		+	"table.hovertable tr { 	background-color:#d4e3e5; font-size:14px; }"
        		+   "table.hovertable td { 	border-width: 1px; padding: 8px; 	border-style: solid; 	border-color: #a9c6c9;}"
        		+   "button input {	width: auto;padding: 9px 15px;background: #617798;border: 0;font-size: 14px;color: #FFFFFF;"
        		+   "-moz-border-radius: 5px;-webkit-border-radius: 5px;}"
        		+   "</style> </head>");
       

        // System.out.println(results);
        
        out.println("<body><table> <tr> <h1 style=\"width:100%;height:10%;text-align:center;\">"
        				+ "Twitter Sentimental Analysis Result </h1> </tr>");
        
       // out.println("<tr> <h2 style=\"width:100%;height:10%;text-align:center;\">"
		//		+ "by \"" + searchkeyword + "\"</h2> </tr>");
        
        DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
   	  	sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        
   	  	out.println("<tr> <h4 style=\"width:100%;height:10%;text-align:center;\">"
				+ "generated time : " + sdf.format(cal.getTime()) + "</h4> </tr> </table>");
   	    
        out.println("<table  class=\"hovertable\">");
        out.println("<tr> 	<th>Sentiment Score </th><th>Tweats</th> </tr>");
        Float sum=(float) 0;
        int count = 0;
        
    
        
        for (int i = 0 ; i< results.size() ; i++)
        {
        	AnalysisResult  oneResult = results.get(i);
        	Float score =  results.get(i).getSentimentScore();  
        	if (score != 0.0)
        	{
        		sum = sum + score;
        		count++;
        	}
        	
        	out.println("<tr>");
        	out.println("<td> " + score + "</td>");
        	out.println("<td>" + oneResult.getText() + "</td>");        	
        	out.println("</tr>");
        }
               
        out.println("</table>");
        
        Float avg = sum/count;
        
        out.println("<br>");
        out.println("<table class=\"hovertable\"> <tr> <th>Average sentiment score : </th>");
        out.println("<th>" + avg + "</th> </tr></table>");
        out.println("<br>");
      
        out.println("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>"
				+ "<script type=\"text/javascript\">"
				+ "google.load('visualization', '1', {packages: ['corechart']});"
				+ "</script> <script type=\"text/javascript\">");

        out.println(" function drawVisualization() {");
        out.println("var data = google.visualization.arrayToDataTable([");
        out.println("['week','positive','negative'],");
        out.println("['First',0.502, 0],");
        out.println("['Second',0.21,0],");     
        out.println("['Third',0,-0.143],");
        out.println("['Fourth',0.112,0],"); 
        out.println(" ]);");
        out.println(" new google.visualization.ColumnChart(document.getElementById('visualization')).");
        out.println(" draw(data, {title:\"Twitter Sentiment Analysis\", width:600, height:400, "
        				+ "hAxis: {title: \"Weeks\"}});}");
        out.println(" google.setOnLoadCallback(drawVisualization);</script>");     
        out.println("<div id=\"visualization\" style=\"width: 600px; height: 400px;\"></div>");
        
        out.println("<br>");
        
        out.println("<table>");
        out.println("<button style=\"width:150;height:30\" type=\"button\" onclick=\"window.location.href='http://www.cardnova.ca:8080/TwitterSentiAnalysis'\">Search</button>");
        out.println("</table>");
        
        out.println(" </body></html>");
	}

}
