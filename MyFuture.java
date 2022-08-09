/* 这是一段注释，异步计算
 * */

package com.nuist.thread.JUC_class;

import java.util.concurrent.*;

public class MyCompletableFuture {
    public static void main(String[] args) throws ExecutionException{
//       第一种：无返回值
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() ->
                System.out.println(Thread.currentThread().getName() + "无返回值"));

//        返回结果
//        future1.get();

//       第二种：有返回值
//        创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "有返回值");
                int num = ThreadLocalRandom.current().nextInt(5);

                //            延时1秒
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                判断是否异常
                if (num > 0) {
                    num = 10 / 0;
                }

                return num;
            },threadPool).whenComplete((res, e) -> {
                if (e == null) {
                    System.out.println("阶段计算完成，结果为"+res);
                }
            }).exceptionally((e)->{
                e.printStackTrace();
                System.out.println("出现异常"+e.getCause()+"\t"+e.getMessage());
                return null;
            });

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

        System.out.println(Thread.currentThread().getName()+"在执行其他任务");
    }
}

