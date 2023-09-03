package com.ujjwalkhatri.primenumbers.converters;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, Algorithm> {

    @Override
    public Algorithm convert(String source) {
        return Algorithm.valueOf(source.toUpperCase());
    }

}
