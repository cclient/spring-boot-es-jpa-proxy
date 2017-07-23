package com.example.service.sql;

/**
 *
 */

import com.example.dao.sql.MemberRepository;
import com.example.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("memberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    @Qualifier("memberRepository")
    private MemberRepository memberRepository;

    public Member getMember(Integer Id) {
        return this.memberRepository.findOne(Id);
    }
}
