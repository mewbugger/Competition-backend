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
        int[] heights = {2,1,5,6,2,3};
        largestRectangleArea(heights);
    }

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        int res = heights[0];
        //当前搜索范围内最低柱子的索引
        int low = heights[0];
        int startIndex = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for(int i = 1; i < len; i++){
            low = heights[startIndex];
            for(int j = startIndex; j <= i; j++){
                low = Math.min(low, heights[j]);
            }
            res = Math.max(res, low * (i - startIndex + 1));
            while(!stack.isEmpty() && heights[i] > res ){
                stack.pop();
            }
            if(stack.isEmpty()){
                startIndex = i;
            }
            res = Math.max(res, heights[i]);
            stack.push(i);
        }
        return res;
    }

}
