package com.example.elastic.config;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.elastic.domain.User;

/**
 * @author xyw
 * @date 2020/12/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchConfigTest {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void getMapping() {
        Map mapping = elasticsearchTemplate.getMapping(User.class);
        System.out.println(mapping);

    }

    //分页查询
    @Test
    public void findAll() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(PageRequest.of(20, 30))
                .build();

        List<User> users = elasticsearchTemplate.queryForList(searchQuery, User.class);
        users.forEach(user -> System.out.println(user));
    }

    //分页查询scroll
//    scroll=5m表示设置scroll_id保留5分钟可用。
//    使用scroll必须要将from设置为0。
//    size决定后面每次调用_search搜索返回的数量
    @Test
    public void findAllScroll() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withPageable(PageRequest.of(0, 30))
                .build();

        ScrolledPage<User> scroll = (ScrolledPage<User>) elasticsearchTemplate.startScroll(3000, searchQuery, User.class);
        System.out.println("查询总命中数：" + scroll.getTotalElements());
        while (scroll.hasContent()) {
            for (User user : scroll.getContent()) {
                //Do your work here
                System.out.println(user);
            }
            //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
            scroll = (ScrolledPage<User>) elasticsearchTemplate.continueScroll(scroll.getScrollId(), 3000, User.class);
        }
        //及时释放es服务器资源
        elasticsearchTemplate.clearScroll(scroll.getScrollId());
    }

    @Test
    public void findById() {
        GetQuery query = new GetQuery();
        query.setId("999");
        User user = elasticsearchTemplate.queryForObject(query, User.class);
        System.out.println(user);
    }

    //局部更新
    @Test
    public void testUpdate() {
        Map<String, Object> params = new HashMap<>();
        params.put("firstname", "xxxxx");
        params.put("lastname", "y");
        params.put("age", 32);
        params.put("gender","M");

        UpdateRequest updateRequest = new UpdateRequest().doc(params);

        UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();
        updateQueryBuilder.withId("999");
        updateQueryBuilder.withUpdateRequest(updateRequest);
        updateQueryBuilder.withClass(User.class);
        UpdateQuery updateQuery = updateQueryBuilder.build();

        elasticsearchTemplate.update(updateQuery);
    }

    @Test
    public void findCriteria() {
        Criteria criteria = new Criteria("firstname").is("yw");
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        List<User> users = elasticsearchTemplate.queryForList(criteriaQuery, User.class);
        users.forEach(user -> System.out.println(user));
    }

    //平均值
    @Test
    public void avg() {
        AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg("Avg_age").field("age");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).addAggregation(avgAggregationBuilder).build();
        Double avg = elasticsearchTemplate.query(searchQuery, response -> {
            InternalAvg internalAvg = (InternalAvg) response.getAggregations().asList().get(0);
            return internalAvg.getValue();
        });
        System.out.println(avg);
    }

    //去重
    @Test
    public void cardinality() {
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("cardinality").field("age");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).addAggregation(cardinalityAggregationBuilder).build();
        Long query = elasticsearchTemplate.query(searchQuery, response -> {
            InternalCardinality cardinality = (InternalCardinality) response.getAggregations().asList().get(0);
            return cardinality.getValue();
        });
        System.out.println(query);
    }

//    //计数统计
//    @Test
//    public void count() {
//        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders.cardinality("cardinality").field("age");
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).addAggregation(cardinalityAggregationBuilder).build();
//        Long query = elasticsearchTemplate.query(searchQuery, response -> {
//            InternalCardinality cardinality = (InternalCardinality) response.getAggregations().asList().get(0);
//            return cardinality.getValue();
//        });
//        System.out.println(query);
//    }

    //条件计数统计
    @Test
    public void termCount() {
        Criteria criteria = new Criteria("age").is(23);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        long count = elasticsearchTemplate.count(criteriaQuery, User.class);
        System.out.println(count);
    }

    //    扩展统计聚合
    @Test
    public void extendedStats() {
        ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder = AggregationBuilders.extendedStats("extended_stats").field("age");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).addAggregation(extendedStatsAggregationBuilder).build();
        elasticsearchTemplate.query(searchQuery, response -> {
            List<Aggregation> aggregations = response.getAggregations().asList();
            return aggregations;
        });
    }

    //terms分组
    @Test
    public void terms() {
        TermsAggregationBuilder gender = AggregationBuilders.terms("gender_terms").field("gender");
        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(gender).build();
        elasticsearchTemplate.query(searchQuery,response -> {
            Aggregations aggregations = response.getAggregations();
            return aggregations;
        });
    }

    //range
//    @Test
//    public void range() {
//        RangeAggregationBuilder field = AggregationBuilders.range("age_range").field("age").addRange(20,30);
//        TopHitsAggregationBuilder limit = AggregationBuilders.topHits("Limit").size(5);
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(field).addAggregation(limit).build();
//        List<User> users = elasticsearchTemplate.queryForList(searchQuery, User.class);
//        users.forEach(user -> System.out.println(user));
//    }

   //match
    @Test
    public void match(){
        NativeSearchQuery address = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("address", "891"))
                .withPageable(PageRequest.of(0, 10))
                .build();
        List<User> users = elasticsearchTemplate.queryForList(address, User.class);
        users.forEach(user -> System.out.println(user));
    }

    //term查询 精确搜索
    @Test
    public void term() {
        NativeSearchQuery age = new NativeSearchQueryBuilder()
                .withQuery(termQuery("age", 23))
                .withPageable(PageRequest.of(0, 50))
                .build();
        List<User> users = elasticsearchTemplate.queryForList(age, User.class);
        users.forEach(user -> System.out.println(user));
    }


    //query range
    @Test
    public void queryRange() {
        NativeSearchQuery age = new NativeSearchQueryBuilder()
                .withQuery(rangeQuery("age").gte(10).lte(20))
                .withPageable(PageRequest.of(0, 50))
                .build();
        List<User> users = elasticsearchTemplate.queryForList(age, User.class);
        users.forEach(user -> System.out.println(user));
    }

    //bool
    @Test
    public void bool() {
        NativeSearchQuery age = new NativeSearchQueryBuilder()
                .withQuery(boolQuery()
                        .filter(rangeQuery("age").gte(10).lte(20))
                        .must(matchQuery("gender","F")))
                .withPageable(PageRequest.of(0, 50))
                .build();
        List<User> users = elasticsearchTemplate.queryForList(age, User.class);
        users.forEach(user -> System.out.println(user));
    }



















}