package com.vote.dog.dogvote.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vote.dog.dogvote.dto.DogVoteEvent;
import com.vote.dog.dogvote.service.KafkaPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DogApiController
{
  private final KafkaPubService kafkaPubService;

  @PostMapping("/vote")
  public Map<String,Object> vote(@RequestBody DogVoteEvent postVote) throws JsonProcessingException
  {


    kafkaPubService.voteForDog(postVote);

    return new HashMap<>();

  }

}
