package com.softplan.desafio.domain.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Mapper<K, V> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@SuppressWarnings("unchecked")
	public V domainToDto(K domain) {
		return (V) modelMapper.map(domain, getGenericClass(1));
	}

	@SuppressWarnings("unchecked")
	public K dtoToDomain(V dto) {
		return (K) modelMapper.map(dto, getGenericClass(0));
	}

	public List<V> domainToDto(List<K> domains) {
		return domains.stream().map(this::domainToDto).collect(Collectors.toList());
	}

	public List<K> dtoToDomain(List<V> dtos) {
		return dtos.stream().map(this::dtoToDomain).collect(Collectors.toList());
	}

	private Class<?> getGenericClass(Integer argument) {
		Type mySuperclass = getClass().getGenericSuperclass();
		Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[argument];
		String className = tType.toString().split(" ")[1];
		
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.error("Classe não encontrada:  "+className, e);
		}
		
		return null;
	}
}
