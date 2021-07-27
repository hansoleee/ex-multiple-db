package com.hansoleee.exmultipledb.member.controller;

import com.hansoleee.exmultipledb.member.domain.Member;
import com.hansoleee.exmultipledb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/members")
    public Long save(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }
}
