package com.example;

import com.example.config.ESConnectionSettings;
import com.example.dao.sql.VROrderVipRepository;
import com.example.entity.VROrderVip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})
public class VROrderVipRepositoryTest {
    @Autowired
    @Qualifier("vrOrderVipRepository")
    VROrderVipRepository vrOrderVipRepository;

    @Test
    public void getordersumCount() {
        System.out.println("\nhello word");
        Double count = vrOrderVipRepository.sumMoney();
        System.out.println(count);
        System.out.println("count");
    }

    @Test
    public void getsuccessorderCount() {
        System.out.println("\nhello word");
        Double count = vrOrderVipRepository.sumSuccessMoney();
        System.out.println(count);
        System.out.println("count");
    }

    @Test
    public void getOrderByUidCount() {
        System.out.println("\nhello word");
        Collection<VROrderVip> orderVips = vrOrderVipRepository.queryByUid(6594);
        Iterator<VROrderVip> iterator = orderVips.iterator();
        if (iterator.hasNext()) {
            VROrderVip vrordervip = iterator.next();
            System.out.println("money " + vrordervip.getMoney());
        }
    }
}
