package com.example.springretrydemo.service;

import com.example.springretrydemo.util.MyOkHttp3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.time.LocalTime;

@Slf4j
@Service
public class ThirdPlatformService {

    @Retryable(value = ConnectException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public void sendRequest() throws Exception {
        log.info("执行时间" + LocalTime.now());
        String url = "https://gw.vipapis.com";
        String json = "{}";
        MyOkHttp3Util.postJson(url, json);
    }

    /**
     * 可选，重试都失败后执行
     * @param e
     */
    @Recover
    public void recover(ConnectException e) {
        log.info("发送请求失败！！！" + LocalTime.now());
        return;
    }
}
