package top.hotel.management.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.hotel.management.common.bean.PageBean;
import top.hotel.management.common.bean.RoomInfoBean;
import top.hotel.management.common.enums.RoomStatusEnum;
import top.hotel.management.common.utils.DateUtil;
import top.hotel.management.entity.server.Record;
import top.hotel.management.entity.server.Room;
import top.hotel.management.repository.server.RoomRepository;

import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room getCurrentRoom(String roomNumber) {
        return roomRepository.findRoomByRoomNumber(roomNumber);
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    /**
     * 实时住房状态更新
     * @param room
     * @param checkInTime
     * @param checkOutTime
     * @return
     */
    public Room roomStatusCheck(Room room, Date checkInTime, Date checkOutTime) {
        List<Record> recordList = room.getRecordList();
        if (recordList.size() == 0) {
            room.setRoomStatus(RoomStatusEnum.VACANT.getKey());
        } else {
            for (Record record : recordList) {
                if (!(DateUtil.timeInterval(checkInTime, record.getCheckOutTime()) <= 0 || DateUtil.timeInterval(checkOutTime, record.getCheckInTime()) >= 0)) {
                    room.setRoomStatus(RoomStatusEnum.OCCUPIED.getKey());
                    room.setTenant(record.getRecordOwner());
                } else {
                    room.setRoomStatus(RoomStatusEnum.VACANT.getKey());
                }
            }
        }
        return room;
    }

    /**
     * 获取更新数据后的所有住房信息
     * @param checkInTime
     * @param checkOutTime
     * @return
     */
    private List<Room> roomStatusUpdateList(Date checkInTime, Date checkOutTime) {
        List<Room> roomList = new ArrayList<>();
        for (Room room : roomRepository.findAll()) {
            roomList.add(roomStatusCheck(room, checkInTime, checkOutTime));
        }
        return roomList;
    }

    /**
     * 根据查询条件过滤
     * @param roomType
     * @param roomStatus
     * @param checkInTime
     * @param checkOutTime
     * @return
     */
    private List<Room> filterList(Integer roomType, Integer roomStatus, Date checkInTime, Date checkOutTime) {
        List<Room> initList = roomStatusUpdateList(checkInTime, checkOutTime);
        List<Room> roomList = new ArrayList<>();
        if (null != roomType && null != roomStatus){
            for (Room room : initList) {
                if (room.getRoomType().equals(roomType) && room.getRoomStatus().equals(roomStatus)) {
                    roomList.add(room);
                }
            }
            return roomList;
        }else if(null != roomType){
            for (Room room : initList) {
                if (room.getRoomType().equals(roomType)) {
                    roomList.add(room);
                }
            }
            return roomList;
        }else if (null != roomStatus){
            for (Room room : initList) {
                if (room.getRoomStatus().equals(roomStatus)) {
                    roomList.add(room);
                }
            }
            return roomList;
        }else {
            return initList;
        }
    }

    /**
     * 自定义分页
     * @param pageNumber
     * @param pageSize
     * @param roomList
     * @return
     */
    public PageBean<Room> page(int pageNumber, int pageSize, List<Room> roomList) {
        List<Room> content = new ArrayList<>();
        int beginNum = pageSize * pageNumber;
        int endNum = pageSize * (pageNumber + 1);
        for (; beginNum < endNum; beginNum++) {
            content.add(roomList.get(beginNum));
        }
        int totalPages = roomList.size() / pageSize + 1;
        return new PageBean(pageNumber, pageSize, totalPages, content.size(), content);
    }


//    public RoomInfoBean roomStatusCheck(String roomNumber, Date checkInTime, Date checkOutTime){
//        RoomInfoBean bean = new RoomInfoBean();
//        Room room = getCurrentRoom(roomNumber);
//        List<Record> recordList = room.getRecordList();
//        if (recordList.size() == 0){
//            bean.setRoomStatus(true);
//        }else{
//            for (Record record:recordList){
//                if (!(DateUtil.timeInterval(checkInTime,record.getCheckOutTime())<=0 || DateUtil.timeInterval(checkOutTime,record.getCheckInTime())>=0)){
//                    bean.setRoomStatus(false);
//                    bean.setTenant(record.getRecordOwner());
//                }else {
//                    bean.setRoomStatus(true);
//                }
//            }
//        }
//        return bean;
//    }

//    public PageBean<Room> getRooms(Date checkInTime, Date checkOutTime, Pageable pageable){
//        Page<Room> originPage = roomRepository.findAll(pageable);
//        List<Room> roomList = new ArrayList<>();
//        for (Room room:originPage.getContent()){
//            RoomInfoBean bean = roomStatusCheck(room,checkInTime,checkOutTime);
//            if (bean.isRoomStatus()){
//                room.setRoomStatus(RoomStatusEnum.VACANT.getKey());
//            }else {
//                room.setRoomStatus(RoomStatusEnum.OCCUPIED.getKey());
//                room.setTenant(bean.getTenant());
//            }
//            roomList.add(room);
//        }
//        PageBean<Room> page = new PageBean<Room>
//                (originPage.getSize(),
//                originPage.getNumber(),
//                originPage.getTotalPages(),
//                originPage.getTotalElements(),
//                roomList);
//        return page;
//    }

    /**
     *  查询结果
     * @param roomType
     * @param roomStatus
     * @param checkInTime
     * @param checkOutTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageBean<Room> roomInquiry(Integer roomType, Integer roomStatus, Date checkInTime, Date checkOutTime, int pageNumber, int pageSize) {
        List<Room> roomList = filterList(roomType, roomStatus, checkInTime, checkOutTime);
        return page(pageNumber, pageSize, roomList);
    }

    /**
     *  初始数据
     * @param checkInTime
     * @param checkOutTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageBean<Room> getRooms(Date checkInTime, Date checkOutTime, int pageNumber, int pageSize) {
        List<Room> roomList = roomStatusUpdateList(checkInTime,checkOutTime);
        return page(pageNumber,pageSize,roomList);
    }
}