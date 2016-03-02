package com.infobird.sparkUDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

import com.infobird.entity.InfoEntity;

import scala.Tuple2;

public class HbaseTest {

	// private static String master = "spark://slave01.infobird.com:7077";

	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf().setAppName("HbaseTest")
				.setMaster("spark://slave01.infobird.com:7077");
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		SQLContext sqlContext = new SQLContext(sc);
		try {

			testUDFHbaseFlaTMap(sc, sqlContext);
			//testSparkCache(sc);
			//testSparkHbase(sc);
			
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	public static void testUDFHbaseFlaTMap(JavaSparkContext sc, SQLContext sqlContext)throws IOException {
		Configuration conf = HBaseConfiguration.create();

		Scan scan = new Scan();

		scan.addFamily(Bytes.toBytes("baseinfo"));

		scan.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("city"));
		
		String tableName = "call_info_history_10000";

		conf.set(TableInputFormat.INPUT_TABLE, tableName);

		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);

		String ScanToString = Base64.encodeBytes(proto.toByteArray());

		conf.set(TableInputFormat.SCAN, ScanToString);

		JavaPairRDD<ImmutableBytesWritable, Result> myRDD = sc
				.newAPIHadoopRDD(conf, TableInputFormat.class,
						ImmutableBytesWritable.class, Result.class);

