package com.vote.dog.dogvote.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vote.dog.dogvote.dto.Dog;
import com.vote.dog.dogvote.dto.RestResponsePage;
import com.vote.dog.dogvote.entity.DogEntity;
import com.vote.dog.dogvote.entity.QDogEntity;
import com.vote.dog.dogvote.repository.DogRepository;
import com.vote.dog.dogvote.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DogService
{
  private final DogRepository dogRepository;
  private final CacheManager dogCacheManager;
  private final EntityManager entityManager;
  private final QDogEntity qDogEntity = QDogEntity.dogEntity;

  @Cacheable(value = "dogList",
          key = "#type.concat('-').concat(#keyword).concat('-').concat(#page).concat('-').concat(#size).concat('-').concat(#orderBy)",
          cacheManager = "dogCacheManager")
  public RestResponsePage<Dog> list(Model model, String type, String keyword, Integer page, Integer size, String orderBy)
  {

    Pageable pageable = null;
    if (page != null && size != null)
    {
      pageable = PageRequest.of(page <= 0 ? 0 : page - 1, size, Sort.by("id").descending());
    }
    ;
    Map searchMap = new HashMap();
    if (!StringUtils.isEmpty(keyword))
    {
      searchMap.put("keyword", keyword);
      searchMap.put("type", type);
    }

    Map orderMap = new HashMap();
    if (!StringUtils.isEmpty(orderBy))
    {
      orderMap.put("orderBy",orderBy);
    }

    List<Dog> dogList = null;

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    dogList = queryFactory.select(Projections.fields(Dog.class, qDogEntity.id, qDogEntity.description, qDogEntity.name, qDogEntity.count, qDogEntity.image))
            .from(qDogEntity)
            .where(isSearchOption(searchMap))
            .offset((pageable.getPageNumber()) * pageable.getPageSize())
            .limit(pageable.getPageSize())
            .orderBy(orderByOption(orderMap)
                    // qDogEntity.id.desc()
            )
            .fetch();

    Long dogListCnt = queryFactory.select()
            .from(qDogEntity)
            .where(isSearchOption(searchMap))
            .fetchCount();

    return new RestResponsePage<>(dogList, pageable, dogListCnt);  // (6) PageImpl 반환
    //return dogList;
  }

  private OrderSpecifier<?> orderByOption(Map orderMap)
  {
    if (ObjectUtils.isEmpty(orderMap.get("orderBy")))
    {
      return qDogEntity.id.desc();
    }
    String orderBy = (String) orderMap.get("orderBy");

    if(orderBy.equals("id") )return qDogEntity.id.desc();
    if(orderBy.equals("name") )return qDogEntity.name.desc();
    if(orderBy.equals("like") )return qDogEntity.count.desc();

    return qDogEntity.id.desc();
  }

  @Cacheable(value = "dogDetail",
          key = "#id",
          cacheManager = "dogCacheManager")
  public Dog detail(Long id)
  {
    Optional<DogEntity> dogEntity = dogRepository.findById(id);
    return dogEntity.map(entity -> ObjectMapperUtils.map(entity, Dog.class)).orElse(null);
  }

  public BooleanBuilder isSearchOption(Map searchMap)
  {
    BooleanBuilder where = new BooleanBuilder();
    if (searchMap != null)
    {

      if (searchMap.containsKey("keyword") && searchMap.get("keyword") != null)
      {
        if (searchMap.containsKey("type") && searchMap.get("type") != null)
          if (searchMap.get("type").equals("name"))
          {
            where.and(qDogEntity.name.like('%' + searchMap.get("keyword").toString() + '%'));
          } else if (searchMap.get("type").equals("description"))
          {
            where.and(qDogEntity.description.like('%' + searchMap.get("keyword").toString() + '%'));
          }
      }

    }

    return where;
  }

  @CacheEvict(value = "dogList", allEntries = true)
  public void deleteDog(Long dogId)
  {

    DogEntity dogEntity = dogRepository.findById(dogId).orElseThrow(() -> new EntityNotFoundException("Order Not Found"));
    dogRepository.delete(dogEntity);

  }


  @CacheEvict(value = "dogList", allEntries = true, cacheManager = "dogCacheManager")
  public void removeDogRedisList()
  {

  }


  @CacheEvict(value = "dogList", allEntries = true, cacheManager = "dogCacheManager")
  public void saveDog()
  {
    dogRepository.save(DogEntity.builder()
            .name("d")
            .count(0L)
            .build());

  }

  @CachePut(value = "Order", key = "#id", cacheManager = "dogCacheManager")
  public void updateDog()
  {
    DogEntity dogEntity = dogRepository.save(DogEntity.builder()
            .name("d")
            .count(0L)
            .build());

  }

}
