package zovl.zhongguanhua.thread.demo.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具：线程工具
 */
public class ThreadUtil {

    public static final String TAG = ThreadUtil.class.getSimpleName();

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 打印线程参数
     */
    public static String printThreads(Thread[] threads) {
        if (threads != null && threads.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (Thread thread : threads) {
                String s = printThread(thread);
                buffer.append(s + "\n");
            }
            return buffer.toString();
        }
        return "threads has not info...";
    }

    /**
     * 打印线程参数（thread）
     */
    public static String printThread(Thread thread) {
        if (thread != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            String name = thread.getName();
            String toString = thread.toString();
            long id = thread.getId();
            int priority = thread.getPriority();
            Thread.State state = thread.getState();

            ThreadGroup threadGroup = thread.getThreadGroup();

            buffer.append("toString=" + toString + "\n");
            buffer.append("name=" + name + "\n");
            buffer.append("id=" + id + "\n");
            buffer.append("priority=" + priority + "\n");
            buffer.append("state=" + state + "\n");
            buffer.append("threadGroup=" + threadGroup + "\n");

            buffer.append("\n");
            Log.d(TAG, "printThread: " + buffer.toString());
            return buffer.toString();
        }
        return "thread has not info...";
    }

    /**
     * 打印线程组参数
     */
    public static String printGroups(ThreadGroup[] groups) {
        if (groups != null && groups.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (ThreadGroup group : groups) {
                String s = printGroup(group);
                buffer.append(s + "\n");
            }
            return buffer.toString();
        }
        return "groups has not info...";
    }

    /**
     * 打印线程组参数（group）
     */
    public static String printGroup(ThreadGroup group) {
        if (group != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            String name = group.getName();
            int maxPriority = group.getMaxPriority();
            String toString = group.toString();
            int activeCount = group.activeCount();
            int activeGroupCount = group.activeGroupCount();
            boolean isDaemon = group.isDaemon();
            boolean isDestroyed = group.isDestroyed();

            ThreadGroup parent = group.getParent();

            buffer.append("toString=" + toString + "\n");
            buffer.append("name=" + name + "\n");
            buffer.append("maxPriority=" + maxPriority + "\n");
            buffer.append("activeCount=" + activeCount + "\n");
            buffer.append("activeGroupCount=" + activeGroupCount + "\n");
            buffer.append("isDaemon=" + isDaemon + "\n");
            buffer.append("isDestroyed=" + isDestroyed + "\n");
            buffer.append("parent=" + parent + "\n");

            buffer.append("\n");
            Log.d(TAG, "printGroup: " + buffer.toString());
            return buffer.toString();
        }
        return "group has not info...";
    }

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 获取根线程组（group）
     */
    public static ThreadGroup getRoot() {
        Log.d(TAG, "----------------getRoot------------------");
        ThreadGroup root = null;
        ThreadGroup group = null;
        try {
            group = Thread.currentThread().getThreadGroup();
            Log.d(TAG, "getRoot: group=" + group);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (group != null) {
            ThreadGroup parent = group;
            while ((parent = parent.getParent()) != null) {
                Log.d(TAG, "getRoot: parent=" + parent);
                root = parent;
            }
        }
        if (root != null) {
            return root;
        }
        Log.d(TAG, "getRoot: root=" + root);
        return null;
    }

    /**
     * 获取线程组所有父线程（group）
     */
    public static ThreadGroup[] getParents(Thread thread) {
        Log.d(TAG, "----------------getParents------------------");
        if (thread != null) {
            Log.d(TAG, "getParents: thread=" + thread.getName());
            ThreadGroup group = thread.getThreadGroup();
            if (group != null) {
                return getParents(group);
            }
        }
        return null;
    }

    /**
     * 获取线程所有父线程（thread）
     */
    public static ThreadGroup[] getParents(ThreadGroup group) {
        Log.d(TAG, "----------------getParents------------------");
        if (group != null) {
            Log.d(TAG, "getParents: group=" + group.getName());
            List<ThreadGroup> list = new ArrayList<>();
            list.add(group);
            if (group != null) {
                list.add(group);
                ThreadGroup parent = group;
                while ((parent = parent.getParent()) != null) {
                    Log.d(TAG, "getParents: parent=" + parent.getName());
                    list.add(parent);
                }
            }
            if (list.size() > 0) {
                ThreadGroup[] groups = new ThreadGroup[list.size()];
                int i = 0;
                for (ThreadGroup g : groups) {
                    groups[i] = g;
                }
                return groups;
            }
        }
        return null;
    }

    /**
     * 获取线程组所有子线程
     */
    public static Thread[] getGroupMembers(ThreadGroup group) {
        Log.d(TAG, "----------------getGroupMembers------------------");
        if (group != null) {
            int activeCount = group.activeCount();
            int n;
            Thread[] threads;
            do {
                activeCount *= 2;
                threads = new Thread[activeCount];
                n = group.enumerate(threads, false);
            } while (n == activeCount);
            if (threads != null) {
                Log.d(TAG, "getGroupMembers: size=" + threads.length);
                return threads;
            }
        }
        return null;
    }

    /**
     * 获取所有线程组
     */
    public static ThreadGroup[] getAllGroups() {
        Log.d(TAG, "----------------getAllGroups------------------");
        final ThreadGroup root = getRoot();
        if (root != null) {
            int activeGroupCount = root.activeGroupCount();
            int n;
            ThreadGroup[] groups;
            do {
                activeGroupCount *= 2;
                groups = new ThreadGroup[activeGroupCount];
                n = root.enumerate(groups, true);
            } while ( n == activeGroupCount );
            if (groups != null) {
                ThreadGroup[] allGroups = new ThreadGroup[n + 1];
                allGroups[0] = root;
                System.arraycopy(groups, 0, allGroups, 1, n);
                if (allGroups != null) {
                    Log.d(TAG, "getAllGroups: size=" + allGroups.length);
                    // printGroups(allGroups);
                    return allGroups;
                }
            }
        }
        return null;
    }

    /**
     * 获取线程（name）
     */
    public static Thread getThread(String name) {
        Log.d(TAG, "----------------getThread------------------");
        Log.d(TAG, "getThread: name=" + name);
        if (name != null) {
            Thread[] threads = getAllThreads( );
            for (Thread thread : threads) {
                if (thread.getName().equals(name)) {
                    return thread;
                }
            }
        }
        return null;
    }

    /**
     * 获取线程（id）
     */
    public static Thread getThread(int id) {
        Log.d(TAG, "----------------getThread------------------");
        Log.d(TAG, "getThread: id=" + id);
        Thread[] threads = getAllThreads( );
        for (Thread thread : threads) {
            if (thread.getId() == id) {
                return thread;
            }
        }
        return null;
    }

    /**
     * 获取所有线程
     */
    public static Thread[] getAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        if (topGroup != null) {
            int activeCount = topGroup.activeCount() * 2;
            Thread[] threads = new Thread[activeCount];
            int n = topGroup.enumerate(threads);
            Thread[] allThreads = new Thread[n];
            System.arraycopy(threads, 0, allThreads, 0, n);
            // printThreads(threads);
            return allThreads;
        }
        return null;
    }
}
