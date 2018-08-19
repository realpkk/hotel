package top.hotel.management.controller.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.hotel.management.common.utils.MD5Util;
import top.hotel.management.entity.security.Customer;
import top.hotel.management.service.security.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam String phoneNumber,
                        @RequestParam String password){
        if (userService.loginCheck(phoneNumber,password)) {
            return "index";
        }else {
            model.addAttribute("message","手机号或密码错误");
            return "login";
        }

    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/register")
    public String register(Model model,@RequestParam String phoneNumber,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String identityCode){
        String dateBasePassword = MD5Util.encode(password);
        Customer customer = new Customer(phoneNumber,name,identityCode,dateBasePassword);
        if (userService.duplicateCheck(phoneNumber)) {
            model.addAttribute("message","手机号已注册");
            return "register";
        }else {
            userService.createNewUser(customer);
            return "login";
        }
    }
}
