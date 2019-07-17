package com.gzhc365.utils;

import com.gzhc365.pre_learn._10_Mongo2;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;

public class Mongo {

	private static final String MONGODB_DATABASE_NAME = "test";//连接Mongo数据库名称
	private static final String MONGODB_COLLECTION_NAME = "test";//连接Mongo的Collection名称
	private static MongoCollection<Document> collection;

	private Mongo(){
		MongoClient mongoClient = new MongoClient("localhost", 27017);// 连接到 mongodb 服务
		MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE_NAME);// 连接到数据库
		collection = mongoDatabase.getCollection(MONGODB_COLLECTION_NAME);// 选择集合MONGODB_COLLECTION_NAME
		System.out.println("StaticSingleton is create");
	}

	private static class SingletonHolder {
		private static Mongo instance = new Mongo();
	}

	public static Mongo getInstanstance(){
		return Mongo.SingletonHolder.instance;
	}

	/**
	 * 将Medlive上爬取的数据存入到mongoDB中间去
	 * @param map 药品详情页中的K-V s
	 */
	public void insertMedicineIntoMongo(Map<String,String> map){//LinkedHashMap继承自HashMap

		Document document = new Document();
		for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
			String key = stringStringEntry.getKey();
			String value = stringStringEntry.getValue();
//			System.out.println(key + "   " + value);
			document.append(key,value);
		}
		collection.insertOne(document);

	}

}