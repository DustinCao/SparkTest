package com.infobird.spark;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

public class SparkMysqlTest {

  public static void main(String[] args) throws Exception {
	  
    SparkConf sparkConf = new SparkConf().setAppName("SparkMysqlTest")
    		.setMaster("spark://slave01.infobird.com:7077");
    JavaSparkContext ctx = new JavaSparkContext(sparkConf);
    SQLContext sqlContext = new SQLContext(ctx);
    
    Map<String, String> options = new HashMap<String, String>();
    options.put("driver", "com.mysql.jdbc.Driver"); //驱动
    options.put("url", "jdbc:mysql://10.122.74.204:3306/test_kafka");//数据库地址
    options.put("user", "root");//用户名
    options.put("password", "123456");//密码
    options.put("dbtable", "accounts");//表名

    DataFrame jdbcDF = sqlContext.read().format("jdbc"). options(options).load();
   
    List<String> teenagerNames = jdbcDF.toJavaRDD().map(new Function<Row, String>() {
    	private static final long serialVersionUID = 1L;

		@Override
	      public String call(Row row) {
			System.out.println("row.size:" + row.size());
			System.out.println("row.length:" + row.length());
			String result = "";
			for(int i = 0; i < row.size(); i++ ) {
				result = result + ":" + row.get(i).toString();
			}
	        return result;
			// return "[Id: " + row.getInt(0) + "] [Name:" + row.getString(1) + "]";
	      }
	    }).collect();
    
	    for (String name: teenagerNames) {
	      System.out.println(name);
	    }
   
    ctx.stop();
  }
  
  
}
