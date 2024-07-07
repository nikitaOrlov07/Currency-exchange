package com.example.currencyexchangeapp.Config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.Base64;

// class for encoding api key
@Component
@ConfigurationPropertiesBinding
public class Base64DecodeConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(source);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            return source;
        }
    }
}