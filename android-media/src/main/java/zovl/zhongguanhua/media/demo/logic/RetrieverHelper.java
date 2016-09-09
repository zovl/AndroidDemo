package zovl.zhongguanhua.media.demo.logic;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

public class RetrieverHelper {

    public static final String TAG  = RetrieverHelper.class.getSimpleName();

    // ---------------------------------------------------------------------------------
    
    private String path;
    private MediaMetadataRetriever retriever;

    public RetrieverHelper(String path) {
        this.path = path;

        Log.d(TAG, "RetrieverHelper: path=" + path);

        retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    // ---------------------------------------------------------------------------------

    /**
     * 获取本地视频信息
     */
    public void retrieve() {
        Log.d(TAG, "----------------------------------[retrieve]----------------------------------");
        
        String author = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR);
        String album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        String albumArtist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
        String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String bitRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        String frameRate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
        String trackNumber = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER);
        String complication = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPILATION);
        String cpmpose = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER);
        String date = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
        String discNumber = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DISC_NUMBER);
        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        String gender = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        String hasAudio = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
        String hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
        String location = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_LOCATION);
        String mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        String numTracks = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS);
        String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        String videoHeight = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String videoRotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
        String videoWidth = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String writer = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_WRITER);
        String year = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);

        Log.d(TAG, "author=" + author);
        Log.d(TAG, "album=" + album);
        Log.d(TAG, "albumArtist=" + albumArtist);
        Log.d(TAG, "artist=" + artist);
        Log.d(TAG, "bitRate=" + bitRate);
        Log.d(TAG, "frameRate=" + frameRate);
        Log.d(TAG, "trackNumber=" + trackNumber);
        Log.d(TAG, "complication=" + complication);
        Log.d(TAG, "cpmpose=" + cpmpose);
        Log.d(TAG, "date=" + date);
        Log.d(TAG, "discNumber=" + discNumber);
        Log.d(TAG, "duration=" + duration);
        Log.d(TAG, "gender=" + gender);
        Log.d(TAG, "hasAudio=" + hasAudio);
        Log.d(TAG, "hasVideo=" + hasVideo);
        Log.d(TAG, "location=" + location);
        Log.d(TAG, "mimeType=" + mimeType);
        Log.d(TAG, "numTracks=" + numTracks);
        Log.d(TAG, "title=" + title);
        Log.d(TAG, "videoHeight=" + videoHeight);
        Log.d(TAG, "videoWidth=" + videoWidth);
        Log.d(TAG, "videoRotation=" + videoRotation);
        Log.d(TAG, "writer=" + writer);
        Log.d(TAG, "year=" + year);
    }

    // ---------------------------------------------------------------------------------

    /**
     * 获取本地视频封面
     *
     * @param secs 秒
     */
    public Bitmap frameAtTime(long secs) {
        Log.d(TAG, "----------------------------------[frameAtTime]----------------------------------");
        Log.d(TAG, "frameAtTime: secs=" + secs);
        Bitmap bitmap = retriever.getFrameAtTime(secs * 1000 * 1000l);
        return bitmap;
    }

    /**
     * 获取本地视频封面
     *
     * @param secs 秒
     */
    public Bitmap frameAtTime(long secs, int option) {
        Log.d(TAG, "----------------------------------[frameAtTime]----------------------------------");
        Log.d(TAG, "frameAtTime: secs=" + secs);
        Log.d(TAG, "frameAtTime: option=" + option);
        Bitmap bitmap = retriever.getFrameAtTime(secs * 1000 * 1000l, option);
        return bitmap;
    }
}
