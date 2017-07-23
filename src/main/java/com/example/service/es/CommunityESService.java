package com.example.service.es;

import com.example.entity.Community;

import java.util.List;


///**
// *
// */
public interface CommunityESService {
    List<Community> queryByName(String name);

    public int update(Community community);

    public int update(List<Community> communitys);

}
