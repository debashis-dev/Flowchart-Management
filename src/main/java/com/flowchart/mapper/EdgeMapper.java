package com.flowchart.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flowchart.dto.EdgeDto;
import com.flowchart.model.Edge;

@Component
public class EdgeMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public Edge convertDtoToEntity(EdgeDto edgeDto) {
		return modelMapper.map(edgeDto, Edge.class);
	}
	
	public EdgeDto convertEntityToDto(Edge edge) {
		return modelMapper.map(edge, EdgeDto.class);
	}
}
