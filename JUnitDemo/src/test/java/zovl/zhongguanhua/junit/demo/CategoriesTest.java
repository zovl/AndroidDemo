package zovl.zhongguanhua.junit.demo;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(ICategories.First.class)
@Categories.ExcludeCategory(ICategories.Second.class)
@Suite.SuiteClasses({ CategoriesA.class, CategoriesB.class })
public class CategoriesTest {}
