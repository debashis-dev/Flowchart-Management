package com.flowchart.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.flowchart.dto.NodeDto;
import com.flowchart.mapper.NodeMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.model.Node;
import com.flowchart.repository.NodeRepository;
import com.flowchart.service.NodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService{
	
	private final NodeRepository nodeRepository;

	private final NodeMapper nodeMapper;
	
	public Node dtoToEntity(NodeDto nodeDto) {
		return nodeMapper.convertDtoToEntity(nodeDto);
	}
	
	public List<Node> saveAllNodes(List<Node> nodes){
		return nodeRepository.saveAll(nodes);
	}
	public Node getNodeByLabelAndFlowchartId(String label, Long flowchartId) {
		return nodeRepository.findByLabelAndFlowchartId(label, flowchartId).orElse(null);
	}
	
	public List<Long> getAllConnectedNodes(Flowchart flowchart, Long nodeId) {

		// Create a set for storing the nodes which are connected
		List<Long> visited = new ArrayList<>();
		
		// Create a queue for checking each node if they are connected or not
		Queue<Long> queue = new LinkedList<>();
		
		queue.add(nodeId);
		visited.add(nodeId);

		while (!queue.isEmpty()) {
			Long currentNode = queue.poll();
			for (Edge edge : flowchart.getEdges()) {
				if (edge.getFromNodeId().equals(currentNode) && !visited.contains(edge.getToNodeId())) {
					queue.add(edge.getToNodeId());
					visited.add(edge.getToNodeId());
				}
			}
		}

		return visited;
	}
}
