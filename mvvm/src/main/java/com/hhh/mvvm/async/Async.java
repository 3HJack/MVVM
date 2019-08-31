package com.hhh.mvvm.async;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class Async {

  private static final Async ASYNC = new Async();

  // 全局通用线程池，主要用于一些不太耗时的操作
  private final ThreadPoolExecutor mGlobalExecutor;

  private Async() {
    int coreCount = getCoreCount();
    mGlobalExecutor = new ThreadPoolExecutor(2 * coreCount, 2 * coreCount, 3, TimeUnit.MINUTES,
        new LinkedBlockingQueue<>());
    mGlobalExecutor.allowCoreThreadTimeOut(true);
  }

  public static Async getInstance() {
    return ASYNC;
  }

  /**
   * 返回可用CPU核数
   */
  public static int getCoreCount() {
    return Runtime.getRuntime().availableProcessors();
  }

  @NonNull
  public static ThreadPoolExecutor getGlobalExecutor() {
    return ASYNC.mGlobalExecutor;
  }

  public static void execute(@NonNull Runnable r) {
    ASYNC.mGlobalExecutor.execute(r);
  }

  public static Future<?> submit(@NonNull Runnable r) {
    return ASYNC.mGlobalExecutor.submit(r);
  }
}
