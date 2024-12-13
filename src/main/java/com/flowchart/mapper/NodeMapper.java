package com.flowchart.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flowchart.dto.NodeDto;
import com.flowchart.model.Node;

@Component
public class NodeMapper {

	@Autowired
	private ModelMapper modelMapper;

	public Node convertDtoToEntity(NodeDto nodeDto) {
		return modelMapper.map(nodeDto, Node.class);
	}

	public NodeDto convertEntityToDto(Node node) {
		return modelMapper.map(node, NodeDto.class);
	}
}
