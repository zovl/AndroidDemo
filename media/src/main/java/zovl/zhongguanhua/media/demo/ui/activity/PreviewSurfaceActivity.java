package zovl.zhongguanhua.media.demo.ui.activity;

import butterknife.Bind;
import zovl.zhongguanhua.framework.lib.framework.TBaseActivity;
import zovl.zhongguanhua.media.demo.R;
import zovl.zhongguanhua.media.demo.logic.PreviewSurfaceView;

public class PreviewSurfaceActivity extends TBaseActivity {

    @Bind(R.id.surface)
    PreviewSurfaceView surfaceView;

    @Override
    public int getContentView() {
        return R.layout.activity_previewsurface;
    }
}
