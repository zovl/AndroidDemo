package zovl.zhongguanhua.media.demo.logic;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ExtractorHelper {

    public static final String TAG  = ExtractorHelper.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    public static void extract(String path) {
        Log.d(TAG, "----------------------------------[extract]----------------------------------");
        Log.d(TAG, "path=" + path);

        MediaExtractor extractor;
        int trackCount;
        MediaFormat format;
        String mime = null;
        int sampleRate = 0;
        int channels = 0; // 声道个数：单声道或双声道
        long duration = 0;
        int bitRate = 0;

        File file = null;
        try {
            file = new File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RandomAccessFile r = null;
        try {
            r = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileDescriptor fd = null;
        try {
            fd = r.getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "fd=" + fd);
        Log.d(TAG, "0");
        extractor = new MediaExtractor();
        try {
            extractor.setDataSource(fd);
            Log.d(TAG, "1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        trackCount = extractor.getTrackCount();
        Log.d(TAG, "trackCount=" + trackCount);
        for (int i = 0; i < trackCount; ++i) {
            Log.d(TAG, "----------------------------------------------");
            Log.d(TAG, "----------------------------------------------");
            Log.d(TAG, "2");
            format = extractor.getTrackFormat(i);
            if (format != null) {
                Log.d(TAG, "3");
                try {
                    mime = format.getString(MediaFormat.KEY_MIME);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    channels = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    duration = format.getLong(MediaFormat.KEY_DURATION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    bitRate = format.getInteger(MediaFormat.KEY_BIT_RATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "index=" + i);
                Log.d(TAG, "format=" + format);
                Log.d(TAG, "mime=" + mime);
                Log.d(TAG, "sampleRate=" + sampleRate);
                Log.d(TAG, "mime=" + mime);
                Log.d(TAG, "channels=" + channels);
                Log.d(TAG, "bitRate=" + bitRate);
            }
            // extractor.selectTrack(i);
        }
        /*
        ByteBuffer inputBuffer = ByteBuffer.allocate()
        while (extractor.readSampleData(inputBuffer, ) >= 0) {
            int trackIndex = extractor.getSampleTrackIndex();
            long sampleTime = extractor.getSampleTime();
            extractor.advance();
        }*/
        try {
            extractor.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
