package zovl.zhongguanhua.junit.demo;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrettyTest1 {

    private static PrettyTest mTest;
    private static String expectedStrAbove;
    private static String expectedStrBelow;

    @BeforeClass
    public static void create(){
        mTest = new PrettyTest();
        expectedStrAbove = "大";
        expectedStrBelow = "小";
    }

    @Test
    public void testPrintAbove(){
        int i = 1;
        String resultStr = mTest.print(i);
        System.out.println("==========testPrintBelow result string is:"+ resultStr + "==========");
        Assert.assertEquals(expectedStrAbove,resultStr);
        Assert.assertNotEquals(expectedStrBelow,resultStr);
    }

    @Test
    public void testPrintBelow(){
        int i = -1;
        String resultStr = mTest.print(i);
        System.out.println("==========testPrintBelow result string is:"+ resultStr + "==========");
        Assert.assertEquals(expectedStrBelow,resultStr);
        Assert.assertNotEquals(expectedStrAbove,resultStr);
    }
}
