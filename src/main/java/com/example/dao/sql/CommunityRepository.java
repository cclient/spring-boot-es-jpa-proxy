package com.example.dao.sql;

import com.example.entity.Community;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

@org.springframework.stereotype.Repository("CommunityRepository")
public interface CommunityRepository extends org.springframework.data.repository.PagingAndSortingRepository<Community, Long> {
    Community getById(Long Id);

    @Query("select u from Community u")
    Stream<Community> searchAll();
}
