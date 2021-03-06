package study.datajpa.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.datajpa.dto.MemberDto;
import study.datajpa.datajpa.entity.Member;
import study.datajpa.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/member1/{id}")
    public String findMember1(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUserName();
    }

    @GetMapping("/member2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        return member.getUserName();
    }

    @GetMapping("/members")
    public Page<MemberDto> findPageMember(@PageableDefault(size=10) Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUserName(), null));
        return map;
    }

//    @PostConstruct
    public void init(){
        for (int i=1; i<=100; i++){
            memberRepository.save(new Member("user"+i, i));
        }
    }


}
