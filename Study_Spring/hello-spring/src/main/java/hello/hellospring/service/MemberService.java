package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    // 이러면 약간 memoryMemberRepo랑 MemberService랑 둘이 각각 다른 리포 쓰는 느낌나자너
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 외부에서 repo 넣어주도록 설정
    private final MemoryMemberRepository memoryMemberRepository;

    @Autowired
    public MemberService(MemoryMemberRepository memoryMemberRepository){
        this.memoryMemberRepository = memoryMemberRepository;
    }

    // 회원 가입
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        memoryMemberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        // 같은 이름이 있는 중복 회원 X
        memoryMemberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    public List<Member> findMembers(){
        return memoryMemberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memoryMemberRepository.findById(memberId);
    }

}
