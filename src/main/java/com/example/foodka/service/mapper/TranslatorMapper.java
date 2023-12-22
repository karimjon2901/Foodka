package com.example.foodka.service.mapper;

import com.example.foodka.dto.TranslatorDto;
import com.example.foodka.model.Translator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslatorMapper extends CommonMapper<TranslatorDto, Translator>{
}
