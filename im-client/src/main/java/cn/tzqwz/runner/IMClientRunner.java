package cn.tzqwz.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 客户端启动时处理的业务逻辑
 */
@Component
public class IMClientRunner implements ApplicationRunner {
    /**
     *客户端启动后登陆IM
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
