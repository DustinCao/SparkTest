package com.test;

import java.util.List;
import java.util.regex.Pattern;
import scala.Tuple2;


import com.google.common.collect.Lists;

public class Test {

	 public static final List<String> exampleApacheLogs = Lists.newArrayList(
			    "10.10.10.10 - \"FRED\" [18/Jan/2013:17:56:07 +1100] \"GET http://images.com/2013/Generic.jpg " +
			      "HTTP/1.1\" 304 315 \"http://referall.com/\" \"Mozilla/4.0 (compatible; MSIE 7.0; " +
			      "Windows NT 5.1; GTB7.4; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; " +
			      ".NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR " +
			      "3.5.30729; Release=ARP)\" \"UD-1\" - \"image/jpeg\" \"whatever\" 0.350 \"-\" - \"\" 265 923 934 \"\" " +
			      "62.24.11.25 images.com 1358492167 - Whatup",
			    "10.10.10.10 - \"FRED\" [18/Jan/2013:18:02:37 +1100] \"GET http://images.com/2013/Generic.jpg " +
			      "HTTP/1.1\" 304 306 \"http:/referall.com\" \"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; " +
			      "GTB7.4; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.04506.648; .NET CLR " +
			      "3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR  " +
			      "3.5.30729; Release=ARP)\" \"UD-1\" - \"image/jpeg\" \"whatever\" 0.352 \"-\" - \"\" 256 977 988 \"\" " +
			      "0 73.23.2.15 images.com 1358492557 - Whatup");

	 public static final Pattern apacheLogRegex = Pattern.compile(
			    "^([\\d.]+) (\\S+) (\\S+) \\[([\\w\\d:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) ([\\d\\-]+) \"([^\"]+)\" \"([^\"]+)\".*");

			  
	public static void main(String[] args) {
		
		Tuple2<String, Integer> t = new Tuple2<String, Integer>("abc", 1);
		System.out.println(t._1 + "==" + t._2);
		System.out.println(t._1() + "==" + t._2());
		
		/*for (String string : exampleApacheLogs) {
			Matcher m = apacheLogRegex.matcher(string);
		    if (m.find()) {
		      String ip = m.group(1);
		      String user = m.group(3);
		      String query = m.group(5);
		      System.out.println(m.group(1) + "==" + m.group(2) + "=="
		    		  + m.group(3) + "==m.group(4):" + m.group(4) 
		    		  + "==m.group(5):" + m.group(5) + "==m.group(6):" + m.group(6) 
		    		  + "==m.group(7):" + m.group(7) + "==m.group(8):" + m.group(8));
		      
		      System.out.println("ip:" + ip + "==user:" + user + "==query:" + query);
		    }
			
		}*/
		 
		    //return new Tuple3<String, String, String>(null, null, null);
	}
}
