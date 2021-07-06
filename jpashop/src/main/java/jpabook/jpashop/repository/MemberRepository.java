package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 스프링 빈- 그중에서도 리포지토리로 등록
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // JPA가 제공하는 표준 어노테이션  --> RequiredArgsConstructor 쓰면 안써도 됨.
    private final EntityManager em; // 스프링이 엔티티매니저를 만들어서 인젝션 해줌

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);

    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)  // 얘(JPQL)는 Entity(객체)를 대상으로 쿼리 돌리는거
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
