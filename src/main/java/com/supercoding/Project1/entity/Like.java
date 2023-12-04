package com.supercoding.Project1.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "`likes`")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel(value="좋아요")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_id")
    @ApiModelProperty(value="ID", required = false)
    private Long id;

    @Column(name="type", nullable = false)
    @ApiModelProperty(hidden = true)
    private Integer type;

    @Column(name="post_id", nullable = true)
    @ApiModelProperty(value="Post ID", required = true)
    private Long postId;

    @Column(length = 50, nullable = false)
    @ApiModelProperty(value="email", required = true)
    private String email;

    @Column(name="comment_id", nullable = true)
    @ApiModelProperty(value="Comment ID", required = true )
    private Long commentId;
}
