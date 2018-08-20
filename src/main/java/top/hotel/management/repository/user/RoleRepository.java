package top.hotel.management.repository.user;

import org.springframework.stereotype.Repository;
import top.hotel.management.entity.user.Role;
import top.hotel.management.repository.base.AbstractRepository;

@Repository
public interface RoleRepository extends AbstractRepository<Role,Long> {
}
