package com.example.shirodemo.realm;

import com.example.shirodemo.entity.User;
import com.example.shirodemo.service.UserService;
import com.example.shirodemo.util.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhouzeqiang
 * @date 2019/11/15
 * @description
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 角色
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        // 权限
        // 测试用权限
        if ("admin".equals(username)) {
            roles.add("admin");
            permissions.add("op:write");
        } else {
            roles.add("user");
            permissions.add("op:read");
        }
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;

    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        User user = userService.findByUserName(userName);
        if(user == null){
            throw new UnknownAccountException("账号不存在");
        }

        //加密后的密码
        String pwdString = MD5Util.MD5Pwd(userPwd,userName+"salt");

        //根据用户名从数据库获取密码
        String password = user.getPassWord();
        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!pwdString.equals(password )) {
            throw new AccountException("密码不正确");
        }
        String salt = user.getUserName() + "salt";

        return new SimpleAuthenticationInfo(userName, password, ByteSource.Util.bytes(salt), getName());
    }
}
