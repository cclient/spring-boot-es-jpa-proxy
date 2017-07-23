package com.example.controller;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/")
public class Data {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    private DateHistogramBuilder createDateHistogramBuilder() {
        DateHistogramBuilder dateAgg = AggregationBuilders.dateHistogram("2");
        //定义分组的日期字段
        dateAgg.field("@timestamp");
        dateAgg.interval(DateHistogramInterval.DAY);
        dateAgg.timeZone("Asia/Shanghai");
        dateAgg.minDocCount(1);
        dateAgg.extendedBounds(1469526122138L, 1472118122138L);
        return dateAgg;
    }

    private TermsBuilder createSubAggBuilder() {
        TermsBuilder termsBuilder = AggregationBuilders.terms("3");
        termsBuilder.field("phone");
        termsBuilder.size(5);
        termsBuilder.order(Terms.Order.count(false));
        return termsBuilder;
    }

    /**
     * 按时间分隔的 每天 phone 聚合 二级聚合
     *
     * @param
     * @return
     */
    @RequestMapping("/data")
    public @ResponseBody
    String data() {
        Client client = elasticsearchTemplate.getClient();
        DateHistogramBuilder dateAgg = createDateHistogramBuilder();
        TermsBuilder termsBuilder = createSubAggBuilder();
        //设置下级聚合
        dateAgg.subAggregation(termsBuilder);
        BoolQueryBuilder boolQueryBuilder = createQueryFilterData(1469526122138L, 1472118122138L);
        FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.queryStringQuery("*").analyzeWildcard(true), boolQueryBuilder);
        SearchRequestBuilder sbuilder = client.prepareSearch("applog") //index name
                .setTypes("syslog") //type name
                .setQuery(builder)
                .setSize(0)
                .addAggregation(dateAgg);
        System.out.println(sbuilder);
        try {
            SearchResponse r = client.search(sbuilder.request()).get();
            return r.toString();
//            //获取一级聚合数据
//            Histogram h=r.getAggregations().get("2");
////            h.getBuckets()
//            //得到一级聚合结果里面的分桶集合
//            List<Histogram.Bucket> buckets = (List<Histogram.Bucket>) h.getBuckets();
////            List<DateHistogram.Bucket> buckets = (List<InternalDateHistogram.class>) h.getBuckets();
//            //遍历分桶集
//            for(Histogram.Bucket b:buckets){
//                //读取二级聚合数据集引用
//                Aggregations sub = b.getAggregations();
//                //获取二级聚合集合
//                StringTerms count = sub.get("success");
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    private BoolQueryBuilder createQueryFilterData(Long datestart, Long dateend) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders
                        .queryStringQuery("*")
                        .analyzeWildcard(true))
                .must(QueryBuilders
                        .rangeQuery("@timestamp")
                        .gte(datestart)
                        .lte(dateend)
                        .format("epoch_millis")
                );
        return boolQueryBuilder;
    }


    /**
     * 按时间分隔的 每天 versionFrom  聚合 一级聚合 当天用户总量
     *
     * @param
     * @return
     */
    @RequestMapping("/top")
    public @ResponseBody
    String topVersionFrom() {
        Client client = elasticsearchTemplate.getClient();
        TermsBuilder termsBuilder = AggregationBuilders.terms("3"); //这个是返回的key 取值时用，对结果不影响
        termsBuilder.field("versionFrom");
        termsBuilder.size(100);
        termsBuilder.order(Terms.Order.count(false));
        BoolQueryBuilder boolQueryBuilder = createQueryFilterData(1469526122138L, 1472118122138L);
        FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.queryStringQuery("*").analyzeWildcard(true), boolQueryBuilder);
        SearchRequestBuilder sbuilder = client.prepareSearch("applog") //index name
                .setTypes("syslog") //type name
                .setQuery(builder)
                .setSize(0)
                .addAggregation(termsBuilder);
        System.out.println(sbuilder);
        try {
            SearchResponse r = client.search(sbuilder.request()).get();
            return r.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 当天用户总数 distinct 无其他条件
     *
     * @param
     * @return
     */
    @RequestMapping("/count")
    public @ResponseBody
    String countPhone() {
        Client client = elasticsearchTemplate.getClient();
        //组装分组
        CardinalityBuilder cardinalityBuilder = AggregationBuilders.cardinality("1").field("phoneImei");
        BoolQueryBuilder boolQueryBuilder = createQueryFilterData(1469526122138L, 1472118122138L);
        FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.queryStringQuery("*").analyzeWildcard(true), boolQueryBuilder);
        SearchRequestBuilder sbuilder = client.prepareSearch("applog") //index name
                .setTypes("syslog") //type name
                .setQuery(builder)
                .setSize(0)
                .addAggregation(cardinalityBuilder);
        System.out.println(sbuilder);
        try {
            SearchResponse r = client.search(sbuilder.request()).get();
            return r.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 当天用户总数 distinct 有其他条件
     *
     * @param
     * @return
     */
    @RequestMapping("/countopenapp")
    public @ResponseBody
    String countPhoneByLogName() {
        Client client = elasticsearchTemplate.getClient();
        //组装分组
        CardinalityBuilder cardinalityBuilder = AggregationBuilders.cardinality("1").field("phoneImei");
        BoolQueryBuilder boolQueryBuilder = createQueryFilterData(1469526122138L, 1472118122138L);
        //就多了这一句
        boolQueryBuilder.must(QueryBuilders.termQuery("name", "openapp"));

        FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.queryStringQuery("*").analyzeWildcard(true), boolQueryBuilder);
        SearchRequestBuilder sbuilder = client.prepareSearch("applog") //index name
                .setTypes("syslog") //type name
                .setQuery(builder)
                .setSize(0)
                .addAggregation(cardinalityBuilder);
        System.out.println(sbuilder);
        try {
            SearchResponse r = client.search(sbuilder.request()).get();
            String jsonData = JSON.toJSONString(r.getAggregations().get("1"));
            return jsonData;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
