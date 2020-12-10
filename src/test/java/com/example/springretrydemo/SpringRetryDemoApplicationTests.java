package com.example.springretrydemo;

import com.example.springretrydemo.service.ThirdPlatformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringRetryDemoApplicationTests {

    @Autowired
    private ThirdPlatformService thirdPlatformService;

    @Test
    public void testSendRequest() throws Exception {
        thirdPlatformService.sendRequest();
    }
}
