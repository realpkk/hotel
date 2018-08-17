package top.hotel.management.entity.server;

import top.hotel.management.entity.base.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "record")
public class Record extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false,unique = true)
    private String recordCode;

    @Column(nullable = false)
    private String recordOwner;

    @Column(nullable = false)
    private String roomNumber;

    /**
     * 1 未支付  2 已支付  3  已退订
     */
    @Column(nullable = false)
    private String recordStatus;

    @Column(nullable = false)
    private Integer recordPrice;

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getRecordOwner() {
        return recordOwner;
    }

    public void setRecordOwner(String recordOwner) {
        this.recordOwner = recordOwner;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getRecordPrice() {
        return recordPrice;
    }

    public void setRecordPrice(Integer recordPrice) {
        this.recordPrice = recordPrice;
    }
}
