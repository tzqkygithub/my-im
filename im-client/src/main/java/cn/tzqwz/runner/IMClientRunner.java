package cn.tzqwz.runner;

import cn.tzqwz.im.client.IMClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 客户端启动时处理的业务逻辑
 */
@Component
public class IMClientRunner implements ApplicationRunner {

    @Autowired
    private IMClient imClient;

    /**
     *客户端启动后登陆IM
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread( new Runnable(){

            @Override
            public void run() {
                imClient.start();
            }
        }).start();
    }
}
