package zovl.zhongguanhua.media.demo.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.googlecode.mp4parser.authoring.tracks.H264TrackImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.framework.lib.utils.StorageUtil;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.MP4parserHelper;

public class MP4parserActivity extends TBaseActivity {

    public static final String TAG = MP4parserActivity.class.getSimpleName();

    @Bind(R.id.insertStartTime)
    EditText startTime;
    @Bind(R.id.insertEndTime)
    EditText endTime;
    @Bind(R.id.trimPath)
    EditText trimPath;
    @Bind(R.id.extractVideoPath)
    EditText extractVideoPath;
    @Bind(R.id.extractAudioPath)
    EditText extractAudioPath;
    @Bind(R.id.muxVideoPath)
    EditText muxVideoPath;
    @Bind(R.id.muxAudioPath)
    EditText muxAudioPath;

    public long getStartTime() {
        if (startTime.getText() != null)
            Long.valueOf(startTime.getText().toString());
        return 0;
    }

    public long getEndTime() {
        if (endTime.getText() != null)
            Long.valueOf(endTime.getText().toString());
        return 255 * 1000;
    }

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
        return R.layout.activity_mp4parser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String videoPath = StorageUtil.getRootExitPath("video.mp4");
        final String audioPath = StorageUtil.getRootExitPath("honor.mp3");
        setText(videoPath);

