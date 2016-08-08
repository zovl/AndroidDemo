package zovl.zhongguanhua.framework.lib.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StorageUtil {

    private static final String TAG = StorageUtil.class.getSimpleName();

    // -----------------------------------------------------------------------------

    public static File getRootDir() {
        final File dir = new File("/mnt/sdcard", "AndroidDemo");
        try {
            dir.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }

    public static File getRootFile(final String ext) {
        final File rootDir = getRootDir();
        if (rootDir.canWrite()) {
            return new File(rootDir, createFileName(ext));
        }
        return null;
    }

    public static String getRootExitPath(String fileName) {
        String path = getRootDir().getPath() + File.separator + fileName;
        return path;
    }

    private static String createFileName(final String ext) {
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
        String name = format.format(calendar.getTime()) + ext;
        return name;
    }
}
