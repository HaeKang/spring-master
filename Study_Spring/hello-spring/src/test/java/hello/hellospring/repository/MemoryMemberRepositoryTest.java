package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 한 함수의 테스트가 끝나면 호출되는 함수 ~> AfterEach
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    // 동작하는지 확인
    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // Optional이므로 get으로 값 얻어옴
        // get 안쓰면 Optional type으로 return 받아야함
        Member result = repository.findById(member.getId()).get();

        // 단순 출력으로 결과 화긴하기
        System.out.println("result = " + (result == member));

        // assertions 기능
        // result랑 member랑 같은지 (expected, actual) ~ 다르면 빨간색으로 오류처럼 나타내줌~
        Assertions.assertEquals(member, result);

        // 요즘 많이 쓰는 방법
        // member가 result랑 똑같니~~~
        // 저 이상한 앞부분부터 Assertions까지 드래그해서 alt+enter 해서 static~~ 뭐시기 누르면 간단하게 assertThat만 써도 됨
        org.assertj.core.api.Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("SPRING1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("SPRING1");
        repository.save(member2);

        List<Member> result = repository.findAll();

        org.assertj.core.api.Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
