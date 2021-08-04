package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 롬복 - 생성자 인잭션 바로 해줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired // 생성자 인젝션이 좋다!
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
    * 회원 가입
    * */

    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    @Transactional
    private void validateDuplicateMember(Member member) {
        // 문제가 생기면 EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 (이름의) 회원입니다.");
        }

    }

    // 회원 전체 조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 한 회원만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
