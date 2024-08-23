package com.cq.cd.service;

import com.cq.cd.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    public UserService userService;
    @Test
    public void getUserListTest(){
        List<User> list=userService.list();
        System.out.println(list);
    }
}
