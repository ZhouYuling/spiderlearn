package com.gzhc365.pre_learn;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 17:49 2019/7/17 0017
 * @Modified by: 单例模式
 */
public class _10_Mongo2 {

    private static final String MONGODB_DATABASE_NAME = "test";//连接Mongo数据库名称
    private static final String MONGODB_COLLECTION_NAME = "test";//连接Mongo的Collection名称
    private static MongoCollection<Document> collection;

    private _10_Mongo2() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);// 连接到 mongodb 服务
        MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE_NAME);// 连接到数据库
        collection = mongoDatabase.getCollection(MONGODB_COLLECTION_NAME);// 选择集合MONGODB_COLLECTION_NAME
        System.out.println("StaticSingleton is create");
    }

    private static class SingletonHolder {
        private static _10_Mongo2 instance = new _10_Mongo2();
    }

    public static _10_Mongo2 getInstanstance() {
        return _10_Mongo2.SingletonHolder.instance;
    }

    @Test
    public void insertDocument() {//插入文档

        Document document = new Document("title", "MongoDB").append("description", "database").append("likes", (int)(Math.random() * 1000)).append("by", "Fly");
        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        collection.insertMany(documents);
        System.out.println("文档插入成功");

    }

}
