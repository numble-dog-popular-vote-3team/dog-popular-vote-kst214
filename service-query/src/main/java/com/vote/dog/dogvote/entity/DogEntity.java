package com.vote.dog.dogvote.entity;

import lombok.*;

import javax.persistence.*;



@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DOG")
public class DogEntity
{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "bigint(19) comment 'SEQ'")
  private Long id;

  @Column(name = "NAME" ,nullable = true, columnDefinition = "varchar(30) comment '이름'")
  private String name;

  @Column(name = "DESCRIPTION" ,nullable = true, columnDefinition = "varchar(1000) comment '설명'")
  private String description;

  @Column(name = "IMAGE" ,nullable = true, columnDefinition = "varchar(30) comment '이미지'")
  private String image;

  @Column(name = "COUNT" ,nullable = true, columnDefinition = "bigint(19) comment '좋아요'")
  private Long count;


  public  void upCount(){
    this.count ++;
  }

  public  void downCount(){
    this.count --;
  }
}
