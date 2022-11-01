package com.youprice.onion.service.member.impl;

import com.youprice.onion.dto.member.*;
import com.youprice.onion.entity.member.Member;
import com.youprice.onion.entity.member.Role;
import com.youprice.onion.repository.member.BlockRepository;
import com.youprice.onion.repository.member.FollowRepository;
import com.youprice.onion.repository.member.MemberRepository;
import com.youprice.onion.service.member.MemberService;
import com.youprice.onion.util.ImageUtil;
import com.youprice.onion.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BlockRepository blockRepository;
    private final FollowRepository followRepository;

    @Value("$(file.path}")
    private String uploadFolder;

//    @Autowired
//    private final ModelMapper modelMapper;

    //회원가입
    @Transactional
    @Override
    public Long saveMember(MemberJoinDTO memberJoinDTO) throws IOException {

        memberJoinDTO.setMemberImageName(ImageUtil.store(memberJoinDTO.getProfileImg(), "member"));

        memberJoinDTO.setPwd(passwordEncoder.encode(memberJoinDTO.getPwd())); //패스워드 암호화 저장
        return memberRepository.save(memberJoinDTO.toEntity()).getId();
    }

    //회원가입 시 유효성 및 중복 체크
    //유효성 검사에 실패한 필드는 key 값과 에러 메시지를 응답
    //Key : valid_{dto 필드명}
    //Message : dto에서 작성한 message 값
    @Transactional(readOnly = true)
    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        //유효성 검사에 실패한 필드 목록을 받음
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    //회원정보 수정
    @Transactional
    @Override
    public void modify(MemberModifyDTO memberModifyDTO) {
        Member findById = memberRepository.findById(memberModifyDTO.getId()).orElseThrow(() ->
                 new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        Member findByEmail = memberRepository.findByEmail(memberModifyDTO.getEmail()).orElse(null);
        Member findByNickname = memberRepository.findByNickname(memberModifyDTO.getNickname()).orElse(null);

        if (findByEmail != null && findByEmail.getId() != memberModifyDTO.getId()) {
            throw  new IllegalArgumentException("이미 존재하는 이메일 입니다. 다시 입력해 주세요.");
        } else if (findByNickname != null && findByNickname.getId() != memberModifyDTO.getId()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다. 다시 입력해 주세요.");
        } else {
            String encPwd = passwordEncoder.encode(memberModifyDTO.getPwd());
            findById.modify(encPwd, memberModifyDTO.getNickname(), memberModifyDTO.getTel(), memberModifyDTO.getPostcode(),
                    memberModifyDTO.getAddress(), memberModifyDTO.getDetailAddress(), memberModifyDTO.getExtraAddress(), memberModifyDTO.getEmail());
        }
    }

    //아이디 찾기
    @Override
    public MemberDTO findId(String email) {
        return memberRepository.findByEmail(email).map(MemberDTO::new).orElse(null);
    }

    @Override
    public int countId(String email) {
        return memberRepository.countByEmail(email);
    }

    //비밀번호 찾기
    @Override
    public MemberDTO findPwd(String email) throws Exception {

        //회원정보 불러오기
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            return null;
        }
        //이메일 전송

        //임시 비밀번호 생성
        String tempPwd = UUID.randomUUID().toString().replace("-", "");
        tempPwd = tempPwd.substring(0, 10);

        member.findPwd(tempPwd);

        //이메일 전송
        MailUtil.sendMail(member);

        //암호화된 임시 비밀번호 저장
        member.findPwd(passwordEncoder.encode(member.getPwd()));
        memberRepository.save(member);

        return new MemberDTO(member);
    }

    @Transactional
    @Override
    public void withdraw(String userId) {
        Member member = memberRepository.findByUserId(userId).orElse(null);
        member.withdraw(Role.valueOf("WITHDRAWAL"));
        memberRepository.save(member);
    }

    @Override
    public MemberDTO getMemberDTO(Long memberId) {
        return memberRepository.findById(memberId).map(MemberDTO::new).orElse(null);
    }

    @Override
    public MemberDTO getMemberDTO(Long memberId, Long sessionId) { //memberId: 타겟유저, sessionId: 로그인 한 유저
        return memberRepository.findById(memberId).map(member -> {
            MemberDTO memberDTO = new MemberDTO(member);

            memberDTO.setFollowCheck(followRepository.existsByMemberIdAndTargetId(sessionId, memberId));
            memberDTO.setBlockCheck(blockRepository.existsByMemberIdAndTargetId(sessionId, memberId));

            return memberDTO;
        }).orElse(null);
    }

/*
    //프로필사진 수정
    @Transactional
    @Override
    public void profileImageUpdate(Long memberId, MemberModifyDTO memberModifyDTO, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String memberImageName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + memberImageName);

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            return new IllegalArgumentException("프로필 사진 수정 실패 : 존재하지 않는 회원입니다.");
        });
    }
*/

/*  차단
    @Override
    public MemberDTO getMemberDTO(Long memberId) {

        Long sender = 1L;
        Long target = 2L;
        if (blockRepository.existsBlockByMemberAndTarget(target,sender)) {
            // 차단되는 로직
            throw new NullPointerException();
        }
        // 채팅로직
        return null;
    }

    //차단 추가
    public void makeBlock(Long memberId, Long targetId) {

        Member targetingMember = memberRepository.findById(memberId).orElseThrow(NullPointerException::new);
        Member targetedMember = memberRepository.findById(targetId).orElseThrow(NullPointerException::new);

        Block block = Block.builder()
                .member(targetingMember)
                .target(targetedMember)
                .build();

        blockRepository.save(block);
    }
*/

}
