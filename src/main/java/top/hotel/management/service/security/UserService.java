package top.hotel.management.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hotel.management.common.utils.MD5Util;
import top.hotel.management.entity.user.User;
import top.hotel.management.repository.user.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(String phoneNumber){
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public void createNewUser(User user){
        userRepository.save(user);
    }

    public boolean duplicateCheck(String phoneNumber){
        User user = getCurrentUser(phoneNumber);
        if (user == null) {
            return false;
        }else {
            return true;
        }
    }

    public boolean loginCheck(String phoneNumber,String password){
        User user = getCurrentUser(phoneNumber);
        if (MD5Util.encode(password).equals(user.getPassword())) {
            return true;
        }else {
            return false;
        }
    }
}


