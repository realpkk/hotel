package top.hotel.management.entity.server;

import top.hotel.management.entity.base.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false,unique = true)
    private String roomNumber;

    /**
     * 1 单人间  2  双人间
     */
    @Column(nullable = false)
    private Integer roomType;

    /**
     * 1 空  2  非空   3  已预订
     */
    @Column(nullable = false)
    private Integer roomStatus;

    @Column(nullable = false)
    private Integer roomPrice;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }
}
