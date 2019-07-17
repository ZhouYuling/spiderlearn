package com.gzhc365.pre_learn;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 18:13 2019/7/17 0017
 * @Modified by:
 */
public class _10_Mongo3 {

    private static final String MONGODB_DATABASE_NAME = "test";//连接Mongo数据库名称
    private static final String MONGODB_COLLECTION_NAME = "test";//连接Mongo的Collection名称
    private static MongoClient mongoClient = new MongoClient("localhost", 27017);// 连接到 mongodb 服务
    private static MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE_NAME);// 连接到数据库
    private static MongoCollection<Document> collection = mongoDatabase.getCollection(MONGODB_COLLECTION_NAME);// 选择集合MONGODB_COLLECTION_NAME


}
