package com.meta.member.entity;

import com.meta.member.enums.MemberRole;
import com.meta.member.enums.MemberState;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Data
@Entity(name = "members")
@Getter
@NoArgsConstructor
@DynamicInsert
public class Member{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private String profileImage;

    private String settingImage;

    @NotNull
    private String nickName;

    private String socialtype;    // null, kakao

    private String state;   // ACTIVE, RESIGNED
    private String role;    // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    private int characterWeight;
//    private int characterDarkcircle;
//    private int coin;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @Builder
    public Member(String email, String profileImage, String settingImage, String password, String nickName, String socialtype, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.socialtype = socialtype;
        this.state = MemberState.ACTIVE.name();
        this.role = MemberRole.ROLE_USER.name();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.profileImage = profileImage;
        this.settingImage = settingImage;
//        this.coin = coin;
//        this.characterDarkcircle = characterDarkcircle;
//        this.characterWeight = characterWeight;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setMember(this);
    }
}
