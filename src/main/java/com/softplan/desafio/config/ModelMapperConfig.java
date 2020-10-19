package com.softplan.desafio.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		// Necessario para eliminar matches multiple source property hierarchies
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	    return modelMapper;
	}
}
