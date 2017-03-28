package zovl.zhongguanhua.junit.demo;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class RuleTester {

    private static File testFile = null;

    @Rule
    public TemporaryFolder mFolder = new TemporaryFolder();

    @Before
    public void before() {
        System.out.println("----------method before testFile is: " + testFile + "----------");
        Assert.assertNull(testFile);
    }

    @Test
    public void test() {
        try {
            testFile = mFolder.newFile("myfile.txt");
            boolean flag = testFile.exists();
            System.out.println("----------method test testFile exists flag: " + flag + "----------");
            Assert.assertTrue(flag);
        } catch (IOException e) {
            Assert.fail("exception is:" + e.getMessage());
        }
    }

    @AfterClass
    public static void after() {
        boolean flag = testFile.exists();
        System.out.println("----------method after testFile exists flag: " + flag + "----------");
        Assert.assertFalse(flag);
    }
}
