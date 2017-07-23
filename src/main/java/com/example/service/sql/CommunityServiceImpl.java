package com.example.service.sql;

/**
 *
 */

import com.example.dao.sql.CommunityRepository;
import com.example.entity.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("communityService")
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    @Qualifier("CommunityRepository")
    private CommunityRepository communityRepository;

    public Community getCommunity(Long Id) {
        return this.communityRepository.findOne(Id);
    }
}
