package com.vote.dog.dogvote.dto;

import com.vote.dog.dogvote.Enum.VoteType;
import lombok.*;


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
