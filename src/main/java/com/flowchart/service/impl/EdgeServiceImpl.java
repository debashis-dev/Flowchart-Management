package com.flowchart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flowchart.dto.EdgeDto;
import com.flowchart.exception.NoOutgoingEdgesException;
import com.flowchart.mapper.EdgeMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.repository.EdgeRepository;
import com.flowchart.service.EdgeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EdgeServiceImpl implements EdgeService {
	
	private final EdgeRepository edgeRepository;

	private final EdgeMapper edgeMapper;
	
	public Edge dtoToEntity(EdgeDto edgeDto) {
		return edgeMapper.convertDtoToEntity(edgeDto);
	}
	
	public List<Edge> saveAllEdges(List<Edge> edges){
		return edgeRepository.saveAll(edges);
	}

	public Edge getEdgeByLabelAndFlowchartId(String label, Long flowchartId) {
		return edgeRepository.findByLabelAndFlowchartId(label, flowchartId).orElse(null);
	}
	
	public List<Edge> getOutgoingEdges(Flowchart flowchart, Long nodeId) {

		List<Edge> outgoingEdges = new ArrayList<>();
		for (Edge edge : flowchart.getEdges()) {
			if (edge.getFromNodeId().equals(nodeId)) {
				outgoingEdges.add(edge);
			}
		}
		if (!outgoingEdges.isEmpty()) {
			return outgoingEdges;
		}
		throw new NoOutgoingEdgesException(
				"No outgoing edges for flowchart with id: " + flowchart.getId() + " and node with id: " + nodeId);
	}
}
