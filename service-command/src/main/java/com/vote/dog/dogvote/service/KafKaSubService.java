package com.vote.dog.dogvote.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vote.dog.dogvote.Enum.VoteType;
import com.vote.dog.dogvote.dto.DogVoteEvent;
import com.vote.dog.dogvote.entity.DogEntity;
import com.vote.dog.dogvote.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class KafKaSubService
{
  private final DogRepository dogRepository;
  private final RedisService redisService;

  @KafkaListener(topics = "dog-vote-topic", groupId = "group-id-dog")

  public void handleDogVoteEvent(ConsumerRecord<String, String> record) throws JsonProcessingException
  {

    // 이벤트 처리 로직 작성
    ObjectMapper objectMapper = new ObjectMapper();
    DogVoteEvent voteEvent = objectMapper.readValue(record.value(), DogVoteEvent.class);

    VoteType voteType = voteEvent.getVoteType();

    DogEntity dogEntity = dogRepository.findById(voteEvent.getDogId()).orElseThrow(() -> new EntityNotFoundException("No Entitiy"));

    // 투표 이벤트 또는 투표 취소 이벤트에 대한 처리 작성
    if (voteType == VoteType.VOTE)
    {
      dogEntity.upCount();

    } else if (voteType == VoteType.CANCEL_VOTE)
    {
      dogEntity.downCount();
    }
    dogRepository.saveAndFlush(dogEntity);

    redisService.removeDogRedisList(dogEntity.getId());
  }



  @CacheEvict(value = "dogList", allEntries = true, cacheManager = "dogCacheManager")
  public void removeDogRedisList()
  {

  }


}