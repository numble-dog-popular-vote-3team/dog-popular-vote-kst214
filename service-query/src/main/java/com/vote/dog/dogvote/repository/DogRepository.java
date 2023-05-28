package com.vote.dog.dogvote.repository;

import com.vote.dog.dogvote.entity.DogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * com.vote.dog.dogvote.repository
 * ㄴ DogRepository
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/18  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */
public interface DogRepository extends JpaRepository<DogEntity,Long>
{
}
