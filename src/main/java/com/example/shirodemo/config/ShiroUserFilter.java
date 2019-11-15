package com.example.shirodemo.config;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ShiroUserFilter extends UserFilter {
 
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        super.redirectToLogin(request, response);

        if(this.getName().equals("anno")){

        }


        response.setContentType("application/json; charset=utf-8");//返回json
        response.getWriter().write(JSON.toJSONString("用户未登录，请先登录"));
    }
}