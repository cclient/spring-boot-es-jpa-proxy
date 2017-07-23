package com.example.service.es;

/**
 *
 */

import com.example.dao.es.ApplogESRepository;
import com.example.entity.Applog;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("memberESService")
public class ApplogESServiceImpl implements ApplogESService {
    @Autowired
    @Qualifier("applogESRepository")
    private ApplogESRepository applogESRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<Applog> queryByVersionName(String name) {
        return this.applogESRepository.queryByVersionName(name);
    }

    public int update(Applog community) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", community.getName());
        UpdateQuery uq = new UpdateQueryBuilder()
                .withClass(Applog.class)
//                    .withId(community.getId().toString())
                .withUpdateRequest(
                        new UpdateRequest()
                                .doc(map)
                )
                .build();
        elasticsearchTemplate.update(uq).getGetResult();
        return 0;
    }

    public int update(List<Applog> communitys) {
        List<UpdateQuery> indexQueries = new ArrayList<>(communitys.size());
        for (Applog community : communitys) {
            UpdateQuery uq = new UpdateQuery();
//            uq.setId(community.getId().toString());
            UpdateRequest ur = new UpdateRequest();
            uq.setUpdateRequest(ur.doc(community));
            indexQueries.add(uq);
        }
        elasticsearchTemplate.bulkUpdate(indexQueries);
        return 0;
    }
}
