package com.flowchart.service;

import java.util.List;

import com.flowchart.dto.EdgeDto;
import com.flowchart.dto.FlowchartDto;
import com.flowchart.dto.NodeDto;
import com.flowchart.model.Edge;

public interface FlowchartService {

	public FlowchartDto createFlowchart(FlowchartDto flowchartDto);
	
	public FlowchartDto getFlowchart(Long id);
	
	public FlowchartDto updateFlowchart(Long id, List<NodeDto> nodeDtos, List<EdgeDto> edgeDtos, String operation);
	
	public String deleteFlowchart(Long id);
	
	public List<Edge> getOutgoingEdges(Long flowchartId, Long nodeId);
	
	public List<Long> getAllConnectedNodes(Long flowchartId, Long nodeId);
}
