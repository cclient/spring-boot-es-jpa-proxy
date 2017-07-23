package com.example.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "applog", type = "syslog")
//@Setting(settingPath = 'elasticSearchSettings/analyzer.json')
public class Applog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Field(type = FieldType.String)
    private Long id;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String versionName;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String versionFrom;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String phone;
    @Field(type = FieldType.String)
    private String time;
    @Field(type = FieldType.Long)
    private Long logtype;
    @Field(type = FieldType.Long)
    private Long contentid;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String logid;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String userid;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String phoneImei;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionFrom() {
        return versionFrom;
    }

    public void setVersionFrom(String versionFrom) {
        this.versionFrom = versionFrom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getLogtype() {
        return logtype;
    }

    public void setLogtype(Long logtype) {
        this.logtype = logtype;
    }

    public Long getContentid() {
        return contentid;
    }

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneImei() {
        return phoneImei;
    }

    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }
}
