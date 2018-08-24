package top.hotel.management.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hotel.management.entity.user.Menu;
import top.hotel.management.entity.user.Role;
import top.hotel.management.entity.user.User;
import top.hotel.management.repository.user.MenuRepository;
import top.hotel.management.repository.user.UserRepository;

import java.util.*;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Menu> findMenuFromUser(User currentUser) {
        List<Long> roleIdList = new ArrayList<>();
        if (currentUser.getRoleList() != null) {
            for (Role role : currentUser.getRoleList()) {
                roleIdList.add(role.getId());
            }
        }
        if (roleIdList.size() > 0) {
            List<Menu> menuList = menuRepository.findMenusByRoleIdIn(roleIdList);
            Collections.sort(menuList);
            return menuList;
        }
        return new ArrayList<>();
    }

    public List<Menu> findMenuTreeFromUser(User currentUser) {
        if (currentUser == null) {
            return new ArrayList<>();
        }
        List<Menu> sourceList = findMenuFromUser(currentUser);
        return findMenuTree(sourceList);
    }

    public List<Menu> findMenuTree(List<Menu> sourceList) {
        Map<Long, Menu> menuMap = new HashMap<>();
        while (true) {
            if (sourceList == null || sourceList.isEmpty()) {
                break;
            }
            for (Menu menu : sourceList) {
                menuMap.put(menu.getId(), menu);
            }
            List<Long> idList = new ArrayList<>();
            for (Menu menu : sourceList) {
                if (menu.getParentId() != null && !menuMap.keySet().contains(menu.getParentId()) && !idList.contains(menu.getParentId())) {
                    idList.add(menu.getParentId());
                }
            }
            if (idList.size() > 0) {
                sourceList = menuRepository.findAllById(idList);
                continue;
            }
            break;
        }
        List<Menu> menuList = new ArrayList<>(menuMap.values());
        Collections.sort(menuList);
        return menuList;
    }

    public List<Menu> getParentMenuFromUser(User currentUser) {
        List<Menu> menuList = findMenuTreeFromUser(currentUser);
        List<Menu> parentMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getType() == 1 && menu.getParentId() == null) {
                parentMenuList.add(menu);
            }
        }
        return parentMenuList;
    }

    public List<Menu> getMenuTreeFromUser(User currentUser) {
        List<Menu> parentMenuList = getParentMenuFromUser(currentUser);
        for (Menu parentMenu : parentMenuList) {
            parentMenu.setMenuList(menuRepository.findMenusByParentId(parentMenu.getId()));
        }
        return parentMenuList;
    }
}
