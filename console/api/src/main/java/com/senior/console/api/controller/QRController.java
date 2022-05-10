package com.senior.console.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sjw
 * @Description
 * @Date 22:53 2022/5/1
 **/
@RestController
@Slf4j
@RequestMapping("/api/qr")
public class QRController {
    /**
     * 生成二维码内容
     *
     * @return 结果
     */
//    @GetMapping("/generate")
//    public BaseResult generate() {
//        String code = IdUtil.simpleUUID();
//        redisCache.setCacheObject(code, CodeUtils.getUnusedCodeInfo(),
//                DEFAULT_QR_EXPIRE_SECONDS, TimeUnit.SECONDS);
//        return BaseResult.success(GENERATE_SUCCESS, code);
//    }
}
