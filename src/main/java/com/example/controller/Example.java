package com.example.controller;

import com.example.entity.Community;
import com.example.service.sql.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/test")
public class Example {
    @Autowired
    private CommunityService communityService;

    @RequestMapping(value = "/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/c")
    String c() {
        Community community = communityService.getCommunity(new Long(12290));
        return community.getId().toString();
    }
}
