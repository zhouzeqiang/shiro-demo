package com.example.shirodemo.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouzeqiang
 * @date 2019/11/15
 * @description
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(value = "/login")
    public String defaultLogin() {
        return "首页";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            return "用户名或密码不正确！";
        }
        if (subject.isAuthenticated()) {
            return "登录成功";
        } else {
            token.clear();
            return "登录失败";
        }
    }

    @GetMapping("/read")
    @RequiresRoles(value = {"user","admin"},logical= Logical.OR)
    @RequiresPermissions("op:read")
    public JSONObject testRead(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("read","this is read ");

        return jsonObject;
    }

    @GetMapping("/write")
    @RequiresRoles("admin")
    @RequiresPermissions("op:write")
    public JSONObject testWrite(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("write","this is write ");

        return jsonObject;
    }


    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @GetMapping(value = "/unauth")
    public Object unauth() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }
}