		JavaRDD<InfoEntity> personRDD = myRDD.flatMap(new FlatMapFunction<Tuple2<ImmutableBytesWritable, Result>, InfoEntity>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Iterable<InfoEntity> call(Tuple2<ImmutableBytesWritable, Result> v1)
					throws Exception {
				
  					System.out.println("=============begion print===================");
  					
  					System.out.println("age:" + v1._2.getValue(Bytes.toBytes("baseinfo"), Bytes.toBytes("city")));
  					
  					List<InfoEntity> entitys = new ArrayList<InfoEntity>();
  					List<Cell> keyvalus = v1._2.listCells();
  					for (Cell cell : keyvalus) {
  						InfoEntity entity = new InfoEntity();
  						String key = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
  						String city = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
  						
  						System.out.println("key:"  + key);
  						System.out.println("family:"  + new String(cell.getFamilyArray(),
  								cell.getFamilyOffset(), cell.getFamilyLength()));
  						System.out.println("qualifier:"  + new String(cell.getQualifierArray(),
  								cell.getQualifierOffset(), cell.getQualifierLength()));
  						System.out.println("value:"  + city);
  						entity.setKey(key);
  						entity.setCity(city);
  						entitys.add(entity);
  					}
  								
				return entitys;
		   }
        });
		
		
		 DataFrame schemaPeople = sqlContext.createDataFrame(personRDD, InfoEntity.class);
		  
		  //编写自定义函数
		  sqlContext.udf().register("strlength", new UDF1<String, Integer>() {
	  
			private static final long serialVersionUID = 1L;

			@Override
	          public Integer call(String str) throws Exception {
	              return str.length();
	          }
	      }, DataTypes.IntegerType);

		  schemaPeople.registerTempTable("people");
		  
		  //数据查询
		  DataFrame teenagers = sqlContext.sql("SELECT strlength(key),key,city FROM people");
		  
		  teenagers.show();
		  
		  Row[] result = teenagers.collect();
		  for (Row row : result) {
			  System.out.println("========" + row.getInt(0) + "," + row.getString(1) + "," + row.getString(2) + "=========");
		  }
	}
	
	public static void testUDFHbase(JavaSparkContext sc, SQLContext sqlContext)throws IOException {
		Configuration conf = HBaseConfiguration.create();

		Scan scan = new Scan();

		scan.addFamily(Bytes.toBytes("baseinfo"));

		scan.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("city"));
		
		String tableName = "call_info_history_10000";

		conf.set(TableInputFormat.INPUT_TABLE, tableName);

		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);

		String ScanToString = Base64.encodeBytes(proto.toByteArray());

		conf.set(TableInputFormat.SCAN, ScanToString);

		JavaPairRDD<ImmutableBytesWritable, Result> myRDD = sc
				.newAPIHadoopRDD(conf, TableInputFormat.class,
						ImmutableBytesWritable.class, Result.class);

		JavaRDD<InfoEntity> personRDD = myRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, InfoEntity>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public InfoEntity call(Tuple2<ImmutableBytesWritable, Result> v1)
					throws Exception {
				
  					System.out.println("=============begion print===================");
  					
  					System.out.println("age:" + v1._2.getValue(Bytes.toBytes("baseinfo"), Bytes.toBytes("city")));
  					
  					InfoEntity entity = new InfoEntity();
  					List<Cell> keyvalus = v1._2.listCells();
  					for (Cell cell : keyvalus) {
  						String key = new String(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
  						String city = new String(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
  						
  						System.out.println("key:"  + key);
  						System.out.println("family:"  + new String(cell.getFamilyArray(),
  								cell.getFamilyOffset(), cell.getFamilyLength()));
  						System.out.println("qualifier:"  + new String(cell.getQualifierArray(),
  								cell.getQualifierOffset(), cell.getQualifierLength()));
  						System.out.println("value:"  + city);
  						entity.setKey(key);
  						entity.setCity(city);
  					}
  								
				return entity;
		   }
        });
		
		
		 DataFrame schemaPeople = sqlContext.createDataFrame(personRDD, InfoEntity.class);
		  
		  //编写自定义函数
		  sqlContext.udf().register("strlength", new UDF1<String, Integer>() {
	  
			private static final long serialVersionUID = 1L;

			@Override
	          public Integer call(String str) throws Exception {
	              return str.length();
	          }
	      }, DataTypes.IntegerType);

		  schemaPeople.registerTempTable("people");
		  
		  //数据查询
		  DataFrame teenagers = sqlContext.sql("SELECT strlength(key),key,city FROM people");
		  
		  teenagers.show();
		  
		  Row[] result = teenagers.collect();
		  for (Row row : result) {
			  System.out.println("========" + row.getInt(0) + "," + row.getString(1) + "," + row.getString(2) + "=========");
		  }
	}
	
	public static void testSparkCache(JavaSparkContext sc) throws IOException {
		Configuration conf = HBaseConfiguration.create();

		Scan scan = new Scan();

		scan.addFamily(Bytes.toBytes("baseinfo"));

		scan.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("city"));
		
		String tableName = "call_info_history";

		conf.set(TableInputFormat.INPUT_TABLE, tableName);

		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);

		String ScanToString = Base64.encodeBytes(proto.toByteArray());

		conf.set(TableInputFormat.SCAN, ScanToString);

		JavaPairRDD<ImmutableBytesWritable, Result> myRDD = sc
				.newAPIHadoopRDD(conf, TableInputFormat.class,
						ImmutableBytesWritable.class, Result.class);


		//JavaPairRDD<ImmutableBytesWritable, Result> myRDD1 = myRDD.cache();
		Integer r = myRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, Integer>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Tuple2<ImmutableBytesWritable, Result> v1)
					throws Exception {
				
  					System.out.println("=============begion print===================");
  					
  					System.out.println("age:" + v1._2.getValue(Bytes.toBytes("baseinfo"), Bytes.toBytes("city")));
  					
  					List<Cell> keyvalus = v1._2.listCells();
  					for (Cell cell : keyvalus) {
  						System.out.println("key:"  + new String(cell.getRowArray(),
  								cell.getRowOffset(), cell.getRowLength()));
  						System.out.println("family:"  + new String(cell.getFamilyArray(),
  								cell.getFamilyOffset(), cell.getFamilyLength()));
  						System.out.println("qualifier:"  + new String(cell.getQualifierArray(),
  								cell.getQualifierOffset(), cell.getQualifierLength()));
  						System.out.println("value:"  + new String(cell.getValueArray(),
  								cell.getValueOffset(), cell.getValueLength()));
  					}
  								
				return 1;
		   }
        }).reduce(new Function2<Integer, Integer, Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
            public Integer call(Integer integer, Integer integer2) {
              return integer + integer2;
            }
          });
		System.out.println("======resulut : ======:" +r);
		System.out.println("======myRDD.count() : ======:" + myRDD.count());
	}
	
	
	public static void testSparkHbase(JavaSparkContext sc) throws IOException {
		
		Configuration conf = HBaseConfiguration.create();

		Scan scan = new Scan();

		scan.addFamily(Bytes.toBytes("baseinfo"));

		scan.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("city"));
		
		String tableName = "call_info_history_10000";

		conf.set(TableInputFormat.INPUT_TABLE, tableName);

		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);

		String ScanToString = Base64.encodeBytes(proto.toByteArray());

		conf.set(TableInputFormat.SCAN, ScanToString);

		JavaPairRDD<ImmutableBytesWritable, Result> myRDD = sc
				.newAPIHadoopRDD(conf, TableInputFormat.class,
						ImmutableBytesWritable.class, Result.class);
		

		Integer r = myRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, Integer>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(Tuple2<ImmutableBytesWritable, Result> v1)
					throws Exception {
				
  					System.out.println("=============begion print===================");
  					
  					System.out.println("age:" + v1._2.getValue(Bytes.toBytes("baseinfo"), Bytes.toBytes("city")));
  					
  					List<Cell> keyvalus = v1._2.listCells();
  					for (Cell cell : keyvalus) {
  						System.out.println("key:"  + new String(cell.getRowArray(),
  								cell.getRowOffset(), cell.getRowLength()));
  						System.out.println("family:"  + new String(cell.getFamilyArray(),
  								cell.getFamilyOffset(), cell.getFamilyLength()));
  						System.out.println("qualifier:"  + new String(cell.getQualifierArray(),
  								cell.getQualifierOffset(), cell.getQualifierLength()));
  						System.out.println("value:"  + new String(cell.getValueArray(),
  								cell.getValueOffset(), cell.getValueLength()));
  					}
  								
				return 1;
		   }
        }).reduce(new Function2<Integer, Integer, Integer>() {

			private static final long serialVersionUID = 1L;

			@Override
            public Integer call(Integer integer, Integer integer2) {
              return integer + integer2;
            }
          });
		System.out.println("======resulut : ======:" +r);
		System.out.println("======myRDD.count() : ======:" + myRDD.count());
	}
}
