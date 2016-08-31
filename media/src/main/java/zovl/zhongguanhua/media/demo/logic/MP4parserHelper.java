package zovl.zhongguanhua.media.demo.logic;

import android.util.Log;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MP4parserHelper {

    public static final String TAG  = MP4parserHelper.class.getSimpleName();

    // ---------------------------------------------------------------------------------

    /**
     * 视频剪辑（isoviewer-1.0-RC-35.jar）
     *
     * @param srcPath   视频源文件
     * @param dstPath   视频剪辑后保存的文件
     * @param startMillSecs 开始（毫秒）
     * @param endMilliSecs   结束（毫秒）
     * @return 是否剪辑成功
     */
    public static boolean trimMedia(String srcPath, String dstPath, long startMillSecs, long endMilliSecs) {
        Log.d(TAG, "----------------------------------[startTrim]----------------------------------");
        Log.d(TAG, "trimMedia: srcPath=" + srcPath);
        Log.d(TAG, "trimMedia: dstPath=" + dstPath);
        Log.d(TAG, "trimMedia: startTime=" + startMillSecs);
        Log.d(TAG, "trimMedia: endTime=" + endMilliSecs);
        try {
            Movie newMovie = trimMedia_1(srcPath, startMillSecs, endMilliSecs);
            if (newMovie != null)
                writeMovie_1(dstPath, newMovie);
            Log.d(TAG, "trimMedia: true");
            printMovie(newMovie);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频剪辑
     */
    private static Movie trimMedia_1(String srcPath, long startMillSecs, long endMilliSecs) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie origMovie = MovieCreator.build(srcPath);
        printMovie(origMovie);
        List<Track> tracks = origMovie.getTracks();
        origMovie.setTracks(new LinkedList<Track>());
        double startSecs = startMillSecs / 1000;
        double endSecs = endMilliSecs / 1000;
        boolean timeCorrected = false;
        for (Track track : tracks) {
            if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
                if (timeCorrected) {
                    throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
                }
                startSecs = correctTimeToSyncSample(track, startSecs, false);
                endSecs = correctTimeToSyncSample(track, endSecs, true);
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
                if (currentTime <= startSecs) {
                    startSample = currentSample;
                }
                if (currentTime <= endSecs) {
                    endSample = currentSample;
                }
                lastTime = currentTime;
                currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
                currentSample++;
            }
            Log.d(TAG, "muxVideoAndAudio: " + startSample + "--" + endSample);
            origMovie.addTrack(new CroppedTrack(track, startSample, endSample));
        }
        return origMovie;
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
            Movie newMovie = muxMedia_1(srcPaths);
            if (newMovie != null) {
                writeMovie_1(dstPath, newMovie);
            }
            Log.d(TAG, "muxMedia: true");
            printMovie(newMovie);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频合成
     */
    private static Movie muxMedia_1(String[] srcPaths) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");

        Movie[] srcMovies = new Movie[srcPaths.length];
        int index = 0;
        for (String srcPath : srcPaths) {
            srcMovies[index] = MovieCreator.build(srcPath);
            index++;
        }
        List<Track> videoTracks = new LinkedList<>();
        List<Track> audioTracks = new LinkedList<>();
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
        Movie newMovie = new Movie();
        if (audioTracks.size() > 0) {
            newMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
        }
        if (videoTracks.size() > 0) {
            newMovie.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
        }
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
            // 有效
            // Movie newMovie = muxVideoAndAudio_2(new File(videoPath), new File(audioPath), new File(dstPath));
            // if (newMovie != null) {
            //     writeMovie_1(new File(dstPath), newMovie);
            // }

            // 有效
            Movie newMovie = muxVideoAndAudio_3(videoPath, audioPath);
            if (newMovie != null) {
                writeMovie_1(dstPath, newMovie);
            }

            Log.d(TAG, "muxVideoAndAudio: true");
            printMovie(newMovie);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 视频音频合成
     */
    private static Movie muxVideoAndAudio_1(String videoPath, String audioPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie videoMovie = MovieCreator.build(videoPath);
        Movie audioMovie = MovieCreator.build(audioPath);
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
    private static Movie muxVideoAndAudio_2(String videoPath, String audioPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie videoMovie = MovieCreator.build(videoPath);
        Movie audioMovie = MovieCreator.build(audioPath);
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
    private static Movie muxVideoAndAudio_3(String videoPath, String audioPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie videoMovie = MovieCreator.build(videoPath);
        Movie audioMovie = MovieCreator.build(audioPath);
        Movie newMovie = new Movie();

        Track audioTrack = audioMovie.getTracks().get(0);
        newMovie.addTrack(audioTrack);

        Track zeroTrack = videoMovie.getTracks().get(0);
        newMovie.addTrack(new CroppedTrack(zeroTrack, 0, zeroTrack.getSamples().size()));
        // newMovie.addTrack(new AppendTrack(new ClippedTrack(zeroTrack, 0, zeroTrack.getSamples().size()), new ClippedTrack(zeroTrack, 0, zeroTrack.getSamples().size())));

        return newMovie;
    }

    /**
     * 视频音频合成（音轨裁剪和拼接）
     * @deprecated 报错
     */
    private static Movie muxVideoAndAudio_4(String videoPath, String audioPath) throws IOException {
        Movie videoMovie = MovieCreator.build(videoPath);
        Movie audioMovie = MovieCreator.build(audioPath);
        printMovie(audioMovie);
        printMovie(videoMovie);
        Movie newMovie = new Movie();
        List<Track> videoTracks = new LinkedList<>();
        List<Track> audioTracks = new LinkedList<>();

        //---------------------------------

        Track audioTrack = audioMovie.getTracks().get(0);
        Track videoTrack = videoMovie.getTracks().get(0);

        //---------------------------------

        for (Track track : audioMovie.getTracks()) {
            if (track.getHandler().equals("soun")) {
                audioTracks.add(track);
            }
        }

        for (Track track : videoMovie.getTracks()) {
            if (track.getHandler().equals("vide")) {
                videoTracks.add(track);
            } else if (track.getHandler().equals("soun")) {
                audioTracks.add(track);
            }
        }

        //---------------------------------

        // List<Track> tracks = new LinkedList<>();
        // tracks.addAll(audioMovie.getTracks());
        // tracks.addAll(videoMovie.getTracks());

        //---------------------------------

        IsoFile videoFile = new IsoFile(videoPath);
        IsoFile audioFile = new IsoFile(audioPath);
        double videoSecs = (double) videoFile.getMovieBox().getMovieHeaderBox().getDuration() /
                videoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        double audioSecs = (double) audioFile.getMovieBox().getMovieHeaderBox().getDuration() /
                audioFile.getMovieBox().getMovieHeaderBox().getTimescale();

        Log.d(TAG, "muxVideoAndAudio: videoSecs=" + videoSecs);
        Log.d(TAG, "muxVideoAndAudio: audioSecs=" + audioSecs);

        if (videoSecs - audioSecs > 0) {// 视频轨道长
            Log.d(TAG, "muxVideoAndAudio: a");
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }

            double startTime1 = 0;
            double endTime1 = audioSecs;
            double startTime2 = audioSecs;
            double endTime2 = videoSecs;
            boolean timeCorrected = false;
            for (Track track : videoTracks) {
                if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
                    if (timeCorrected) {
                        throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
                    }
                    startTime1 = correctTimeToSyncSample(track, startTime1, false);
                    endTime1 = correctTimeToSyncSample(track, endTime1, true);
                    startTime2 = correctTimeToSyncSample(track, startTime2, false);
                    endTime2 = correctTimeToSyncSample(track, endTime2, true);
                    timeCorrected = true;
                }
            }

            long currentSample = 0;
            double currentTime = 0;
            double lastTime = -1;
            long startSample1 = -1;
            long endSample1 = -1;
            for (int i = 0; i < audioTrack.getSampleDurations().length; i++) {
                long delta = audioTrack.getSampleDurations()[i];
                if (currentTime > lastTime && currentTime <= startTime1) {
                    startSample1 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime1) {
                    endSample1 = currentSample;
                }
                lastTime = currentTime;
                currentTime += (double) delta / (double) audioTrack.getTrackMetaData().getTimescale();
                currentSample++;
            }
            currentSample = 0;
            currentTime = 0;
            lastTime = -1;
            long startSample2 = -1;
            long endSample2 = -1;
            for (int i = 0; i < videoTrack.getSampleDurations().length; i++) {
                long delta = videoTrack.getSampleDurations()[i];
                if (currentTime > lastTime && currentTime <= startTime2) {
                    startSample2 = currentSample;
                }
                if (currentTime > lastTime && currentTime <= endTime2) {
                    endSample2 = currentSample;
                }
                lastTime = currentTime;
                currentTime += (double) delta / (double) videoTrack.getTrackMetaData().getTimescale();
                currentSample++;
            }
            Log.d(TAG, "muxVideoAndAudio: " + startSample1 + "--" + endSample1);
            Log.d(TAG, "muxVideoAndAudio: " + startSample2 + "--" + endSample2);
            newMovie.addTrack(
                    new AppendTrack(
                            new CroppedTrack(audioTrack, startSample1, endSample1),
                            new CroppedTrack(videoTrack, startSample2, endSample2)));
        } else if (videoSecs - audioSecs < 0) {// 音频轨道长
            Log.d(TAG, "muxVideoAndAudio: b");
            for (Track track : audioTracks) {
                newMovie.addTrack(new CroppedTrack(track, 0, findNextSyncSample(track, videoSecs)));
            }
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }
        } else {
            Log.d(TAG, "muxVideoAndAudio: c");
            for (Track track : audioTracks) {
                newMovie.addTrack(track);
            }
            for (Track track : videoTracks) {
                newMovie.addTrack(track);
            }
        }

        return newMovie;
    }

    private static long findNextSyncSample(Track track, double cutHere) {
        long currentSample = 0;
        double currentTime = 0;
        long[] durations = track.getSampleDurations();
        long[] syncSamples = track.getSyncSamples();
        for (int i = 0; i < durations.length; i++) {
            long delta = durations[i];

            if ((syncSamples == null || syncSamples.length > 0 || Arrays.binarySearch(syncSamples, currentSample + 1) >= 0)
                    && currentTime > cutHere) {
                return i;
            }
            currentTime += (double) delta / (double) track.getTrackMetaData().getTimescale();
            currentSample++;
        }
        return currentSample;
    }

    /**
     * 视频音频合成
     */
    private static Movie muxVideoAndAudio_9(String h264Path, String aacPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(h264Path));
        AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(aacPath));
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
            Movie newMovie = extractVideo_1(srcPath);
            if (newMovie != null) {
                writeMovie_1(dstPath, newMovie);
            }

            Log.d(TAG, "extractVideo: true");
            printMovie(newMovie);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提取视频
     */
    private static Movie extractVideo_1(String srcPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie srcMovie = MovieCreator.build(srcPath);
        Movie dstMovie = new Movie();

        //---------------------------------

        // 第一步：有效
        List<Track> srcTracks = new LinkedList<>();
        for (Track track : srcMovie.getTracks()) {
            if (track.getHandler().equals("vide")) {
                srcTracks.add(track);
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
            Movie newMovie = extractAudio_1(srcPath);
            if (newMovie != null) {
                writeMovie_1(dstPath, newMovie);
            }

            Log.d(TAG, "extractAudio: true");
            printMovie(newMovie);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 提取音轨
     */
    private static Movie extractAudio_1(String srcPath) throws IOException {
        Log.d(TAG, "--------------------------------------------------------------------");
        Movie srcMovie = MovieCreator.build(srcPath);
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
     * @param path 保存的路径
     * @param movie 保存的视频
     */
    private static void writeMovie_1(String path, Movie movie) throws IOException {
        Container container = new DefaultMp4Builder().build(movie);
        FileOutputStream fos = new FileOutputStream(path);
        FileChannel fc = fos.getChannel();
        container.writeContainer(fc);
        fc.close();
        fos.close();
    }

    /**
     * 保存视频
     *
     * @param path 保存的路径
     * @param movie 保存的视频
     */
    private static void writeMovie_2(String path, Movie movie) throws IOException {
        Container container = new DefaultMp4Builder().build(movie);
        FileChannel fc = new RandomAccessFile(path, "rw").getChannel();
        container.writeContainer(fc);
        fc.close();
    }

    // ---------------------------------------------------------------------------------

    /**
     * 打印电影
     */
    private static void printMovie(Movie movie) {
        Log.d(TAG, "--------------------------------[Movie]------------------------------------");
        if (movie != null) {
            Log.d(TAG, "printTrack: movie=" + movie.toString());
            if (movie.getTracks() != null) {
                for (Track track: movie.getTracks()) {
                    printTrack(track);
                }
            }
        }
    }

    /**
     * 打印轨道
     */
    private static void printTrack(Track track) {
        Log.d(TAG, "--------------------------------[Track]------------------------------------");
        if (track != null) {
            Log.d(TAG, "printTrack: name=" + track.getName());
            Log.d(TAG, "printTrack: hanlder=" + track.getHandler());
            Log.d(TAG, "printTrack: duration=" + track.getDuration());
            if (track.getSamples() != null) {
                Log.d(TAG, "printTrack: samplesSize=" + track.getSamples().size());
            }
            if (track.getSampleDurations() != null) {
                Log.d(TAG, "printTrack: samplesDurationsLength=" + track.getSampleDurations().length);
            }
            if (track.getSyncSamples() != null) {
                Log.d(TAG, "printTrack: SyncSamplesLength=" + track.getSyncSamples().length);
            }
            // printBox(track.getMediaHeaderBox());
            // printBox(track.getSampleDescriptionBox());
            // printBox(track.getSubsampleInformationBox());
            /*
            for (Sample sample : track.getSamples()) {
                printSample(sample);
            }*/
        }
    }

    private static void printBox(Box box) {
        Log.d(TAG, "--------------------------------[Box]------------------------------------");
        if (box != null) {
            Log.d(TAG, "printTrack: box=" + box);
            Log.d(TAG, "printTrack: type=" + box.getType());
            Log.d(TAG, "printTrack: offSet=" + box.getOffset());
            Log.d(TAG, "printTrack: parent=" + box.getParent());
            Log.d(TAG, "printTrack: size=" + box.getSize());
        }
    }

    private static void printSample(Sample sample) {
        Log.d(TAG, "--------------------------------[Sample]------------------------------------");
        if (sample != null) {
            Log.d(TAG, "printTrack: sample=" + sample);
        }
    }
}
