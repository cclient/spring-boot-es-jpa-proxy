package com.example.dao.sql;


import com.example.entity.Community;
import com.example.entity.Shop;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;


@org.springframework.stereotype.Repository("ShopRepository")
public interface ShopRepository extends org.springframework.data.repository.PagingAndSortingRepository<Community, Long> {
    @Query("select u from Shop u")
    Stream<Shop> searchAll();
}
