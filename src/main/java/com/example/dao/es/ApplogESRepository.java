package com.example.dao.es;

import com.example.entity.Applog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository("applogESRepository")
public interface ApplogESRepository extends org.springframework.data.elasticsearch.repository.ElasticsearchRepository<Applog, Integer> {
    List<Applog> queryByVersionName(String name);

    Page<Applog> queryByVersionName(String name, Pageable page);

    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"message\" : \"?0\"}}}}")
    Page<Applog> findByName(String name, Pageable pageable);

    //    @Query("{\"match\": {\"describe\": \"?0\"}}")
    List<Applog> findByIdBetween(int start, int end);

    Applog getById(Long Id);
}
