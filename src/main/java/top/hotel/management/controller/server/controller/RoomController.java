package top.hotel.management.controller.server.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.hotel.management.common.bean.OrderInfoBean;
import top.hotel.management.common.bean.ResultBean;
import top.hotel.management.common.bean.RoomCheckBean;
import top.hotel.management.common.bean.RoomInfoBean;
import top.hotel.management.common.enums.PromptMessageEnum;
import top.hotel.management.common.enums.RoomStatusEnum;
import top.hotel.management.common.enums.RoomTypeEnum;
import top.hotel.management.common.utils.CodeUntil;
import top.hotel.management.common.utils.DateUtil;
import top.hotel.management.common.utils.PriceCalculateUtil;
import top.hotel.management.entity.server.Record;
import top.hotel.management.entity.server.Room;
import top.hotel.management.service.security.UserService;
import top.hotel.management.service.server.RecordService;
import top.hotel.management.service.server.RoomService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    private final String DEFAULT_TIME_RANGE = DateUtil.getDate()+" - "+DateUtil.getTomorrowDate();
    private final int DEFAULT_PAGE_NUMBER = 0;
    private final int DEFAULT_PAGE_SIZE = 10;

    /**
     *  枚举类封装
     * @param model
     */
    public void setInfoModel(Model model){
        model.addAttribute("roomTypeEnum", RoomTypeEnum.values());
        model.addAttribute("roomStatusEnum", RoomStatusEnum.values());
    }

    /**
     *  房间信息展示
     * @param model
     * @return
     */
    @RequestMapping("/show")
    public String showRoom(Model model){
        setInfoModel(model);
        Date[] range = DateUtil.timeRangeAnalyze(DEFAULT_TIME_RANGE);
        model.addAttribute("page",roomService.getRooms(range[0],range[1],DEFAULT_PAGE_NUMBER,DEFAULT_PAGE_SIZE));
        return "room";
    }

    /**
     *  住房添加
     * @param room
     */
    @RequiresPermissions("room:add")
    @RequestMapping("/add")
    public void addRoom(@RequestBody Room room){
        roomService.addRoom(room);
    }

    /**
     *  住房编辑
     * @param room
     */
    @RequiresPermissions("room:edit")
    @RequestMapping("{roomNumber}/edit")
    public void editRoom(@RequestBody Room room){

    }

    /**
     *  (非预定)入住管理
     * @param identityCode
     */
    @RequiresPermissions("room:check")
    @RequestMapping("{identityCode}/checkIn")
    public void roomCheckIn(@PathVariable(name = "identityCode") String identityCode){

    }

    /**
     *  退房管理
     * @param identityCode
     */
    @RequiresPermissions("room:check")
    @RequestMapping("{identityCode}/checkOut")
    public void roomCheckOut(@PathVariable(name = "identityCode") String identityCode){
        recordService.checkOut(identityCode);
    }

    /**
     *  时间段内住房状态检查
     * @param bean
     */
    @RequestMapping("/statusCheck")
    @ResponseBody
    public ResultBean roomStatusCheck(@RequestBody RoomInfoBean bean){
        ResultBean result = new ResultBean();
        Date[] range = DateUtil.timeRangeAnalyze(bean.getTimeRange());
        Room room = roomService.getCurrentRoom(bean.getRoomNumber());
        if (!roomService.roomStatusCheck(room,range[0],range[1]).getRoomStatus().equals(RoomStatusEnum.OCCUPIED.getKey())) {
            result.setMessage(PromptMessageEnum.CURRENT_TIME_ORDERED.getValue());
        }
        return result;
    }

    /**
     *  住房预定
     * @param bean
     */
    @RequestMapping("/order")
    public void roomOrder(@RequestBody OrderInfoBean bean){
        Date[] range = DateUtil.timeRangeAnalyze(bean.getTimeRange());
        List<Room> roomList = new ArrayList<>();
        List<Integer> priceList = new ArrayList<>();
        for (String roomNumber:bean.getRoomNumber()){
            Room room = roomService.getCurrentRoom(roomNumber);
            roomList.add(room);
            priceList.add(room.getRoomPrice());
        }
        Record record = new Record(
                CodeUntil.getCode(), (String) SecurityUtils.getSubject().getPrincipal(),
                PriceCalculateUtil.priceCalculate(range[0],range[1],priceList),
                range[0],range[1],roomList);
        recordService.createRecord(record);
    }

//    /**
//     *  住房预定
//     * @param bean
//     */
//    @RequestMapping("/orderTest")
//    public void roomOrderT(@RequestBody OrderInfoBean bean){
//        String[] range = bean.getTimeRange().split(" - ");
//        String checkInTimeStr = range[0];
//        String checkOutTimeStr = range[1];
//        Date checkInTime = DateUtil.parseDate(checkInTimeStr);
//        Date checkOutTime = DateUtil.parseDate(checkOutTimeStr);
//        List<Room> roomList = new ArrayList<>();
//        List<Integer> priceList = new ArrayList<>();
//        for (Long roomId:bean.getRoomId()){
//            Room room = new Room();
//            room.setId(roomId);
//            roomList.add(room);
//            priceList.add((roomService.getRoom(roomId)).getRoomPrice());
//        }
//        Record record = new Record(
//                CodeUntil.getCode(), (String) SecurityUtils.getSubject().getPrincipal(),
//                PriceCalculateUtil.priceCalculate(checkInTime,checkOutTime,priceList),
//                checkInTime,checkOutTime,roomList);
//        recordService.createRecord(record);
//    }

    /**
     *  住房查询
     */
    @RequestMapping("/inquiry")
    public void roomInquiry(@RequestParam String timeRange,
                            @RequestParam(required = false) Integer roomType,
                            @RequestParam(required = false) Integer roomStatus){
        Date[] range = DateUtil.timeRangeAnalyze(timeRange);
        roomService.roomInquiry(roomType,roomStatus,range[0],range[1],DEFAULT_PAGE_NUMBER,DEFAULT_PAGE_SIZE);
    }

    @RequestMapping("{roomNumber}/info")
    public String roomInfo(@PathVariable(name = "roomNumber") String roomNumber,Model model){
        Room room = roomService.getCurrentRoom(roomNumber);
        model.addAttribute("info",room);
        return null;
    }
}
