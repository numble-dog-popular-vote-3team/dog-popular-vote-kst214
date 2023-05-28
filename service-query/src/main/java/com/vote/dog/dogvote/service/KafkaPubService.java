package com.vote.dog.dogvote.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vote.dog.dogvote.dto.DogVoteEvent;
import com.vote.dog.dogvote.entity.DogEntity;
import com.vote.dog.dogvote.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;


@Service
@RequiredArgsConstructor

public class KafkaPubService
{

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void voteForDog(DogVoteEvent voteEvent) throws JsonProcessingException
  {
    // 강아지 투표 이벤트 생성

    // 카프카로 이벤트 Produce

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = objectMapper.writeValueAsString(voteEvent);


     kafkaTemplate.send("dog-vote-topic", jsonString);


  }


}

