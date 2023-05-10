package com.plat.plantsplanet.entity;

import com.plat.plantsplanet.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true) //unique 제약조건 추가
    private String memberEmail;
    @Column
    private String memberPassword;
    @Column(unique = true) // unique
    private String memberName;
    @Column
    private String memberBirth;
    @Column
    private String memberNum;

    //처음에 회원가입할 땐 id가 자동으로 부여되는데
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        return memberEntity;
    }

    //수정할 땐 이미 id가 있으니까 그걸 가져오는게 필요함
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberNum(memberDTO.getMemberNum());
        return memberEntity;
    }
}
