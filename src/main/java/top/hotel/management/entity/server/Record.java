package top.hotel.management.entity.server;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.hotel.management.entity.base.AbstractEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "record")
public class Record extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false,unique = true)
    private String recordCode;

    @Column(nullable = false)
    private String recordOwner;

    @Column(nullable = false)
    private String identityCode;
    /**
     * 1 未支付  2 已支付  3  已退订
     */
    @Column(nullable = false)
    private Integer recordStatus;

    @Column(nullable = false)
    private Integer recordPrice;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkInTime;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOutTime;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_rel_record",joinColumns = {@JoinColumn(name = "record_id")},inverseJoinColumns = {@JoinColumn(name = "room_id")})
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Room> roomList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "record_rel_detail",joinColumns = {@JoinColumn(name = "record_id")},inverseJoinColumns = {@JoinColumn(name = "detail_id")})
    @Fetch(FetchMode.SUBSELECT)
    private List<Detail> detailList;

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

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getRecordPrice() {
        return recordPrice;
    }

    public void setRecordPrice(Integer recordPrice) {
        this.recordPrice = recordPrice;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public Record(String recordCode, String recordOwner, Integer recordPrice, Date checkInTime, Date checkOutTime, List<Room> roomList) {
        this.recordCode = recordCode;
        this.recordOwner = recordOwner;
        this.recordStatus = 1;
        this.recordPrice = recordPrice;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.roomList = roomList;
    }

    public Record(){}
}
