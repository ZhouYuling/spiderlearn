package com.gzhc365.pre_learn;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @Author: ZhouYuling
 * @Description: 为了避免系统频繁地创建和销毁线程，我们可以让创建地线程复用
 * @Date: Created in 下午 14:41 2019/7/17 0017
 * @Modified by:
 */
public class _6_ThreadPool {

    @Test
    public void testFixedThreadPool() {
        //底层是个无界队列
        //newFixedThreadPool()方法：该方法返回一个固定线程池数量地线程池。该线程池中的线程数量始终不变。当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。
        // 若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理任务队列中的任务
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new MyThread());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());
        executorService.submit(new MyThread());
        executorService.submit(new MyRunnable());
        Future submit = executorService.submit(new Thread(new OtherRunnable("Task2")));

        try {
            if(submit.get()==null){//如果Future's get返回null，任务完成
                System.out.println("任务完成");
            }
        } catch (InterruptedException e) {
            //TODO:
        } catch (ExecutionException e) {
            //否则我们可以看看任务失败的原因是什么
            System.out.println(e.getCause().getMessage());
        }

    }

    @Test
    public void testSingleThreadPool() {
        //底层是个无界队列。
        //newSingleThreadExecutor()方法：该方法返回一个只有一个线程的线程池。
        // 若多余一个任务被提交到该线程池，任务会被保存在一个任务队列中，待线程池空闲，按先入先出的顺序执行队列中的任务
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new MyThread());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyThread());

    }

    @Test
    public void testCacheThreadPool() {
        //创建非固定数量，可缓存的线程池。当提交的任务数量起起伏伏时，会自动创建或者减少执行线程的数量。
        //当然，重用线程是线程池的基本特征。
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new MyThread());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyRunnable());
        executorService.execute(new MyThread());
    }

    @Test
    public void testScheduledThreadPool(){//使用main调用这个方法试试周期性执行
        //创建一个定时执行线程池
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);
        //1、配置任务的执行周期
        //scheduleAtFixedRate 初始化延迟0ms开始执行，每隔1000ms重新执行一次任务。
        executorService.scheduleAtFixedRate(new MyRunnable(),0,1000, TimeUnit.MILLISECONDS);
        //scheduleWithFixedDelay 初始化时延时0ms开始执行，本次执行结束后延迟1000ms开始下次执行。
        executorService.scheduleWithFixedDelay(new MyThread(),0,1000,TimeUnit.MILLISECONDS);
        //schedule 延迟1000ms开始下次执行
        executorService.schedule(new MyThread(), 1000, TimeUnit.MILLISECONDS);
        System.out.println("Hello everybody");
    }

    @Test
    public void testSingleCacheThreadPool(){
        //创建一个单个线程执行的定时器
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //scheduleAtFixedRate 固定周期执行完毕
        executorService.scheduleAtFixedRate(new MyRunnable(),0,1000,TimeUnit.MILLISECONDS);
        //scheduleWithFixedDelay 上一次执行完毕之后下一次开始执行
        executorService.scheduleWithFixedDelay(new MyRunnable(),0,1000,TimeUnit.MILLISECONDS);
    }

    public void testTimer(){
        new Timer().schedule(new MyTimerTask(), 2000, 5000);
    }

    @Test
    public void forkAndJoin(){//计算给定函数 y=1/x 在定义域 [1,100]上围城与X轴围成的面积，计算步长0.01
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 100);
        Future<Double> resultFuture = forkJoinPool.submit(task);
        try {
            System.out.println(resultFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMyThreadPool(){
        //自定义连接池稍微麻烦些，不过通过创建的ThreadPoolExecutor线程池对象，可以获取到当前线程池的尺寸、正在执行任务的线程数、工作队列等等。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,100,10,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100));
        threadPoolExecutor.execute(new MyThread());
        threadPoolExecutor.execute(new MyRunnable());
        threadPoolExecutor.execute(new MyRunnable());
    }

}

class MyThread extends Thread{

    //重写的是父类Thread的run()
    public void run(){
        System.out.println(getName()+" is running...");//do something
    }

}

class MyRunnable implements Runnable{

    //重写的是Runnable接口的run()
    public void run() {
        System.out.println("implements Runnable is running " + Thread.currentThread().getName());//do something
    }

}

class OtherRunnable implements Runnable{

    private String taskName;

    public OtherRunnable(){
        super();
    }

    public OtherRunnable(final String taskName) {
        this.taskName = taskName;
    }

    public void run() {
        System.out.println("Inside "+taskName);
        throw new RuntimeException("RuntimeException from inside " + taskName);
        //https://www.cnblogs.com/liuchuanfeng/p/6956014.html
    }

}

class CountTask extends RecursiveTask<Double>{

    private static final long serialVersionUID = 1L;
    private static final double threshold = 0.01;
    private double start;
    private double end;
    public CountTask(double start, double end){
        this.start = start;
        this.end = end;
    }

    protected Double compute() {
        double sum = 0;
        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            System.out.println(Thread.currentThread().getName() +
                    " start:" + start + " end:" + end);
            double temp = (end -start) * 1.0 / start;
            sum += temp;
            System.out.println(sum);
        }else {
            /**关键代码**/
            //如果任务大于阈值，就分裂成两个子任务进行计算
            double middle = (start + end) / 2.0;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle, end);
            leftTask.fork();
            rightTask.fork();
            //等待任务执行结束合并其结果
            sum = leftTask.join() + rightTask.join();
            /**关键代码**/
        }
        return sum;
    }

}

class MyTimerTask extends TimerTask {

    public void run() {
        System.out.println("timer task");
    }

}
