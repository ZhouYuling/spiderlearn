package com.gzhc365.pre_learn;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: ZhouYuling
 * @Description: 多线程
 * @Date: Created in 下午 14:13 2019/7/17 0017
 * @Modified by: https://blog.csdn.net/itcats_cn/article/details/81149232
 */
public class _5_MyMultiThread extends Thread {

    @Test
    public void extendThread(){//继承Thread类，并复写run方法，创建该类对象，调用start方法开启线程。
        Demo1 demo1 = new Demo1();
        Demo1 demo2 = new Demo1();//创建两个线程
        demo1.start();
        demo2.start();
    }

    @Test
    public void implementRunnable(){//实现Runnable接口，复写run方法，创建Thread类对象，将Runnable子类对象传递给Thread类对象。调用start方法开启线程。
        Thread thread1 = new Thread(new Demo2(),"Thread-1");
        Thread thread2 = new Thread(new Demo2(),"Thread-2");
        thread1.start();
        thread2.start();
    }

    @Test
    public void callableAndFuture() throws ExecutionException, InterruptedException {

        Demo3 callable = new Demo3();
//        Callable<String> callable1 = new Callable<String>() { //泛型中为返回结果的类型
//            public String call() throws Exception {
//                return "返回结果为1";
//            }
//        };

        FutureTask<String> future = new FutureTask<String>(callable);
        Thread t = new Thread(future);
        t.start();
        System.out.println("提前完成任务...");
        //获取任务执行后返回的结果
        String result = future.get();
        System.out.println("线程执行结果为"+result);

    }

    @Test
    public void anonymity(){

        //方式1：相当于继承了Thread类，作为子类重写run()实现
        new Thread() {
            public void run() {
                System.out.println("匿名内部类创建线程方式1...");
            };
        }.start();

        //方式2:实现Runnable,Runnable作为匿名内部类
        new Thread(new Runnable() {
            public void run() {
                System.out.println("匿名内部类创建线程方式2...");
            }
        } ).start();

    }

}

class Demo1 extends Thread{

    //重写的是父类Thread的run()
    public void run(){
        System.out.println(getName()+"is running...");//业务代码
        //或者你可以这样
//        while (true){//两个线程一直执行一下代码
//            System.out.println("我一直在执行");//业务代码
//            try {
//                Thread.sleep(1000);//休息一段时间，别把别人的服务器搞崩了
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

}

class Demo2 implements Runnable{

    //重写的是Runnable接口的run()
    public void run() {
        System.out.println("implements Runnable is running " + Thread.currentThread().getName());//业务代码
    }

}

class Demo3 implements Callable<String> {

    public String call() throws Exception {
        System.out.println("正在执行新建任务");
        Thread.sleep(2000);
        return "新建线程睡了2s后返回执行结果";
    }
}
