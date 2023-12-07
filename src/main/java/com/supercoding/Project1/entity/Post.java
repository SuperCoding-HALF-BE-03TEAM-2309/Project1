package com.supercoding.Project1.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value = "게시글")
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // PK

    @Column(length = 50, nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String content; // 내용

    @Column(length = 50, nullable = false)
    @ApiModelProperty(value = "email", required = true, example = "user@mail.com")
    private String email; // 이메일


    @Column(name = "created_at", updatable = false)
    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    private Timestamp createAt; // 생성시간

    @Column(name = "updated_at", insertable = false)
    @ApiModelProperty(hidden = true)
    @UpdateTimestamp
    private Timestamp updateAt; // 업데이트 시간

    @Builder
    public Post(String title, String content, String email){
        this.title = title;
        this.content = content;
        this.email = email;
    }

    public void updatePost(String title, String content){
        this.title = title;
        this.content = content;
    }
}
