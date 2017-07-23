package com.example;

import com.example.config.ESConnectionSettings;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aa on 2016/8/25.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties({ESConnectionSettings.class})
public class AppLogRepositoryTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void searchAggtest() {
        Client client = elasticsearchTemplate.getClient();
        //组装分组
        DateHistogramBuilder dateAgg = AggregationBuilders.dateHistogram("2");
        //定义分组的日期字段
        dateAgg.field("@timestamp");
        dateAgg.interval(DateHistogramInterval.DAY);
        dateAgg.timeZone("Asia/Shanghai");
        dateAgg.minDocCount(1);
        dateAgg.extendedBounds(1469526122138L, 1472118122138L);

        TermsBuilder termsBuilder = AggregationBuilders.terms("3");
        termsBuilder.field("phone");
        termsBuilder.size(5);
        termsBuilder.order(Terms.Order.count(false));
        dateAgg.subAggregation(termsBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.queryStringQuery("*").analyzeWildcard(true))
                .must(QueryBuilders.rangeQuery("@timestamp").gte(1469526122138L).lte(1472118122138L).format("epoch_millis"));
        org.elasticsearch.index.query.FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.queryStringQuery("*").analyzeWildcard(true), boolQueryBuilder);
        SearchRequestBuilder sbuilder = client.prepareSearch("applog") //index name
                .setTypes("syslog") //type name
                .setQuery(builder)
                .setSize(0).addAggregation(dateAgg);
        System.out.println("\n");
        System.out.println(sbuilder);
        try {
            SearchResponse r = client.search(sbuilder.request()).get();
            //获取一级聚合数据
            Histogram h = r.getAggregations().get("2");
//            h.getBuckets()
            //得到一级聚合结果里面的分桶集合
            List<Histogram.Bucket> buckets = (List<Histogram.Bucket>) h.getBuckets();
//            List<DateHistogram.Bucket> buckets = (List<InternalDateHistogram.class>) h.getBuckets();
            //遍历分桶集
            for (Histogram.Bucket b : buckets) {
                //读取二级聚合数据集引用
                Aggregations sub = b.getAggregations();
                //获取二级聚合集合
                StringTerms count = sub.get("success");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
