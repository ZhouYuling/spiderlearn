package com.gzhc365.pre_learn;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description: MongoDB的增删改查 MongoDBUtil 0.1，多线程操作时，因为不是单例模式，会有一些问题。运行时间比较长，会导致电脑卡顿
 * @Date: Created in 下午 17:23 2019/7/17 0017
 * @Modified by:
 */
public class _10_Mongo {

    private static final String MONGODB_DATABASE_NAME = "test";//连接Mongo数据库名称
    private static final String MONGODB_COLLECTION_NAME = "test";//连接Mongo的Collection名称
    private static MongoCollection<Document> collection;

    static {
        MongoClient mongoClient = new MongoClient("localhost", 27017);// 连接到 mongodb 服务
        MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE_NAME);// 连接到数据库
        collection = mongoDatabase.getCollection(MONGODB_COLLECTION_NAME);// 选择集合MONGODB_COLLECTION_NAME
        System.out.println("StaticSingleton is create");
    }

    public static void insertDocument(){//插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式 2. 创建文档集合List<Document> 3.
         * 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用
         * mongoCollection.insertOne(Document)
         */
        Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100).append("by", "Fly");
        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        collection.insertMany(documents);
        System.out.println("文档插入成功");
    }

    @Test
    public void findDocument(){// 检索所有文档
        /**
         * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3. 通过游标遍历检索出的文档集合
         */
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
    }

    @Test
    public void updateDocument(){// 更新文档 将文档中likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
        // 检索查看结果
        FindIterable<Document> findIterable1 = collection.find();
        MongoCursor<Document> mongoCursor2 = findIterable1.iterator();
        while (mongoCursor2.hasNext()) {
            System.out.println(mongoCursor2.next());
        }
    }

    @Test
    public void deleteDocument(){// 删除符合条件的第一个文档

        collection.deleteOne(Filters.eq("likes", 200));
        collection.deleteMany(Filters.eq("likes", 200));// 删除所有符合条件的文档
        // 检索查看结果
        FindIterable<Document> findIterable3 = collection.find();
        MongoCursor<Document> mongoCursor3 = findIterable3.iterator();
        while (mongoCursor3.hasNext()) {
            System.out.println(mongoCursor3.next());
        }

    }

}
