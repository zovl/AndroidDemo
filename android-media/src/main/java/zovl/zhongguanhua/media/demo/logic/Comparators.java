package zovl.zhongguanhua.media.demo.logic;

import android.hardware.Camera;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Comparators {

    public static final String TAG = Comparators.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    /**
     * 对相机参数根据像素进行排序
     */
    public static void sortCameraSize(List<Camera.Size> list) {
        CameraSizeComparator comparator = new CameraSizeComparator();
        Collections.sort(list, comparator);
        printCameraSizes(list);
    }

    private static class CameraSizeComparator implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.width > rhs.width) {
                return 1;
            } else if (lhs.width < rhs.width) {
                return -1;
            } else {
                if (lhs.height > rhs.height) {
                    return 1;
                } else if (lhs.height< rhs.height) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    private static String printCameraSizes(List<Camera.Size> sizes) {
        Log.d(TAG, "----------------printCameraSizes------------------");
        StringBuffer buffer = new StringBuffer();
        buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
        buffer.append("\n");
        if (sizes != null && sizes.size() > 0) {
            for (Camera.Size size : sizes) {
                buffer.append("width=" + size.width + "--height=" + size.height + "\n");
            }
            buffer.append("\n");
            Log.d(TAG, "printCameraSizes: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "Camera.Size is null...";
    }
}
