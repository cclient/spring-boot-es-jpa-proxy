package com.example.dao.sql;

import com.example.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by aa on 2016/8/25.
 */
@Repository("memberRepository")
public interface MemberRepository extends PagingAndSortingRepository<Member, Integer> {
    List<Member> queryByName(String name);

    Page<Member> queryByName(String name, Pageable page);

    List<Member> findByIdBetween(int start, int end);

    List<Member> findByIdIsIn(List<String> ids);

    @Async
    Future<Member> findByName(String name);

    Member getById(Long Id);

    /*
    某时间后所有的注册用户
     */
    Integer countByReigsttimeAfter(Date start);

    Integer countByIdAfter(Integer start);
}
