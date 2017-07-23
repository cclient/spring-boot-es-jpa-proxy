package com.example;


import com.example.config.ESConnectionSettings;
import com.example.dao.es.ZufangquanESRepository;
import com.example.entity.Zufangquan;
import com.example.entity.ZufangquanBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:/springContext-test.xml")
public class EsJpaTests {

    @Resource
    @Qualifier("zufangquanESRepository")
    private ZufangquanESRepository zufangquanRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void iniData() {
        zufangquanRepository.deleteAll();
        Zufangquan z1 = new ZufangquanBuilder((long) 1)
                .name("张三")
                .describe("本人有通州区西马庄一套三室两厅两卫一厨，合租房出租，租金3500元/月，押一付三，有要租的请联系我，139****5057")
                .buyattr("三室")
                .comment("评论1")
                .comment("评论2")
                .insertdate(new Date())
                .updatedate(new Date())
                .price((double) 3999)
                .build();

        Zufangquan z2 = new ZufangquanBuilder((long) 2)
                .name("李四")
                .describe("本人有昌平区天通东苑一套三室两厅两卫一厨，合租房出租，租金3500元/月，押一付三，有要租的请联系我，139****5057")
                .buyattr("天通东苑")
                .comment("评论1")
                .comment("评论2")
                .insertdate(new Date())
                .updatedate(new Date())
                .price((double) 1999)
                .build();

        Zufangquan z3 = new ZufangquanBuilder((long) 3)
                .name("王五")
                .describe("本人有丰台区角门东里一套三室两厅两卫一厨，合租房出租，租金2200元/月，押一付三，有要租的请联系我，139****5057")
                .buyattr("丰台区")
                .comment("评论1")
                .comment("评论2")
                .insertdate(new Date())
                .updatedate(new Date())
                .price((double) 2999)
                .build();

        Zufangquan z4 = new ZufangquanBuilder((long) 4)
                .name("赵六")
                .describe("本人有丰台区角门东里一套三室两厅两卫一厨，整租房出租，租金2200元/月，押一付三，有要租的请联系我，139****5057")
                .buyattr("丰台区")
                .comment("评论1")
                .comment("评论2")
                .insertdate(new Date())
                .updatedate(new Date())
                .price((double) 5000)
                .build();
        zufangquanRepository.index(z1);
        zufangquanRepository.index(z2);
        zufangquanRepository.index(z3);
        zufangquanRepository.index(z4);
    }

    @Test
    public void shouldReturnZufangquanByName() {
        //when
        Zufangquan zufangquan = zufangquanRepository.findByName("李四");
        //then
        assertThat(zufangquan.getName(), is("李四"));
    }

    @Test
    public void shouldReturnListOfZufangquanByDescribe() {
        List<Zufangquan> zufangquans = zufangquanRepository.findByDescribe("我想在角门找个三室的整租");
        System.out.println("find by describe");
        for (Zufangquan zf : zufangquans) {
            System.out.println(zf.getDescribe());
        }
        zufangquans = zufangquanRepository.searchByDescribe("我想在角门找个三室的整租");
        System.out.println("search by describe");
        for (Zufangquan zf : zufangquans) {
            System.out.println(zf.getDescribe());
        }
    }

    @Test
    public void esSearchIdBetween() {
        List<Zufangquan> zufangquans = zufangquanRepository.findByIdBetween(2, 3);
        System.out.println("search by describe");
        for (Zufangquan zf : zufangquans) {
            System.out.println(zf.getId());
            System.out.println(zf.getDescribe());
        }
    }

    @Test
    public void esSearchIdIn() {
        List<Zufangquan> zufangquans = zufangquanRepository.findByIdIsIn(Arrays.asList(new String[]{"3", "4"}));
        System.out.println("search by describe");
        for (Zufangquan zf : zufangquans) {
            System.out.println(zf.getId());
            System.out.println(zf.getDescribe());
        }
    }

}
