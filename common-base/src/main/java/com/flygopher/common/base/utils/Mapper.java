package com.flygopher.common.base.utils;

import com.flygopher.common.base.pagination.PageResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper
                .getConfiguration()
                .setFieldMatchingEnabled(true)
                .setCollectionsMergeEnabled(false)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMethodAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <E, V> V map(E record, Class<V> object) {
        return modelMapper.map(record, object);
    }

    public static <T, V> PageResponse<V> map(PageResponse<T> pagedResource, Class<V> responseType) {
        List<V> responses =
                pagedResource.getContent().stream()
                        .map(res -> map(res, responseType))
                        .collect(Collectors.toList());
        return new PageResponse<>(pagedResource, responses);
    }

    public static <E, V> V map(E sourceObj, V destinationObj) {
        modelMapper.map(sourceObj, destinationObj);
        return destinationObj;
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
