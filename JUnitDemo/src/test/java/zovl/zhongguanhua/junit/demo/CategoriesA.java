package zovl.zhongguanhua.junit.demo;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CategoriesA {
    @Test
    public void a() {
        System.out.println("------class CategoriesA method a called------");
    }

    @Category(ICategories.First.class)
    @Test
    public void b() {
        System.out.println("------class CategoriesA method b called------");
    }
}