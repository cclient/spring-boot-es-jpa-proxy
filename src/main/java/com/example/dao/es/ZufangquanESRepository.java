package com.example.dao.es;

//import org.springframework.stereotype.Repository;
//import org.springframework.data.repository.Repository;
//import org.springframework.stereotype.Repository;

import com.example.entity.Zufangquan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 */
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")

//@EnableMongoRepositories
@org.springframework.stereotype.Repository("zufangquanESRepository")
//@Repository
//@EnableMongoRepositories

public interface ZufangquanESRepository extends org.springframework.data.elasticsearch.repository.ElasticsearchRepository<Zufangquan, Long> {
    List<Zufangquan> queryByName(String name);

    Page<Zufangquan> queryByName(String name, Pageable page);

    Zufangquan findByName(String name);

    //看结果，应该是全词匹配，查询分词结果在原始数据中全部存在，才会返回
    List<Zufangquan> findByDescribe(String describe);

    @Query("{\"match\": {\"describe\": \"?0\"}}")
    List<Zufangquan> searchByDescribe(String describe);

    List<Zufangquan> findByIdBetween(int start, int end);

    List<Zufangquan> findByIdIsIn(List<String> ids);
//    Integer update(Community community);
//    Integer update(Iterable<Community> communitys);
}
