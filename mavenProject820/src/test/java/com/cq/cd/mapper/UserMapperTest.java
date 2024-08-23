//package com.cq.cd.mapper;
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.cq.cd.pojo.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootTest
//
//public class UserMapperTest {
//    //自动装配
//    @Autowired
//    public UserMapper userMapper;
//    //获取用户信息
//    @Test
//    public void getUserInfo(){
//        List<User> users=userMapper.selectList(null);
//        System.out.println(users);
//    }
//
//    @Test
//    public void UpdateUserInfo(){
//        User updateUser=new User();
//        updateUser.setId(1);
//        updateUser.setEmail("New-test1@baomidou.com");
//        int rows=userMapper.updateById(updateUser);
//        if (rows > 0) {
//            System.out.println("User updated successfully.");
//        } else {
//            System.out.println("No user updated.");
//        }
//    }
//    @Test
//    public void DeleteUserInfo(){
//        int id=1;
//        int rows=userMapper.deleteById(id);
//        if (rows > 0) {
//            System.out.println("User deleted successfully.");
//        }else {
//            System.out.println("No user deleted.");
//        }
//    }
//    @Test
//    public void InsertUserInfo(){
//        User user=new User();
//        user.setId(1);
//        user.setName("HaJiMi");
//        user.setAge(18);
//        user.setEmail("test1@SaiManian.com");
//        int rows = userMapper.insert(user);
//        if (rows > 0) {
//            System.out.println("User inserted successfully.");
//        } else {
//            System.out.println("Failed to insert user.");
//        }
//    }
//    @Test
//    public void getUserInfoById(){
//        User user=userMapper.selectById(null);
//        System.out.println(user);
//    }
//    @Test
//    public void QueryWrapper(){
//        QueryWrapper<User> wrapper=new QueryWrapper<User>()
//                .select("id","name","age","email")
//                .ge("age",17)
//                .like("email","b");
//        List<User> users=userMapper.selectList(wrapper);
//        System.out.println(users);
//    }
//    @Test
//    public void UpdateWrapper(){
//        User user=new User();
////        user.setEmail("Manbo@Saimaniang.com");
//        user.setAge(25);
//        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
//                .eq("name","HaJiMi");
//        userMapper.update(user, wrapper);
//    }
//    @Test
//    public void UpdateWrapper2(){
//        ArrayList<Integer> ids=new ArrayList<>(Arrays.asList(1,2,4));
//        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
//                .setSql("age=age+22")
//                .in("id",ids);
//        userMapper.update(null, wrapper);
//    }
//    @Test
//    public void updateByMySql(){
//        int minusAge=2;
//        ArrayList<Integer> ids=new ArrayList<>(Arrays.asList(1,2,4));
//        UpdateWrapper<User> wrapper = new UpdateWrapper<User>().in("id",ids);
//        userMapper.updateByAge(wrapper,minusAge);
//    }
////    @Test
////    public void regiser(){
////
////    }
//
//}
