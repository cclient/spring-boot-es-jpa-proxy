package com.example.dao.sql;

import com.example.entity.Business;
import com.example.entity.Community;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository("businessRepository")
public interface BusinessRepository extends org.springframework.data.repository.PagingAndSortingRepository<Community, Long> {
    @Query("select u from Business u")
    Stream<Business> searchAll();

    @Async
    Future<Business> findByName(String name);
}
