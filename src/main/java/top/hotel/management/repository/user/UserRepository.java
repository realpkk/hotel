package top.hotel.management.repository.user;

import top.hotel.management.entity.user.User;
import top.hotel.management.repository.base.AbstractRepository;

public interface UserRepository extends AbstractRepository<User,Long> {

    User findUserByPhoneNumber(String phoneNumber);
}
