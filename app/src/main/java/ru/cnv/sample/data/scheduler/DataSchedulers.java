package ru.cnv.sample.data.scheduler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class DataSchedulers {

    private static DataExecutor executorInstance;

    public static Scheduler getDataScheduler() {
        if (executorInstance == null) {
            executorInstance = new DataExecutor();
        }
        return Schedulers.from(executorInstance);
    }

    public static class DataExecutor implements Executor {

        private HandlerThread thread;

        private DataExecutor() {
            thread = new HandlerThread(getClass().getSimpleName());
            thread.start();
        }

        @Override
        public void execute(Runnable runnable) {
            new Handler(thread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    runnable.run();
                }
            }.sendEmptyMessage(0);
        }
    }
}
