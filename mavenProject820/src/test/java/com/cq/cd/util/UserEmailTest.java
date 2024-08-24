package com.cq.cd.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserEmailTest {
    @Autowired
    private EmailSendUtils emailSendUtil;

    @Test
    public void sendMessage(){
//        Map<String, Object> map = new HashMap<>();
//        map.put("content", "-------This is a test to send Email---------");
//        EmailDTO emailDTO = EmailDTO.builder()
//                .template("templates/common.html")
//                .email("2889983928@qq.com")
//                .subject("TestEmail")
//                .commentMap(map).build();
//        CompletableFuture.runAsync(()-> emailSendUtil.sendHtmlMail(emailDTO));
//    }
}
