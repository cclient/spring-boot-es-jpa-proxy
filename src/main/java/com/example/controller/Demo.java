package com.example.controller;

import com.example.dao.sql.MemberRepository;
import com.example.dao.sql.VROrderVipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;


@Controller
@RequestMapping("/")
public class Demo {
    @Autowired
    @Qualifier("memberRepository")
    MemberRepository memberRepository;
    @Autowired
    @Qualifier("vrOrderVipRepository")
    VROrderVipRepository vrOrderVipRepository;

    @RequestMapping("/demo")
    public ModelAndView demo() {
        //所有用户
        Long allcount = memberRepository.count();
        //时间后注册 （数据里）
        Date date = new Date();
        Integer afterDateCount = memberRepository.countByReigsttimeAfter(date);
        //营收总额
        Double successOrderSum = vrOrderVipRepository.sumSuccessMoney();
        ModelAndView mav = new ModelAndView("demo");
        mav.addObject("allcount", allcount);
        mav.addObject("afterDateCount", afterDateCount);
        mav.addObject("successOrderSum", successOrderSum);
        return mav;
    }
}
