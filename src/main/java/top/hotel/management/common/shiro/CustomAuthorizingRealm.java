package top.hotel.management.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.hotel.management.entity.user.Menu;
import top.hotel.management.entity.user.User;
import top.hotel.management.service.security.MenuService;
import top.hotel.management.service.security.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

public class CustomAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @PostConstruct
    public void initCredentialsMatcher(){
        setCredentialsMatcher(new CustomCredentialsMatcher());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String phoneNumber = (String) principalCollection.getPrimaryPrincipal();
        User currentUser = userService.getCurrentUser(phoneNumber);
        List<Menu> menuList = menuService.findMenuTreeFromUser(currentUser);
        for (Menu menu:menuList){
            if (StringUtils.isNotEmpty(menu.getPermission())){
                authorizationInfo.addStringPermission(menu.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String phoneNumber = usernamePasswordToken.getUsername();
        User currentUser = userService.getCurrentUser(phoneNumber);
        if (currentUser != null) {
            return new SimpleAuthenticationInfo(currentUser.getPhoneNumber(),currentUser.getPassword(),getName());
        }else {
            return null;
        }
    }
}
