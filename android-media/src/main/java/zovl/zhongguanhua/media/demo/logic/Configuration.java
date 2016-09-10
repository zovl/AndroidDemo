package zovl.zhongguanhua.media.demo.logic;

import java.io.Serializable;

/**
 * 录屏配置
 */
public class Configuration implements Serializable {

    // 4.4/5.0
    //           标清         高清                             超高清
    // 比特率：1 200 000    2 000 000                    2 500 000(3 000 000)
    // 分辨率： 768*432      1280*720                         1280*720

    // < 4.4
    //           流畅         标清           高清
    // 比特率：1 000 000    1 200 000      1 500 000(2 000 000)
    // 分辨率： 320*640      768*432        1280*720
    // 屏幕/：     2           1.7            1.4
    // fps：      12           15             15

    public static final int QUALITY_HIGH_WIDTH = 720;
    public static final int QUALITY_HIGH_HEIGHT = 1280;
    public static final int QUALITY_STANDARD_WIDTH = 432;
    public static final int QUALITY_STANDARD_HEIGHT = 768;
    public static final int QUALITY_LOW_WIDTH = 360;
    public static final int QUALITY_LOW_HEIGHT = 640;

    public static final int QUALITY_LOW_BITRATE = 10 * 100 * 1000;
    public static final int QUALITY_STANDARD_BITRATE = 12 * 100 * 1000;
    public static final int QUALITY_HIGH_BITRATE = 20 * 100 * 1000;
    public static final int QUALITY_ULTRAHIGH_BITRATE = 25 * 100 * 1000;

    public static final int QUALITY_LOW_FPS = 12;
    public static final int QUALITY_STANDARD_FPS  = 15;
    public static final int QUALITY_HIGH_FPS  = 20;

    public static final Configuration DEFAULT = new Configuration(1, Quality.HIGH, true, false);

    private int width;
    private int height;
    private int bitrate;
    private int dpi;
    private int fps;

    private Quality quality;
    private boolean audio;
    private boolean landscape;

    public enum Quality {
        ULTRAHIGH, HIGH, STANDARD, LOW
    }

    private Configuration() {
        super();
    }

    public Configuration(int dpi, Quality quality, boolean audio, boolean landscape) {
        this.dpi = dpi;
        this.quality = quality;
        this.audio = audio;
        this.landscape = landscape;
    }

    public int getWidth() {
        if (quality == Quality.ULTRAHIGH) {
            width = QUALITY_HIGH_WIDTH;
        } else if (quality == Quality.HIGH) {
            width = QUALITY_HIGH_WIDTH;
        } else if (quality == Quality.STANDARD) {
            width = QUALITY_STANDARD_WIDTH;
        } else {
            width = QUALITY_LOW_WIDTH;
        }
        return width;
    }

    public int getHeight() {
        if (quality == Quality.ULTRAHIGH) {
            height = QUALITY_HIGH_HEIGHT;
        } else if (quality == Quality.HIGH) {
            height = QUALITY_HIGH_HEIGHT;
        } else if (quality == Quality.STANDARD) {
            height = QUALITY_STANDARD_HEIGHT;
        } else {
            height = QUALITY_LOW_HEIGHT;
        }
        return height;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getFps() {
        if (quality == Quality.ULTRAHIGH) {
            fps = QUALITY_HIGH_FPS;
        } else if (quality == Quality.HIGH) {
            fps = QUALITY_HIGH_FPS;
        } else if (quality == Quality.STANDARD) {
            fps = QUALITY_STANDARD_FPS;
        } else {
            fps = QUALITY_LOW_FPS;
        }
        return fps;
    }

    public int getBitrate() {
        if (quality == Quality.ULTRAHIGH) {
            bitrate = QUALITY_ULTRAHIGH_BITRATE;
        } else if (quality == Quality.HIGH) {
            bitrate = QUALITY_HIGH_BITRATE;
        } else if (quality == Quality.STANDARD) {
            bitrate = QUALITY_STANDARD_BITRATE;
        } else {
            bitrate = QUALITY_LOW_BITRATE;
        }
        return bitrate;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public static class Builder {

        private Configuration configuration = Configuration.DEFAULT;

        public Builder() {
            super();

        }

        public Builder dpi(int dpi) {
            configuration.setDpi(dpi);
            return this;
        }

        public Builder quality(Quality quality) {
            configuration.setQuality(quality);
            return this;
        }

        public Builder audio(boolean audio) {
            configuration.setAudio(audio);
            return this;
        }

        public Builder landscape(boolean landscape) {
            configuration.setLandscape(landscape);
            return this;
        }

        public Configuration build() {
            return configuration;
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "width=" + getWidth() +
                ", height=" + getHeight() +
                ", bitrate=" + getBitrate() +
                ", dpi=" + getDpi() +
                ", fps=" + getFps() +
                ", quality=" + getQuality() +
                ", audio=" + audio +
                ", landscape=" + landscape +
                '}';
    }
}
