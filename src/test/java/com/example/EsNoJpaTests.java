package com.example;


import com.example.config.ESConnectionSettings;
import com.example.dao.es.ZufangquanESRepository;
import com.example.entity.Zufangquan;
import com.example.entity.ZufangquanBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.weight.WeightBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})
public class EsNoJpaTests {
    private String indexname = "kyjes";
    private String typename = "zufangquan";
    @Resource
    @Qualifier("zufangquanESRepository")
    private ZufangquanESRepository zufangquanRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void iniDataData() {
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
    public void esSearch() {
        QueryBuilder qb = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("describe", "丰台区").type(Type.BOOLEAN));
        System.out.println(qb);

        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder srq = client.prepareSearch(indexname)
                .setTypes(typename).setQuery(qb)
                .addHighlightedField("describe")
                .setHighlighterPreTags("<tag1>", "<tag2>")
                .setHighlighterPostTags("</tag1>", "</tag2>");
        System.out.println("ElasticSearch Query using Java Client API:\n" + srq.internalBuilder());
        SearchResponse searchResponse = srq.execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            System.out.println(hit.getHighlightFields().get("describe").toString());
        }
    }

    @Test
    public void esSearchByTemplate() {
        QueryBuilder qb = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("describe", "丰台区").type(Type.BOOLEAN));
        SearchQuery sq = (new NativeSearchQueryBuilder())
                .withQuery(qb)
                .withHighlightFields(new HighlightBuilder.Field("describe")
                        .preTags("<tag1>", "<tag2>")
                        .postTags("</tag1>", "</tag2>")
                ).build();
        Page<Zufangquan> zufangquanPage = elasticsearchTemplate.queryForPage(sq, Zufangquan.class);
        System.out.println("search end");
        for (Zufangquan zufangquan : zufangquanPage) {
            System.out.println(zufangquan.getDescribe());
        }
    }

    @Test
    public void esSearchScore() {
        QueryBuilder qb = QueryBuilders.functionScoreQuery(QueryBuilders
                .boolQuery()
                .must(QueryBuilders.matchQuery("describe", "租").type(Type.BOOLEAN)))
                .add(QueryBuilders.boolQuery().filter(QueryBuilders.matchQuery("describe", "角门")), new WeightBuilder().setWeight(3));

        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder srq = client.prepareSearch(indexname)
                .setTypes(typename).setQuery(qb)
                .addHighlightedField("describe")
                .setHighlighterPreTags("<tag1>", "<tag2>")
                .setHighlighterPostTags("</tag1>", "</tag2>");
        System.out.println("ElasticSearch Query using Java Client API:\n" + srq.internalBuilder());
        SearchResponse searchResponse = srq.execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            System.out.println(hit.getHighlightFields().get("describe").toString());
        }
    }

    @Test
    public void esSearchIdBetween() {
        QueryBuilder qb = QueryBuilders.rangeQuery("id").from(2).to(3);
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder srq = client.prepareSearch(indexname)
                .setTypes(typename).setQuery(qb);
        SearchResponse searchResponse = srq.execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }

    @Test
    public void esSearchScanAndScroll() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withIndices("test-index")
                .withTypes("test-type")
                .withPageable(new PageRequest(0, 1))
                .build();

        String scrollId = elasticsearchTemplate.scan(searchQuery, 1000, false);
        List<Zufangquan> sampleEntities = new ArrayList<Zufangquan>();

        boolean hasRecords = true;
        while (hasRecords) {
            Page<Zufangquan> page = elasticsearchTemplate.scroll(scrollId, 5000L, Zufangquan.class);
            if (page != null) {
                sampleEntities.addAll(page.getContent());
                hasRecords = page.hasNext();
            } else {
                hasRecords = false;
            }
        }
    }
}
