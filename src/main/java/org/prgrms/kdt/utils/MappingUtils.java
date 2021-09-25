package org.prgrms.kdt.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

public class MappingUtils {

    private MappingUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static <S, T> List<T> mapList(List<S> source,
        Class<T> targetClass, ModelMapper modelMapper) {
        return source
            .stream()
            .map(element -> modelMapper.map(element, targetClass))
            .collect(Collectors.toList());
    }

}
