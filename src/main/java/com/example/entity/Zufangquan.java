package com.example.entity;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "kyjes", type = "zufangquan")
public class Zufangquan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    @Field(type = FieldType.Nested, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String describe;
    @Field(type = FieldType.Double, index = FieldIndex.not_analyzed)
    private Double price;
    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed)
    private List<String> buyattr;
    private Date insertdate;
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
    private Date updatedate;
    @Field(type = FieldType.Nested, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private List<String> comments;

    public Zufangquan(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getBuyattr() {
        return buyattr;
    }

    public void setBuyattr(List<String> buyattr) {
        this.buyattr = buyattr;
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }


}
