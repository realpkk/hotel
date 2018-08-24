package top.hotel.management.controller.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.hotel.management.entity.user.Role;
import top.hotel.management.entity.user.User;
import top.hotel.management.service.security.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/roleEdit")
    public void roleEdit(@RequestParam String phoneNumber,@RequestParam Long roleId){
        User user = userService.getCurrentUser(phoneNumber);
        userService.roleEdit(user,roleId);
    }
}
