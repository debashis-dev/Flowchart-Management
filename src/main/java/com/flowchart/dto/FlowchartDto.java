package com.flowchart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowchartDto {

	private String label;
	
	private List<NodeDto> nodes;
	
	private List<EdgeDto> edges;
}
