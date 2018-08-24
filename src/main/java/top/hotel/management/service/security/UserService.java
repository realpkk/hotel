package top.hotel.management.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hotel.management.common.utils.MD5Util;
import top.hotel.management.entity.user.Role;
import top.hotel.management.entity.user.User;
import top.hotel.management.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final long DEFAULT_ROLE = 2;

    public User getCurrentUser(String phoneNumber){
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public void createNewUser(User user){
        roleEdit(user,DEFAULT_ROLE);
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

    public void roleEdit(User user,Long roleId){
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        if (null != roleList){
            role.setId(roleId);
            roleList.add(role);
        }
        user.setRoleList(roleList);
        userRepository.save(user);
    }
}


