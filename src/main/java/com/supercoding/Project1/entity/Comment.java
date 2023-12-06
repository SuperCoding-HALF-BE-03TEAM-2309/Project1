package com.supercoding.Project1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "comment")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value="댓글")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    @ApiModelProperty(value="ID", required = false)
    private Long id;

    @Column(name="post_id", nullable = false)
    @ApiModelProperty(value="Post ID", required = true, example = "1")
    private Long postId;

    @Column(length = 50, nullable = false)
    @ApiModelProperty(value = "email", required = true, example = "user@mail.com")
    private String email;

    @Lob // Large Object for text
    @Column(columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(value="내용",required = true, example = "내용")
    private String content;

    @Column(name = "created_at", nullable = false)
    @ApiModelProperty(hidden = true)
    private Timestamp createAt;

    @Column(name = "updated_at", nullable = false)
    @ApiModelProperty(hidden = true)
    private Timestamp updateAt;
/*
    @Transient
    private String nickname;
 */
}
