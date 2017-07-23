package com.example.service.es;

import com.example.entity.Applog;

import java.util.List;


///**
// *
// */
public interface ApplogESService {
    List<Applog> queryByVersionName(String name);

    public int update(Applog community);

    public int update(List<Applog> communitys);
}
