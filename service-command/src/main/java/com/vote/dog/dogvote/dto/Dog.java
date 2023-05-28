package com.vote.dog.dogvote.dto;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.Column;

/**
 * com.vote.dog.dogvote.dto
 * ㄴ Dog
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/19  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */
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
