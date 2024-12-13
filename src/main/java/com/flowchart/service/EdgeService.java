package com.flowchart.service;

import java.util.List;

import com.flowchart.dto.EdgeDto;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;

public interface EdgeService {

	public Edge dtoToEntity(EdgeDto edgeDto);
	
	public List<Edge> saveAllEdges(List<Edge> edges);
	
	public Edge getEdgeByLabelAndFlowchartId(String label, Long flowchartId);
	
	public List<Edge> getOutgoingEdges(Flowchart flowchart, Long nodeId);
}
