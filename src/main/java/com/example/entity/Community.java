package com.example.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "community")
@Document(indexName = "applog", type = "syslog")
//@Setting(settingPath = 'elasticSearchSettings/analyzer.json')
public class Community implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Long id;
    @Field(type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @Column(name = "Name", unique = false, nullable = false)
    private String name;
    //单filed如果有多个analyzer 则必须指定 不然报错 mapper [content] has different [analyzer]]
    @Field(type = FieldType.String, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @Column(name = "Address")

    private String address;
    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed)
    @Transient
    private List<String> bank;
    @Column(name = "Cname")
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String cname;
    @Column(name = "Bank")
    @Field(ignoreFields = {"banks", "星河城"})
    private String banks;
    @Field(type = FieldType.Date)
    @Transient
    private Date date;


    public Community() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getBank() {
        return bank;
    }

    public void setBank(List<String> bank) {
        this.bank = bank;
    }

    public String getBanks() {
        return banks;
    }

    public void setBanks(String banks) {
        this.banks = banks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
