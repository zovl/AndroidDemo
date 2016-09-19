package zovl.zhongguanhua.junit.demo;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category({ ICategories.First.class, ICategories.Second.class })
public class CategoriesB {
    @Test
    public void c() {
        System.out.println("------class CategoriesB method c called------");
    }
}
