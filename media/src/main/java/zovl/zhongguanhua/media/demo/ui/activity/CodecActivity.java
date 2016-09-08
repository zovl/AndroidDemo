package zovl.zhongguanhua.media.demo.ui.activity;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.nio.ByteBuffer;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;

public class CodecActivity extends TBaseActivity {

    @Override
    public TextView getText() {
        return text;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.text)
    TextView text;

    @Override
    public int getContentView() {
        return R.layout.activity_codec;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.codec, R.id.decode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.codec:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String path = StorageUtil.getRootExitPath("video.mp4");
                        codec(path);
                    }
                }).start();
                break;
            case R.id.decode:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String path = StorageUtil.getRootExitPath("video.mp4");
                        decode(path);
                    }
                }).start();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    private MediaCodec mediaCodec;
    private MediaExtractor extractor;
    private MediaFormat format;
    private String mime;
    private int sampleRate = 0, channels = 0, bitrate = 0;
    private long presentationTimeUs = 0, duration = 0;

    private void codec(String path) {
        log("----------------------------------[codec]----------------------------------");
        log("path=" + path);
    }

    public void decode(String url) {
        log("----------------------------------[decode]----------------------------------");
        log("url=" + url);
        extractor = new MediaExtractor();
        try {
            extractor.setDataSource(url);
        } catch (Exception e) {
            e.printStackTrace();
            log("11");
        }
        try {
            // 音频文件信息
            format = extractor.getTrackFormat(0);
            mime = format.getString(MediaFormat.KEY_MIME);
            sampleRate = format.getInteger(MediaFormat.KEY_SAMPLE_RATE);
            channels = format.getInteger(MediaFormat.KEY_CHANNEL_COUNT);
            duration = format.getLong(MediaFormat.KEY_DURATION);
            bitrate = format.getInteger(MediaFormat.KEY_BIT_RATE);
        } catch (Exception e) {
            e.printStackTrace();
            log("22");
        }
        log("format=" + format);
        log("mime=" + mime);
        log("sampleRate=" + sampleRate);
        log("mime=" + mime);
        log("channels=" + channels);
        log("bitrate=" + bitrate);
        // 检查是否为音频文件
        if (format == null || !mime.startsWith("audio/")) {
            log("33");
            return;
        }
        // 实例化一个指定类型的解码器,提供数据输出
        try {
            mediaCodec = MediaCodec.createDecoderByType(mime);
        } catch (IOException e) {
            e.printStackTrace();
            log("44");
        }
        if (mediaCodec == null) {
            log("55");
            return;
        }
        mediaCodec.configure(format, null, null, 0);
        mediaCodec.start();
        // 用来存放目标文件的数据
        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
        // 解码后的数据
        ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
        // 设置声道类型:AudioFormat.CHANNEL_OUT_MONO单声道，AudioFormat.CHANNEL_OUT_STEREO双声道
        int channelConfiguration = channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO;
        log("channelConfiguration=" + channelConfiguration);
        extractor.selectTrack(0);
        // ==========开始解码=============
        boolean sawInputEOS = false;
        boolean sawOutputEOS = false;
        final long kTimeOutUs = 10;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        while (!sawOutputEOS) {
            try {
                if (!sawInputEOS) {
                    int inputBufIndex = mediaCodec.dequeueInputBuffer(kTimeOutUs);
                    if (inputBufIndex >= 0) {
                        ByteBuffer inputBuffer = inputBuffers[inputBufIndex];
                        int sampleSize = extractor.readSampleData(inputBuffer, 0);
                        if (sampleSize < 0) {
                            log("66");
                            // saw input EOS. Stopping playback
                            sawInputEOS = true;
                            sampleSize = 0;
                        } else {
                            presentationTimeUs = extractor.getSampleTime();
                        }
                        String msg = "inputBufIndex=" + inputBufIndex +
                                "/inputBuffer=" + inputBuffer +
                                "/sampleSize=" + sampleSize +
                                "/presentationTimeUs=" + presentationTimeUs;
                        // log(msg);
                        mediaCodec.queueInputBuffer(inputBufIndex,
                                0,
                                sampleSize,
                                presentationTimeUs,
                                sawInputEOS ? MediaCodec.BUFFER_FLAG_END_OF_STREAM : 0);
                        if (!sawInputEOS) {
                            extractor.advance();
                        }
                    } else {
                        log("inputBufIndex " + inputBufIndex);
                    }
                }
                // !sawInputEOS
                // decode to PCM and push it to the AudioTrack player
                int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, kTimeOutUs);
                if (outputBufferIndex >= 0) {
                    int outputBufIndex = outputBufferIndex;
                    ByteBuffer outputBuffer = outputBuffers[outputBufIndex];
                    final byte[] chunk = new byte[bufferInfo.size];
                    outputBuffer.get(chunk);
                    outputBuffer.clear();
                    if (chunk.length > 0) {
                        // chunk解码后的音频流
                    }
                    String msg = "outputBufferIndex=" + outputBufferIndex +
                            "/outputBuffer=" + outputBuffer +
                            "/chunk=" + chunk;
                    // log(msg);
                    mediaCodec.releaseOutputBuffer(outputBufIndex, false);
                    if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                        log("88");
                        // saw output EOS
                        sawOutputEOS = true;
                    }
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                    outputBuffers = mediaCodec.getOutputBuffers();
                    log("[AudioDecoder]output buffers have changed.");
                } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    MediaFormat oformat = mediaCodec.getOutputFormat();
                    log("[AudioDecoder]output format has changed to " + oformat);
                } else {
                    log("[AudioDecoder] dequeueOutputBuffer returned " + outputBufferIndex);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                log("99");
            }
        }
        log("9");
        if (mediaCodec != null) {
            mediaCodec.stop();
            mediaCodec.release();
            mediaCodec = null;
        }
        if (extractor != null) {
            extractor.release();
            extractor = null;
        }
        // clear source and the other globals
        duration = 0;
        mime = null;
        sampleRate = 0;
        channels = 0;
        bitrate = 0;
        presentationTimeUs = 0;
        duration = 0;
    }
}
