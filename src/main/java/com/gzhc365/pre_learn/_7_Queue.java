package com.gzhc365.pre_learn;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 15:49 2019/7/17 0017
 * @Modified by:
 */
public class _7_Queue {

    public static void main(String[] args) {
        MyQueue();
    }

    public static void MyQueue(){

        final BlockingQueue queue = new ArrayBlockingQueue(3);
        //创建三个线程 生产数据 put
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {//把需要重写的run方法省略不写了
                while (true) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println(Thread.currentThread().getName() + "--------------准备放数据!");
                        queue.put(1);
                        System.out.println(Thread.currentThread().getName() + "--------------已经放了数据，" +
                                "队列目前有" + queue.size() + "个数据");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //创建一个线程消费数据 take 数据
        new Thread(() -> {
            while (true) {
                try {
                    //将此处的睡眠时间分别改为100和1000，观察运行结果
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "准备取数据!");
                    queue.take();
                    System.out.println(Thread.currentThread().getName() + "已经取走数据，" +
                            "队列目前有" + queue.size() + "个数据");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Queue的常用方法
     *              抛出异常            返回特殊值
     * 插入           add(e)              offer(e)
     * 移除           remove()            poll()
     * 检查           element()           peek()
     *
     * 插入
     *      void add(Object e):     将指定元素插入到队列的尾部。
     *      boolean offer(Object e):    将指定的元素插入此队列的尾部。当使用容量有限的队列时，此方法通常比add(Object e)有效。
     * 移除
     *      Object remove():    获取队列头部的元素，并删除该元素。
     *      Object poll():  返回队列头部的元素，并删除该元素。如果队列为空，则返回null。
     * 检查
     *      object element():   获取队列头部的元素，但是不删除该元素。
     *      Object peek():  返回队列头部的元素，但是不删除该元素。如果队列为空，则返回null。
     *
     */

    /**
     * 双端队列:    ArrayDeque、LinkedList
     * 阻塞队列:    ArrayBlockingQueue、LinkedBlockingQueue 区别https://blog.csdn.net/chang_ge/article/details/79974121
     * 阻塞队列中常见操作：
     * add():把anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则报异常
     * offer():表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false.
     * put():把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断直到BlockingQueue里面有空间再继续.
     * poll():取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,取不到时返回null
     * take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到Blocking有新的对象被加入为止
     *
     */


}
