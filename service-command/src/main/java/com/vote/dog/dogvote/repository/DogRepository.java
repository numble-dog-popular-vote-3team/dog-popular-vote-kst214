package com.vote.dog.dogvote.repository;

import com.vote.dog.dogvote.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DogRepository extends JpaRepository<DogEntity,Long>
{
}
