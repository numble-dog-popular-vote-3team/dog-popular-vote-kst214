package com.vote.dog.dogvote.service;

import com.vote.dog.dogvote.dto.Dog;
import com.vote.dog.dogvote.entity.DogEntity;
import com.vote.dog.dogvote.repository.DogRepository;
import com.vote.dog.dogvote.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ObjectUtils;

import javax.persistence.Cache;
import javax.persistence.EntityNotFoundException;

/**
 * com.vote.dog.dogvote.service
 * ㄴ test
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/28  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class RedisService
{
  private final DogRepository dogRepository;
  private final CacheManager dogCacheManager;
  @CacheEvict(value = "dogList", allEntries = true, cacheManager = "dogCacheManager")
  public void removeDogRedisList(Long dogId)
  {
    if (dogId != null)
    {
    updateDogRedisDetail(dogId);
    }
  }
  public void updateDogRedisDetail(Long id)
  {

    DogEntity dogEntity = dogRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Entity"));
    Dog dog = ObjectMapperUtils.map(dogEntity,Dog.class);
    dogCacheManager.getCache("dogDetail").put(id,dog);





  }

}
