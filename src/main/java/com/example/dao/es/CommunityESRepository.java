package com.example.dao.es;

import com.example.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.List;

/**
 *
 */
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")

@org.springframework.stereotype.Repository("communityESRepository")
//@Repository
//@EnableMongoRepositories
public interface CommunityESRepository extends org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository<Community, Long> {
    List<Community> queryByName(String name);

    Page<Community> queryByName(String name, Pageable page);

    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"message\" : \"?0\"}}}}")
    Page<Community> findByName(String name, Pageable pageable);
}
