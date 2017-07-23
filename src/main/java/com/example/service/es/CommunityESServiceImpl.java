package com.example.service.es;


import com.example.dao.es.CommunityESRepository;
import com.example.entity.Community;
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

@Component("communityESService")
public class CommunityESServiceImpl implements CommunityESService {
    @Autowired
    @Qualifier("communityESRepository")
    private CommunityESRepository communityRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public Community getCommunity(Long Id) {
        return this.communityRepository.findOne(Id);
    }

    public List<Community> queryByName(String name) {
        return this.communityRepository.queryByName(name);
    }

    public int update(Community community) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("name", community.getName());
        map.put("id", community.getId());
        UpdateQuery uq = new UpdateQueryBuilder()
                .withClass(Community.class)
                .withId(community.getId().toString())
                .withUpdateRequest(
                        new UpdateRequest()
                                .doc(map)
                )
                .build();
        elasticsearchTemplate.update(uq).getGetResult();

        return 0;
    }

    public int update(List<Community> communitys) {
        List<UpdateQuery> indexQueries = new ArrayList<>(communitys.size());
        for (Community community : communitys) {
            UpdateQuery uq = new UpdateQuery();
            uq.setId(community.getId().toString());
            UpdateRequest ur = new UpdateRequest();
            uq.setUpdateRequest(ur.doc(community));
            indexQueries.add(uq);
        }
        elasticsearchTemplate.bulkUpdate(indexQueries);
        return 0;
    }
}
