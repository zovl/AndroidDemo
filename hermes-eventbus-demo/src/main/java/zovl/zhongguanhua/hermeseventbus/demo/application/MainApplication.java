package zovl.zhongguanhua.hermeseventbus.demo.application;

import xiaofei.library.hermeseventbus.HermesEventBus;
import zovl.zhongguanhua.framework.lib.framework.BaseApplication;

/**
 * 应用:主应用程序
 */
public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        HermesEventBus.getDefault().init(this);
    }
}
