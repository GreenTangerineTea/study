package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)  // Junit 실행할 때 스프링이랑 엮어줘
@SpringBootTest // 스프링부트를 띄운 상태로
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;


    @Test
    @Rollback(value = false) // TEST는 디폴트가 ROLLBACK TRUE
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));

    }


    @Test
    public void 중복_회원_가입() throws Exception {

        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); // 예외가 발생해야 한다 !
        } catch (IllegalStateException e) {
            return;
        }
        //then
        fail("여기 오면 안된다!!");  // fail은 무조건 예외 발생하도록 하는 메소드임
    }
}