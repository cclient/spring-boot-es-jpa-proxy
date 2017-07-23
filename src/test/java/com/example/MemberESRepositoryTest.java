package com.example;

import com.example.config.ESConnectionSettings;
import com.example.dao.es.ApplogESRepository;
import com.example.dao.sql.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})
public class MemberESRepositoryTest {
    @Autowired
    @Qualifier("memberRepository")
    MemberRepository memberRepository;

    @Autowired
    @Qualifier("applogESRepository")
    ApplogESRepository applogESRepository;


    @Test
    public void getmemberCount() {
        System.out.println("\nhello word");
        Long count = memberRepository.count();
        System.out.println(count);
        System.out.println("count");
    }

    @Test
    public void getmemberCountByRegistTime() {
        System.out.println("\nhello word");
        Integer count = memberRepository.countByIdAfter(3);
        System.out.println(count);
        System.out.println("count");
    }

    @Test
    public void getlogCount() {
        System.out.println("\nhello word");
        Long count = applogESRepository.count();
        System.out.println(count);
        System.out.println("count");
    }
}
