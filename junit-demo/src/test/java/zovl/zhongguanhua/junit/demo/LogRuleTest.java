package zovl.zhongguanhua.junit.demo;

import org.junit.Rule;
import org.junit.Test;

public class LogRuleTest {

    @Rule
    public LogRule mLogRule= new LogRule();

    @Test
    public void test(){
        String message = "method test";
        mLogRule.print(message);
    }
}
