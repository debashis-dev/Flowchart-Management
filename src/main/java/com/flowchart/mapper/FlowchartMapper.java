package com.flowchart.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flowchart.dto.FlowchartDto;
import com.flowchart.model.Flowchart;

@Component
public class FlowchartMapper {

	@Autowired
	private ModelMapper modelMapper;

	public FlowchartDto convertEntityToDto(Flowchart flowchart) {
		return modelMapper.map(flowchart, FlowchartDto.class);
	}

	public Flowchart convertDtoToEntity(FlowchartDto flowchartDto) {
		return modelMapper.map(flowchartDto, Flowchart.class);
	}
}
