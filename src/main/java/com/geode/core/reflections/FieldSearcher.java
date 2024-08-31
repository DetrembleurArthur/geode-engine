package com.geode.core.reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FieldSearcher {

    public static List<Field> getFields(Class<? extends Annotation> annotationClass, Object object) {
        return Arrays.stream(object.getClass().getFields())
                .filter(field -> field.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }
}
