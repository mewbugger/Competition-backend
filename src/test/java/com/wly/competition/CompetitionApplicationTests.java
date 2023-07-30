package com.wly.competition;

import com.wly.competition.model.domain.User;
import com.wly.competition.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class CompetitionApplicationTests {

    @Resource
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSearchUserByTags(){
        List<String> tags = Arrays.asList("Java","Python");
        //SQL:SELECT id,userName,userAccount,avatarUrl,gender,userPassword,phone,email,userStatus,createTime,updateTime,isDelete,userRole,tags FROM user WHERE isDelete=0 AND (tags LIKE ? AND tags LIKE ?)
        List<User> userList = userService.searchUsersByTags(tags);
        System.out.println(userList);
    }

    @Test
    void testLeetCode(){
        int[] ratings = {1,0,2};
        //数组元素默认为0
        int[] candies = new int[ratings.length];
        Arrays.fill(candies, 1);  // 将糖果数量初始化为1
        int res = 0;
        for(int i = 1; i < ratings.length; i++){
            //右孩子比左孩子大
            if(candies[i] > candies[i - 1]) candies[i] = candies[i - 1] + 1;
        }
        for(int i = ratings.length - 2; i >= 0; i--){
            //左孩子比右孩子大
            if(ratings[i] > ratings[i + 1])
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
        for(int i = 0; i < candies.length; i++){
            res += candies[i];
            System.out.println(candies[i]);
        }
        System.out.println(res);
    }

}
