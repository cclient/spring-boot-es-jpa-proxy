package com.example;


import com.example.common.dict.Mydict;
import com.example.common.dict.MydictImpl;
import com.example.config.ESConnectionSettings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})

public class DictTests {

    @Autowired
    @Qualifier("mydict")
    Mydict mydict;

    @Test
    public void esclientopen() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println("path");
        System.out.println(path);
        path = path + "/business.dict";
        System.out.println(path);
        List<String> ls = mydict.LoadDictFromFile(path);
        System.out.println(ls.size());
        System.out.println(ls.get(5));
    }

    @Test
    public void getFirstNumOffset() {
        System.out.println("hello");
        System.out.println(MydictImpl.splitByFirstNum("东革新里40号院").toString());
        System.out.println(MydictImpl.splitByFirstNum("东革新里40号院")[0]);
        System.out.println(MydictImpl.splitByFirstNum("东革新里40号院")[1]);

    }

}
