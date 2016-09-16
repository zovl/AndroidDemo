package zovl.zhongguanhua.thread.demo.logic;

import java.lang.management.*;
import java.util.logging.Logger;

/**
 * 工具：线程工具
 */
public class ThreadUtil {

    private static final String TAG = ThreadUtil.class.getSimpleName();
    private static final Logger LOG = Logger.getLogger(TAG);

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 打印线程信息组
     */
    public static String printThreadInfos(ThreadInfo[] threadInfos) {
        if (threadInfos != null && threadInfos.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (ThreadInfo threadInfo : threadInfos) {
                String s = printThreadInfo(threadInfo);
                buffer.append(s + "\n");
            }
            return buffer.toString();
        }
        return "threadInfos has not info...";
    }

    /**
     * 打印线程信息
     */
    public static String printThreadInfo(ThreadInfo threadInfo) {
        if (threadInfo != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            long blockedCount = threadInfo.getBlockedCount();
            long blockedTime = threadInfo.getBlockedTime();

            MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
            LockInfo[] lockedSynchronizers = threadInfo.getLockedSynchronizers();
            LockInfo lockInfo = threadInfo.getLockInfo();
            String lockName = threadInfo.getLockName();
            long lockOwnerId = threadInfo.getLockOwnerId();
            String lockOwnerName = threadInfo.getLockOwnerName();

            StackTraceElement[] stackTrace = threadInfo.getStackTrace();

            long threadId = threadInfo.getThreadId();
            String threadName = threadInfo.getThreadName();
            Thread.State threadState = threadInfo.getThreadState();

            long waitedTime = threadInfo.getWaitedTime();
            long waitedCount = threadInfo.getWaitedCount();

            boolean isInNative = threadInfo.isInNative();
            boolean isSuspended = threadInfo.isSuspended();
            String toString = threadInfo.toString();

            buffer.append("blockedCount=" + blockedCount + "\n");
            buffer.append("blockedTime=" + blockedTime + "\n");

            buffer.append("lockName=" + lockName + "\n");
            buffer.append("lockOwnerId=" + lockOwnerId + "\n");
            buffer.append("lockOwnerName=" + lockOwnerName + "\n");

            buffer.append("threadId=" + threadId + "\n");
            buffer.append("threadName=" + threadName + "\n");
            buffer.append("threadState=" + threadState + "\n");

            buffer.append("waitedTime=" + waitedTime + "\n");
            buffer.append("waitedCount=" + waitedCount + "\n");

            buffer.append("isInNative=" + isInNative + "\n");
            buffer.append("isSuspended=" + isSuspended + "\n");
            buffer.append("toString=" + toString + "\n");

            buffer.append("\n");
            LOG.info("printThreadInfo: " + buffer.toString());
            return buffer.toString();
        }
        return "threadInfo has not info...";
    }

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 获取所有线程信息
     */
    public static ThreadInfo[] dumpAllThreads() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = bean.dumpAllThreads(false, false);
        return threadInfos;
    }

    /**
     * 获取所有线程id
     */
    public static long[] getAllThreadIds() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] ids = bean.getAllThreadIds();
        return ids;
    }
}
