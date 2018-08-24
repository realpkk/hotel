package top.hotel.management.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.hotel.management.entity.user.Menu;
import top.hotel.management.repository.base.AbstractRepository;

import java.util.List;

@Repository
public interface MenuRepository extends AbstractRepository<Menu,Long> {

    @Query("select m from Role r join r.menuList m where r.id in :roleIdList")
    List<Menu> findMenusByRoleIdIn(@Param("roleIdList") List<Long> roleIdList);

    List<Menu> findMenusByParentId(Long parentId);
}
