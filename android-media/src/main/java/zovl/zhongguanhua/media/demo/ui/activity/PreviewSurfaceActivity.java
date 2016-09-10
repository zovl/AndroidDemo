package zovl.zhongguanhua.media.demo.ui.activity;

import butterknife.Bind;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.PreviewSurfaceView;

/**
 * 活动：相机预览（回调处理图片）
 */
public class PreviewSurfaceActivity extends TBaseActivity {

    @Bind(R.id.surface)
    PreviewSurfaceView surfaceView;

    @Override
    public int getContentView() {
        return R.layout.activity_previewsurface;
    }
}
