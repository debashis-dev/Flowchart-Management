package com.flowchart.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

//	public Node nodeDtoToEntity(NodeDto nodeDto) {
//		Node node = new Node();
//		node.setLabel(nodeDto.getLabel());
//		return node;
//	}
//	
//	public Edge edgeDtoToEntity(EdgeDto edgeDto) {
//		Edge edge = new Edge();
//		edge.setLabel(edgeDto.getLabel());
//		edge.setFromNodeId(edgeDto.getFromNodeId());
//		edge.setToNodeId(edgeDto.getToNodeId());
//		return edge;
//	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
