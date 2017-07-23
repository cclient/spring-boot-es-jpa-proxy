package com.example;

import com.example.config.ESConnectionSettings;
import com.example.entity.Applog;
import com.example.service.es.ApplogESService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest

@EnableConfigurationProperties({ESConnectionSettings.class})

public class DemoApplicationTests {
    @Autowired
    ApplogESService applogESService;

    @Test
    public void esjpaget() throws IOException {
        List<Applog> response = applogESService.queryByVersionName("押一付三");
        System.out.println("name");
        System.out.println("name");
        System.out.println(response.size());
        Iterator<Applog> iterator = response.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getPhoneImei());
        }
    }
}
