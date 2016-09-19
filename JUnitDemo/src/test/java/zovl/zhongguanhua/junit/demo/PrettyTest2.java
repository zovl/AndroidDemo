package zovl.zhongguanhua.junit.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class PrettyTest2 {
    //-------初始化代码省略-------
    @Parameterized.Parameter
    public int testNum;

    private static PrettyTest mTest = new PrettyTest();

    @Parameterized.Parameters
    public static Collection<Integer> initData() {
        List<Integer> data = new ArrayList<>();
        data.add(-1);
        data.add(0);
        data.add(1);
        return data;
    }

    @Test
    public void test(){
        String resultStr = mTest.print(testNum);
        System.out.println("==========testPrintBelow result string is:"+ resultStr + "==========");
        if (testNum > 0){
            //-------判断是否与预期值相同-------
        }else{
            //-------判断是否与预期值相同-------
        }
    }
}
