package com.vote.dog.dogvote.dto;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.Column;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Dog
{

  private Long id;
  private String name;
  private String description;
  private String image;
  private Long count;

}
