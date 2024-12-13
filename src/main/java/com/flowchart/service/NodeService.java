package com.flowchart.service;

import java.util.List;

import com.flowchart.dto.NodeDto;
import com.flowchart.model.Flowchart;
import com.flowchart.model.Node;

public interface NodeService {

	public Node dtoToEntity(NodeDto nodeDto);
	
	public List<Node> saveAllNodes(List<Node> nodes);
	
	public Node getNodeByLabelAndFlowchartId(String label, Long flowchartId);
	
	public List<Long> getAllConnectedNodes(Flowchart flowchart, Long nodeId);
}
