package com.vote.dog.dogvote.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * com.vote.dog.dogvote.dto
 * ㄴ RestResponsePage
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/27  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */

@JsonIgnoreProperties(ignoreUnknown = true,value = {"pageable"})
public class RestResponsePage<T> extends PageImpl<T>
{

  //private static final long serialVersionUID = 3248189030448292002L;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public RestResponsePage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number, @JsonProperty("size") int size,
                          @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                          @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first,
                          @JsonProperty("numberOfElements") int numberOfElements) {
    super(content, PageRequest.of(number, size), totalElements);
  }


  public RestResponsePage(@JsonProperty("content")  List<T> content, @JsonProperty("pageable")  Pageable pageable, @JsonProperty("totalPages") long total
  ) {

    super(content, pageable, total);
  }

  public RestResponsePage(List<T> content) {
    super(content);
  }

  public RestResponsePage() {
    super(new ArrayList<T>());
  }

}