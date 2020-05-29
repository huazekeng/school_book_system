package com.mrli.school_book;

import com.mrli.school_book.security.JwtUser;
import com.mrli.school_book.util.RedisUtils;

public class RedisTest {

    // @Autowired
    private RedisUtils redisUtils;

    // @Test
    public void set(){
        redisUtils.set("test","測試");
    }

    // @Test
    public void get() { System.out.println(((JwtUser)redisUtils.get("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJyb2xlIjoiUk9MRV9VU0VSLFJPTEVfTEVBREVSIiwibmlja19uYW1lIjoiREFTREExMjEiLCJpc3MiOiJHWUIiLCJleHAiOjE1ODA1NjQ1OTgsImlhdCI6MTU4MDU2Mjc5OCwianRpIjoiYWRtaW4ifQ.Uri8KTCtv-_OLBqC2q0lVWG24JH58asvGQAzilOQtq4")).getUsername());}

}
