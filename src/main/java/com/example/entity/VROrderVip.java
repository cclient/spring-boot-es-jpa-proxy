package com.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vr_order_vip")
public class VROrderVip {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;


    private Integer uid;


    @Column(name = "vip_id")
    private Integer vipId;


    private String money;


    private String name;


    private Date time;


    private String billnum;


    private Byte status;


    private Byte paytype;


    private Integer notifyid;


    private Date notifytime;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public Integer getUid() {
        return uid;
    }


    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public Integer getVipId() {
        return vipId;
    }


    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }


    public String getMoney() {
        return money;
    }


    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public Date getTime() {
        return time;
    }


    public void setTime(Date time) {
        this.time = time;
    }


    public String getBillnum() {
        return billnum;
    }


    public void setBillnum(String billnum) {
        this.billnum = billnum == null ? null : billnum.trim();
    }


    public Byte getStatus() {
        return status;
    }


    public void setStatus(Byte status) {
        this.status = status;
    }


    public Byte getPaytype() {
        return paytype;
    }


    public void setPaytype(Byte paytype) {
        this.paytype = paytype;
    }


    public Integer getNotifyid() {
        return notifyid;
    }


    public void setNotifyid(Integer notifyid) {
        this.notifyid = notifyid;
    }


    public Date getNotifytime() {
        return notifytime;
    }


    public void setNotifytime(Date notifytime) {
        this.notifytime = notifytime;
    }
}