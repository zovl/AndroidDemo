package zovl.zhongguanhua.media.demo.logic;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * 工具：多媒体
 */
public class MediaUtil {

    public static final String TAG  = MediaUtil.class.getSimpleName();

    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    /**
     * 打印缓存字节
     */
    public static String printByteBuffer(ByteBuffer byteBuffer) {
        Log.d(TAG, "----------------[printByteBuffer]------------------");
        if (byteBuffer != null) {
            int limit = byteBuffer.limit();
            int position = byteBuffer.position();
            byte[] array = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset();
            boolean hasArray = byteBuffer.hasArray();
            boolean isDirect = byteBuffer.isDirect();
            char c = byteBuffer.getChar();
            double d = byteBuffer.getDouble();
            float f = byteBuffer.getFloat();
            int i = byteBuffer.getInt();
            long l = byteBuffer.getLong();
            short s = byteBuffer.getShort();

            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            buffer.append("limit=" + limit + "\n");
            buffer.append("position=" + position + "\n");
            buffer.append("arrayLength=" + array.length + "\n");
            buffer.append("arrayOffset=" + arrayOffset + "\n");
            buffer.append("hasArray=" + hasArray + "\n");
            buffer.append("isDirect=" + isDirect + "\n");
            buffer.append("c=" + c + "\n");
            buffer.append("d=" + d + "\n");
            buffer.append("f=" + f + "\n");
            buffer.append("i=" + i + "\n");
            buffer.append("l=" + l + "\n");
            buffer.append("s=" + s + "\n");

            buffer.append("\n");
            Log.d(TAG, "printByteBuffer: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "byteBuffer is null";

    }

    /**
     * 打印缓存字符
     */
    public static String printCharBuffer(CharBuffer charBuffer) {
        Log.d(TAG, "----------------[printCharBuffer]------------------");
        if (charBuffer != null) {
            int limit = charBuffer.limit();
            int position = charBuffer.position();
            char[] array = charBuffer.array();
            int arrayOffset = charBuffer.arrayOffset();
            boolean hasArray = charBuffer.hasArray();
            boolean isDirect = charBuffer.isDirect();

            String toString = charBuffer.toString();

            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            buffer.append("limit=" + limit + "\n");
            buffer.append("position=" + position + "\n");
            buffer.append("arrayLength=" + array.length + "\n");
            buffer.append("arrayOffset=" + arrayOffset + "\n");
            buffer.append("hasArray=" + hasArray + "\n");
            buffer.append("isDirect=" + isDirect + "\n");
            buffer.append("toString=" + toString + "\n");

            buffer.append("\n");
            Log.d(TAG, "printByteBuffer: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "charBuffer is null";
    }

    /**
     * 打印编解码器数据信息
     */
    public static String printBufferInfo(MediaCodec.BufferInfo bufferInfo) {
        Log.d(TAG, "----------------[printBufferInfo]------------------");
        if (bufferInfo != null) {
            int offset = bufferInfo.offset;
            int size = bufferInfo.size;
            long presentationTimeUs = bufferInfo.presentationTimeUs;
            int flags = bufferInfo.flags;

            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            buffer.append("offset=" + offset + "\n");
            buffer.append("size=" + size + "\n");
            buffer.append("presentationTimeUs=" + presentationTimeUs + "\n");
            buffer.append("flags=" + flags + "\n");

            buffer.append("\n");
            Log.d(TAG, "printByteBuffer: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "bufferInfo is null";
    }

    /**
     * 打印编解码器信息
     */
    public static String printMediaCodec(MediaCodec mediaCodec) {
        Log.d(TAG, "----------------[printMediaCodec]------------------");
        if (mediaCodec != null) {
            String name = mediaCodec.getName();
            MediaCodecInfo mediaCodecInfo = mediaCodec.getCodecInfo();

            StringBuffer buffer = new StringBuffer();
            buffer.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n");
            buffer.append("\n");

            buffer.append("name=" + name + "\n");
            buffer.append("mediaCodecInfo" + "\n");
            printMediaCodecInfo(mediaCodecInfo);

            if (Build.VERSION.SDK_INT >= 21) {
                MediaFormat mediaFormat = mediaCodec.getInputFormat();
                buffer.append("mediaFormat=" + mediaFormat + "\n");
            }

            buffer.append("\n");
            Log.d(TAG, "printMediaCodec: " + "\n" + buffer.toString());
            return buffer.toString();
        }
        return "mediaCodec is null";
    }

    /**
     * 打印编解码器信息
     */
    public static String printMediaCodecInfo(MediaCodecInfo mediaCodecInfo) {
        Log.d(TAG, "----------------[printMediaCodecInfo]------------------");
        if (mediaCodecInfo != null) {
            String name = mediaCodecInfo.getName();
            boolean isEncoder = mediaCodecInfo.isEncoder();
            String[] supportedTypes =mediaCodecInfo.getSupportedTypes();

            StringBuffer buffer = new StringBuffer();

            buffer.append("\t" + "name=" + name + "\n");
            buffer.append("\t" + "isEncoder=" + isEncoder + "\n");

            buffer.append("\t" + "supportedTypes" + "\n");
            for (int i = 0; i < supportedTypes.length; ++i) {
                buffer.append("\t\t" + supportedTypes[i] + "\n");
            }
            return buffer.toString();
        }
        return "mediaCodecInfo is null";
    }
}
