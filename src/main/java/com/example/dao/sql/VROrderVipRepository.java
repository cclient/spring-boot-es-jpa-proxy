package com.example.dao.sql;

import com.example.entity.VROrderVip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by aa on 2016/8/25.
 */
@Repository("vrOrderVipRepository")
public interface VROrderVipRepository extends PagingAndSortingRepository<VROrderVip, Integer> {
    /*
    订单金额总数
     */
    @Query("select sum (u.money) from VROrderVip u")
    Double sumMoney();

    @Query("select sum (u.money) from VROrderVip u where u.status in ?1")
    Double sumMoneyByStatusIn(Collection<Integer> statuss);

    /*
    付费成功的订单
    */
    @Query("select sum (u.money) from VROrderVip u where u.status in (2,3)")
    Double sumSuccessMoney();

    /*
    查询用户的订单
     */
    Collection<VROrderVip> queryByUid(Integer uid);
}
