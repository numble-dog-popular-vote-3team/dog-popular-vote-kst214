package com.vote.dog.dogvote.dto;

import com.vote.dog.dogvote.Enum.VoteType;
import lombok.*;

/**
 * com.vote.dog.dogvote.service
 * ㄴ DogVoteEvent
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/21  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DogVoteEvent
{
    private Long dogId;
    private VoteType voteType;



}
