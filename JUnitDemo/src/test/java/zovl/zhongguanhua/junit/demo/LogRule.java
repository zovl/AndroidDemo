package zovl.zhongguanhua.junit.demo;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class LogRule implements TestRule {

    private Statement mBase;

    @Override
    public Statement apply(Statement base, Description description) {
        this.mBase = base;
        return new LogStatement(base);
    }

    public void print(String message){
        System.out.println("LogRule message is:" + message);
    }

    public class LogStatement extends Statement{

        private final Statement base;

        public LogStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            System.out.println("method evaluate before");
            try{
                base.evaluate();
            }finally {
                System.out.println("method evaluate after");
            }
        }
    }
}
