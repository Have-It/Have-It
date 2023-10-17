package com.meta.game.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "games")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private int winCount;

    private String nickName;
}
