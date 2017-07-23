package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ZufangquanBuilder {

    private Zufangquan result;

    public ZufangquanBuilder(Long id) {
        result = new Zufangquan(id);
    }

    public ZufangquanBuilder name(String name) {
        result.setName(name);
        return this;
    }

    public ZufangquanBuilder describe(String describe) {
        result.setDescribe(describe);
        return this;
    }

    public ZufangquanBuilder insertdate(Date date) {
        result.setInsertdate(date);
        return this;
    }

    public ZufangquanBuilder updatedate(Date date) {
        result.setUpdatedate(date);
        return this;
    }

    public ZufangquanBuilder price(Double price) {
        result.setPrice(price);
        return this;
    }

    public Zufangquan build() {
        return result;
    }

    public ZufangquanBuilder comment(String comment) {
        List<String> tagsTmp = new ArrayList<>();
        if (result.getComments() == null) {
            result.setComments(tagsTmp);
        } else {
            tagsTmp = result.getComments();
        }
        tagsTmp.add(comment);
        return this;
    }

    public ZufangquanBuilder buyattr(String buyattr) {
        List<String> tagsTmp = new ArrayList<>();
        if (result.getBuyattr() == null) {
            result.setBuyattr(tagsTmp);
        } else {
            tagsTmp = result.getBuyattr();
        }
        tagsTmp.add(buyattr);
        return this;
    }


//
//    public IndexQuery buildIndex() {
//        IndexQuery indexQuery = new IndexQuery();
//        indexQuery.setId(result.getId());
//        indexQuery.setObject(result);
//        return indexQuery;
//    }

}
