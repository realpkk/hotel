package top.hotel.management.repository.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.hotel.management.entity.server.Room;
import top.hotel.management.repository.base.AbstractRepository;

import java.util.Date;
import java.util.List;

public interface RoomRepository extends AbstractRepository<Room,Long> {

    Room findRoomByRoomNumber(String roomNumber);

}