        trimPath.setText(videoPath);
        extractVideoPath.setText(videoPath);
        extractAudioPath.setText(videoPath);
        muxVideoPath.setText(videoPath);
        muxAudioPath.setText(videoPath);
    }

    private String videoPath, audioPath;

    @OnClick({R.id.trim,
            R.id.extractVideo,
            R.id.extractAudio,
            R.id.mux,
            R.id.mutilMux})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trim:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = trimPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_trim.mp4");
                        boolean flag = MP4parserHelper.startTrim(src, dst.getPath(), getStartTime(), getEndTime());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
            case R.id.extractAudio:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = extractAudioPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_audio.mp4");
                        boolean flag = MP4parserHelper.extractAudio(src, dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    audioPath = dst.getAbsolutePath();
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
            case R.id.extractVideo:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String src = extractVideoPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_video.mp4");
                        boolean flag = MP4parserHelper.extractVideo(src, dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    videoPath = dst.getAbsolutePath();
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
            case R.id.mux:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String videoPath = muxVideoPath.getText().toString();
                        final String audioPath = muxAudioPath.getText().toString();
                        final File dst = StorageUtil.getRootFile("_mp4parser_mux.mp4");
                        boolean flag = MP4parserHelper.muxVideoAndAudio(videoPath, audioPath, dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
            case R.id.mutilMux:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String path = StorageUtil.getRootExitPath("video.mp4");
                        final File dst = StorageUtil.getRootFile("_mp4parser_mutilMux.mp4");
                        boolean flag = MP4parserHelper.muxMedia(new String[]{ new String(path), new String(path), new String(path) }, dst.getPath());
                        if (flag)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    toastShort(dst.getAbsolutePath());
                                    setText(dst.getAbsolutePath() + "\n");
                                }
                            });
                    }
                }).start();
                break;
        }
    }

    // ---------------------------------------------------------------------------------

    /**
     * 视频剪辑（isoviewer-1.0-RC-35.jar）
     *
     * @param srcPath   视频源文件
     * @param dstPath   视频剪辑后保存的文件
     * @param startTime 开始（毫秒）
     * @param endTime   结束（毫秒）
     * @return 是否剪辑成功
     */
    public static boolean startTrim(String srcPath, String dstPath, long startTime, long endTime) {
        Log.d(TAG, "----------------------------------[startTrim]----------------------------------");
        Log.d(TAG, "startTrim: srcPath=" + srcPath);
        Log.d(TAG, "startTrim: dstPath=" + dstPath);
        Log.d(TAG, "startTrim: startTime=" + startTime);
        Log.d(TAG, "startTrim: endTime=" + endTime);
        try {
            startTrim(new File(srcPath), new File(dstPath), startTime, endTime);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频剪辑
     */
    public static void startTrim(File scrFile, File dstFile, long startTime, long endTime) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie movie = MovieCreator.build(scrFile.getPath());
        List<Track> tracks = movie.getTracks();
        movie.setTracks(new LinkedList<Track>());
        double startTimeSec = startTime / 1000;
        double endTimeSec = endTime / 1000;
        boolean timeCorrected = false;
        for (Track track : tracks) {
            if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startTimeSec = correctTimeToSyncSample(track, startTimeSec, false);
                endTimeSec = correctTimeToSyncSample(track, endTimeSec, true);
                timeCorrected = true;
            }
        }
        for (Track track : tracks) {
            long currentSample = 0;
            double currentTime = 0;
            double lastTime = 0;
            long startSample = -1;
            long endSample = -1;
            for (int i = 0; i < track.getSampleDurations().length; i++) {
                long delta = track.getSampleDurations()[i];
                if (currentTime <= startTimeSec) {
                    startSample = currentSample;
                }
                if (currentTime <= endTimeSec) {
                    endSample = currentSample;
                }
                lastTime = currentTime;
                currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            movie.addTrack(new CroppedTrack(track, startSample, endSample));
            // movie.addTrack(new AppendTrack(new CroppedTrack(track, startSample, endSample), new CroppedTrack(track, startSample, endSample)));
        }
        Container container = new DefaultMp4Builder().build(movie);
        FileOutputStream fos = new FileOutputStream(dstFile);
        FileChannel fc = fos.getChannel();
        container.writeContainer(fc);
        fc.close();
        fos.close();
        Log.d(TAG, "startTrim: true");
    }

    private static double correctTimeToSyncSample(Track track, double cutHere, boolean next) {
        Log.d(TAG, "--------------------------------------------------------------------");
        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
        long currentSample = 0;
        double currentTime = 0;
        for (int i = 0; i < track.getSampleDurations().length; i++) {
            long delta = track.getSampleDurations()[i];

            if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
                // samples always startCollectionActivity with 1 but we startCollectionActivity with zero therefore
                // +1
                timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(), currentSample + 1)] = currentTime;
            }
            currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
            currentSample++;
        }
        double previous = 0;
        for (double timeOfSyncSample : timeOfSyncSamples) {
            if (timeOfSyncSample > cutHere) {
                if (next) {
                    return timeOfSyncSample;
                } else {
                    return previous;
                }
            }
            previous = timeOfSyncSample;
        }
        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
    }

    // ---------------------------------------------------------------------------------

    /**
     * 视频合成
     *
     * @param srcPaths 视频源文件
     * @param dstPath   视频后保存的文件
     * @return 是否视频音频合成成功
     */
    public static boolean muxMedia(String[] srcPaths, String dstPath) {
        Log.d(TAG, "----------------------------------[muxMedia]----------------------------------");
        Log.d(TAG, "muxMedia: srcPaths=" + srcPaths);
        Log.d(TAG, "muxMedia: dstPath=" + dstPath);
        try {
            Movie newMovie = muxMedia_1(srcPaths, dstPath);
            writeMovie(new File(dstPath), newMovie);
            Log.d(TAG, "muxMedia: true");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频合成
     */
    public static Movie muxMedia_1(String[] srcPaths, String dstPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");

        Movie newMovie = new Movie();
        Movie[] srcMovies = new Movie[srcPaths.length];
        int index = 0;
        for (String srcPath : srcPaths) {
            srcMovies[index] = MovieCreator.build(srcPath);
            index++;
        }
        List<Track> videoTracks = new LinkedList<>();
        List<Track> audioTracks = new LinkedList<>();

        //---------------------------------

        // 第一步：有效
        for (Movie movie : srcMovies) {
            for (Track track : movie.getTracks()) {
                if (track.getHandler().equals("soun")) {
                    audioTracks.add(track);
                }
                if (track.getHandler().equals("vide")) {
                    videoTracks.add(track);
                }
            }
        }

        //---------------------------------

        // 第二步：有效
        if (audioTracks.size() > 0) {
            for (Track track : audioTracks) {
                newMovie.addTrack(track);
            }
        }
        if (videoTracks.size() > 0) {
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }
        }

        /*
        // 第二步：无效
        AppendTrack appendTrack;
        if (audioTracks.size() > 0) {
            appendTrack = new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()]));
            newMovie.addTrack(appendTrack);
        }
        if (videoTracks.size() > 0) {
            appendTrack = new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()]));
            newMovie.addTrack(appendTrack);
        }*/

        return newMovie;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 视频音频合成
     *
     * @param videoPath 视频源文件
     * @param audioPath 音频源文件
     * @param dstPath   视频后保存的文件
     * @return 是否视频音频合成成功
     */
    public static boolean muxVideoAndAudio(String videoPath, String audioPath, String dstPath) {
        Log.d(TAG, "----------------------------------[muxVideoAndAudio]----------------------------------");
        Log.d(TAG, "muxVideoAndAudio: videoPath=" + videoPath);
        Log.d(TAG, "muxVideoAndAudio: audioPath=" + audioPath);
        Log.d(TAG, "muxVideoAndAudio: dstPath=" + dstPath);
        try {
            Movie newMovie = muxVideoAndAudio_2(new File(videoPath), new File(audioPath), new File(dstPath));
            writeMovie(new File(dstPath), newMovie);
            Log.d(TAG, "muxVideoAndAudio: true");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频音频合成
     */
    public static Movie muxVideoAndAudio(File videoFile, File audioFile, File dstFile) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie videoMovie = MovieCreator.build(videoFile.getAbsolutePath());
        Movie audioMovie = MovieCreator.build(audioFile.getAbsolutePath());
        Movie newMovie = new Movie();

        Track audioTrack = audioMovie.getTracks().get(0);
        newMovie.addTrack(audioTrack);

        List<Track> videoTracks = new LinkedList<>();
        for (int i = 0; i < videoMovie.getTracks().size(); ++i) {
            if (i != 0) {
                videoTracks.add(videoMovie.getTracks().get(i));
            }
        }
        if (videoTracks.size() > 0) {
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }
        }
        return newMovie;
    }

    /**
     * 视频音频合成
     */
    public static Movie muxVideoAndAudio_2(File videoFile, File audioFile, File dstFile) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie videoMovie = MovieCreator.build(videoFile.getAbsolutePath());
        Movie audioMovie = MovieCreator.build(audioFile.getAbsolutePath());
        Movie newMovie = new Movie();

        List<Track> videoTracks = new LinkedList<>();
        List<Track> audioTracks = new LinkedList<>();

        //---------------------------------

        // 第一步：有效
        for (Track track : audioMovie.getTracks()) {
            if (track.getHandler().equals("soun")) {
                audioTracks.add(track);
            }
        }
        for (Track track : videoMovie.getTracks()) {
            if (track.getHandler().equals("vide")) {
                videoTracks.add(track);
            }
        }

        //---------------------------------

        // 第二步：有效
        if (audioTracks.size() > 0) {
            for (Track track : audioTracks) {
                newMovie.addTrack(track);
            }
        }
        if (videoTracks.size() > 0) {
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }
        }

        /*
        // 第二步：无效
        if (videoTracks.size() > 0) {
            newMovie.setTracks(videoTracks);
        }
        if (audioTracks.size() > 0) {
            newMovie.setTracks(audioTracks);
        }*/

        /*
        // 第二步：无效
        if (videoTracks.size() > 0) {
            AppendTrack appendTrack = new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()]));
            newMovie.addTrack(appendTrack);
        }
        if (videoTracks.size() > 0) {
            AppendTrack appendTrack = new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()]));
            newMovie.addTrack(appendTrack);
        }*/

        return newMovie;
    }

    /**
     * 视频音频合成
     */
    public static Movie muxVideoAndAudio_3(File h264File, File aacFile, File dstFile) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(h264File.getPath()));
        AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(aacFile.getPath()));
        Movie movie = new Movie();
        movie.addTrack(h264Track);
        movie.addTrack(aacTrack);
        return movie;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 提取视频
     *
     * @param srcPath 视频源文件
     * @param dstPath 视频提取视频后保存的文件
     * @return 是否提取视频成功
     */
    public static boolean extractVideo(String srcPath, String dstPath) {
        Log.d(TAG, "----------------------------------[extractVideo]----------------------------------");
        Log.d(TAG, "extractVideo: srcPath=" + srcPath);
        Log.d(TAG, "extractVideo: dstPath=" + dstPath);
        try {
            Movie newMoview = extractVideo(new File(srcPath), new File(dstPath));
            writeMovie(new File(dstPath), newMoview);
            Log.d(TAG, "extractVideo: true");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提取视频
     */
    public static Movie extractVideo(File scrFile, File dstFile) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie srcMovie = MovieCreator.build(scrFile.getAbsolutePath());
        Movie dstMovie = new Movie();

        //---------------------------------

        // 第一步：有效
        List<Track> srcTracks = new LinkedList<>();
        for (Track track : srcMovie.getTracks()) {
            if (track.getHandler().equals("vide")) {
                srcTracks.add(track);
                try {
                    printTrack(track);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /*
        // 第一步：有效
        for (int i = 0; i < srcMovie.getTracks().size(); ++i) {
            if (i != 0) {
                srcTracks.add(srcMovie.getTracks().get(i));
            }
        }*/

        //---------------------------------

        // 第二步：有效
        if (srcTracks.size() > 0) {
            dstMovie.setTracks(srcTracks);
        }

        /*
        // 第二步：无效
        if (srcTracks.size() > 0) {
            for (Track track : srcTracks) {
                dstMovie.addTrack(track);
            }
        }*/

        /*
        // 第二步：无效
        if (srcTracks.size() > 0) {
            AppendTrack appendTrack = new AppendTrack(srcTracks.toArray(new Track[srcTracks.size()]));
            dstMovie.addTrack(appendTrack);
        }*/

        return dstMovie;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 提取音轨
     *
     * @param srcPath 视频源文件
     * @param dstPath 视频提取音轨后保存的文件
     * @return 是否提取音轨成功
     */
    public static boolean extractAudio(String srcPath, String dstPath) {
        Log.d(TAG, "----------------------------------[extractAudio]----------------------------------");
        Log.d(TAG, "extractAudio: srcPath=" + srcPath);
        Log.d(TAG, "extractAudio: dstPath=" + dstPath);
        try {
            Movie newMoview = extractAudio(new File(srcPath), new File(dstPath));
            writeMovie(new File(dstPath), newMoview);
            Log.d(TAG, "extractAudio: true");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提取音轨
     */
    public static Movie extractAudio(File scrFile, File dstFile) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie srcMovie = MovieCreator.build(scrFile.getAbsolutePath());
        Movie dstMovie = new Movie();

        //---------------------------------

        // 第一步：有效
        List<Track> srcTracks = new LinkedList<>();
        for (Track track : srcMovie.getTracks()) {
            if (track.getHandler().equals("soun")) {
                srcTracks.add(track);
            }
        }

        /*
        // 第一步：有效
        Track srcTrack = srcMovie.getTracks().get(0);*/

        // 第一步：有效
        // CroppedTrack croppedTrack = new CroppedTrack(srcTrack, 0, srcTrack.getSamples().size());

        //---------------------------------

        // 第二步：有效
        if (srcTracks.size() > 0) {
            dstMovie.setTracks(srcTracks);
        }

        /*
        // 第二步：有效
        dstMovie.addTrack(srcTrack);*/

        return dstMovie;
    }

    // ---------------------------------------------------------------------------------

    /**
     * 保存视频
     *
     * @param file 保存的路径
     * @param movie 保存的视频
     */
    private static void writeMovie(File file, Movie movie) throws IOException {
        Container container = new DefaultMp4Builder().build(movie);
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel fc = fos.getChannel();
        container.writeContainer(fc);
        fc.close();
        fos.close();
    }

    /**
     * 保存视频
     */
    private static void writeMovie_2(File file, Movie movie) throws IOException {
        Container container = new DefaultMp4Builder().build(movie);
        FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
        container.writeContainer(fc);
        fc.close();
    }

    // ---------------------------------------------------------------------------------

    /**
     * 打印轨道
     */
    private static void printTrack(Track track) throws Exception {
        Log.d(TAG, "--------------------------------[Track]------------------------------------");
        Log.d(TAG, "printTrack: track=" + track);
        Log.d(TAG, "printTrack: hanlder=" + track.getHandler());
        Log.d(TAG, "printTrack: duration=" + track.getDuration());
        printBox(track.getMediaHeaderBox());/*
        for (Sample sample : track.getSamples()) {
            printSample(sample);
        }*/
    }

    private static void printBox(Box box) throws Exception {
        Log.d(TAG, "--------------------------------[Box]------------------------------------");
        Log.d(TAG, "printTrack: box=" + box);
        Log.d(TAG, "printTrack: type=" + box.getType());
        Log.d(TAG, "printTrack: offSet=" + box.getOffset());
        Log.d(TAG, "printTrack: parent=" + box.getParent());
        Log.d(TAG, "printTrack: size=" + box.getSize());
    }

    private static void printSample(Sample sample) throws Exception {
        Log.d(TAG, "--------------------------------[Sample]------------------------------------");
        Log.d(TAG, "printTrack: sample=" + sample);
    }
}
