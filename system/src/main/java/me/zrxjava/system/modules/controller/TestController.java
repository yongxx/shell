package me.zrxjava.system.modules.controller;

import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zrxjava.common.annotation.AccessLimit;
import me.zrxjava.common.annotation.RedisLock;
import me.zrxjava.common.base.ResponseResult;
import me.zrxjava.system.modules.service.IUserService;
import me.zrxjava.system.modules.vo.UserVo;
import me.zrxjava.system.support.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author void
 * @create 2020-09-18
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "测试",tags = "测试服务")
public class TestController {

    @Autowired
    private IUserService userService;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/1")
    @AccessLimit(perSecond = 0.1,timeOut = 3000)
    public ResponseResult test(String name){
        log.info("进入test/1方法，name：{}",name);
        return ResponseResult.success(name);
    }

    @GetMapping("/2")
    @RedisLock(key = "#name",lockTime = 3000)
    public ResponseResult test1(String name){
        log.info("进入test/2方法，name：{}",name);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseResult.success(name);
    }

    @GetMapping("/cursorList")
    @ApiOperation("大数据用户流式查询")
    public ResponseResult<List<UserVo>> cursorList() throws IOException {
        return ResponseResult.success(userService.cursorList());
    }


    public static void main(String[] args) {
//        User old = User.builder().username("张三").userId(123L).build();
//        User fast = User.builder().username("李四").userId(234L).build();
//        String log = BeanChangeUtils.contrastObj(old,fast,null);
//        System.out.println(log);

        System.out.println(RandomUtil.randomString("hjvbytimhmgkqweasdsawetwr",14));

    }
}
