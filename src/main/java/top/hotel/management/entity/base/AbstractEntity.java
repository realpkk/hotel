package top.hotel.management.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity extends IDEntity {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public AbstractEntity(){}

    @PrePersist
    public void preCreate(){
        Date date = new Date();
        this.createTime = date;
    }

    @PreUpdate
    public void preUpdate(){
        Date date = new Date();
        if (this.updateTime == null){
            this.createTime = date;
        }
        this.updateTime = date;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
