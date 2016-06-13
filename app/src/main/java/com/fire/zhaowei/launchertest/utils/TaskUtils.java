package com.fire.zhaowei.launchertest.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaowei on 16/6/12.
 */
public class TaskUtils {
    private static final int MAX_THREAD_NUM = 2;    //每种任务的最大线程数

    private static ExecutorService mExecutor = Executors.newCachedThreadPool();
    private static HashMap<String, TaskList> mTables = new HashMap<String, TaskList>();

    private static TaskList getTaskList(String tag){
        TaskList list = mTables.get(tag);
        if (list == null){
            list = new TaskList();
            mTables.put(tag, list);
        }
        return list;
    }
    public static void executeTask(String tag, AbstractTask task){
        TaskList list = getTaskList(tag);
        list.addTask(task);
    }

    public static boolean cancelTask(String tag, AbstractTask task){
        TaskList list = getTaskList(tag);
        return list.removeTask(task);
    }

    public static void setTaskMaxSize(String tag, int maxSize){
        TaskList list = mTables.get(tag);
        if (list == null){
            mTables.put(tag, new TaskList(maxSize));
        } else {
            list.setMaxNum(maxSize);
        }
    }
    private static class TaskList extends LinkedList<AbstractTask>{
        private int mTaskNum;
        private int mMaxNum;

        public TaskList() {
            mTaskNum = 0;
            mMaxNum = MAX_THREAD_NUM;
        }

        public TaskList(int mMaxNum) {
            this.mMaxNum = mMaxNum;
        }

        /**
         * maxNum < 0,则线程池无界
         * @param maxNum
         */
        public void setMaxNum(int maxNum){
            mMaxNum = maxNum;
            int num = size();
            while (num > 0 && (mTaskNum < maxNum || maxNum < 0)){
                num--;
                mTaskNum++;
                mExecutor.execute(new TaskRunner(this));
            }
        }
        public synchronized void addTask(AbstractTask task){
            if (task == null) return;
            addFirst(task);
            if (mMaxNum < 0 || mTaskNum < mMaxNum){
                mExecutor.execute(new TaskRunner(this));
            }
        }
        public synchronized boolean removeTask(AbstractTask task){
            if (task == null) return false;
            if (contains(task)){
                remove(task);
                return true;
            } else {
                task.setIsCanceled(true);
                return false;
            }
        }
        public synchronized AbstractTask pollTask(){
            AbstractTask task = pollLast();
            if (task != null) mTaskNum--;
            return task;
        }
    }

    private static class TaskRunner implements Runnable{
        private TaskList mList;
        public TaskRunner(TaskList mList) {
            this.mList = mList;
        }

        @Override
        public void run() {
            while (true){
                Runnable mTask = mList.pollTask();
                if (mTask == null)break;
                mTask.run();
            }
        }
    }
}
