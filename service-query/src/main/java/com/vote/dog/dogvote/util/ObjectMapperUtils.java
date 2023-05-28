package com.vote.dog.dogvote.util;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
@Component
public class ObjectMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();
    static {
        modelMapper = new ModelMapper();
        // 매핑 연결 - STRICT전략_타입,컬럼명 같을경우만 매핑.
       // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }



    /**
     * <p>Note1: Obj -> Obj ( rst =>  Entity ->  DTO or DTO ->  Entity  </p>
     * @hitroy
     *        nullExcepion -> null 예외추가
     * @param inputObj   T type of Object
     * @param outClass   D type
     * @return D type Obj
     */
    public static <D, T> D map(final T inputObj, Class<D> outClass) {
        D outObj = ( inputObj == null ?  null : modelMapper.map(inputObj, outClass) );
        return outObj;
    }



    /**
     * <p>Note2: List<T> -> List<D> ( rst =>  List<Entity> ->  List<DTO> or List<DTO> ->  List<Entity> ) s</p>
     * @history
     *        nullExcepion -> null 예외추가
     * @param inputList  T type of list
     * @param outCLass   D type
     * @return  D type List
     */
    public static <D, T> List<D> mapAll(final Collection<T> inputList, Class<D> outCLass) {

        List<D> outObjList = ( inputList == null ? null : inputList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList())
        );

        return outObjList;
    }


}