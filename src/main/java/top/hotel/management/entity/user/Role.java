package top.hotel.management.entity.user;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.hotel.management.entity.base.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_rel_role",joinColumns = {@JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Fetch(FetchMode.SUBSELECT)
    private List<User> userList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_rel_menu",joinColumns = {@JoinColumn(name = "role_id")},inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Menu> menuList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
