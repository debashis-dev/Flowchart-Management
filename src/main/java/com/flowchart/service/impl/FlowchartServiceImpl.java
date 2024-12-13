package com.flowchart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flowchart.dto.EdgeDto;
import com.flowchart.dto.FlowchartDto;
import com.flowchart.dto.NodeDto;
import com.flowchart.exception.EdgeAlreadyExistsException;
import com.flowchart.exception.EdgeNotFoundException;
import com.flowchart.exception.FlowchartAlreadyExistException;
import com.flowchart.exception.FlowchartNotFoundException;
import com.flowchart.exception.NodeAlreadyExistsException;
import com.flowchart.exception.NodeNotFoundException;
import com.flowchart.mapper.FlowchartMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.model.Node;
import com.flowchart.repository.FlowchartRepository;
import com.flowchart.service.EdgeService;
import com.flowchart.service.FlowchartService;
import com.flowchart.service.NodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlowchartServiceImpl implements FlowchartService {

	private final FlowchartRepository flowchartRepository;

	private final FlowchartMapper flowchartMapper;

	private final NodeService nodeService;

	private final EdgeService edgeService;

	@Transactional
	public FlowchartDto createFlowchart(FlowchartDto flowchartDto) {

		// Create a new Flowchart object with the given ID, nodes, and edges
		if (flowchartRepository.findByLabel(flowchartDto.getLabel()).isPresent()) {
			throw new FlowchartAlreadyExistException(
					"Flowchart is already present with name: " + flowchartDto.getLabel() + ", Please try again...");
		}
		Flowchart flowchart = new Flowchart();
		flowchart.setLabel(flowchartDto.getLabel());

		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		// Ensure nodes and edges are associated with the flowchart
		for (NodeDto nodeDto : flowchartDto.getNodes()) {
			Node node = nodeService.dtoToEntity(nodeDto);
			node.setFlowchart(flowchart);
			nodes.add(node);
		}
		for (EdgeDto edgeDto : flowchartDto.getEdges()) {
			Edge edge = edgeService.dtoToEntity(edgeDto);
			edge.setFlowchart(flowchart);
			edges.add(edge);
		}

		flowchart.setNodes(nodes);
		flowchart.setEdges(edges);

		// Save the flowchart to the repository first
		flowchart = flowchartRepository.save(flowchart);

		// Then save nodes and edges after the flowchart is persisted
		nodeService.saveAllNodes(nodes);
		edgeService.saveAllEdges(edges);

		return flowchartMapper.convertEntityToDto(flowchart);
	}

	public FlowchartDto getFlowchart(Long id) {
		return flowchartMapper.convertEntityToDto(flowchartRepository.findById(id).orElseThrow(
				() -> new FlowchartNotFoundException("Flowchart not found with id: " + id + ", Please try again...")));
	}

	@Transactional
	public FlowchartDto updateFlowchart(Long id, List<NodeDto> nodeDtos, List<EdgeDto> edgeDtos, String operation) {

		// Retrieve the existing flowchart
		Flowchart flowchart = flowchartRepository.findById(id).orElse(null);

		if (flowchart == null) {
			throw new FlowchartNotFoundException("Flowchart Not found with id: " + id + ", Please try again...");
		}

		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		// Perform the 'add' operation if specified
		if (operation.equalsIgnoreCase("add")) {

			// Convert Node DTOs to Entities and associate with Flowchart
			if (nodeDtos != null) {
				for (NodeDto nodeDto : nodeDtos) {
					Node nodeCheck = nodeService.getNodeByLabelAndFlowchartId(nodeDto.getLabel(), flowchart.getId());
					if (nodeCheck == null) {
						Node node = nodeService.dtoToEntity(nodeDto);
						node.setFlowchart(flowchart);
						nodes.add(node);
					} else {
						throw new NodeAlreadyExistsException("Node Already Exists with label: " + nodeDto.getLabel()
								+ " for " + flowchart.getLabel() + ", Please try again...");
					}
				}
			}

			// Convert Edge DTOs to Entities and associate with Flowchart
			if (edgeDtos != null) {
				for (EdgeDto edgeDto : edgeDtos) {
					Edge edgeCheck = edgeService.getEdgeByLabelAndFlowchartId(edgeDto.getLabel(), flowchart.getId());
					if (edgeCheck == null) {
						Edge edge = edgeService.dtoToEntity(edgeDto);
						edge.setFlowchart(flowchart);
						edges.add(edge);
					} else {
						throw new EdgeAlreadyExistsException("Edge already exists with label: " + edgeDto.getLabel()
								+ " for " + flowchart.getLabel() + ", Please try again...");
					}
				}
			}

			// Add the new nodes and edges to the Flowchart
			flowchart.getNodes().addAll(nodes);
			flowchart.getEdges().addAll(edges);
		}

		// Perform the 'delete' operation if specified
		else if (operation.equalsIgnoreCase("delete")) {

			// Convert Node DTOs to Entities and associate with Flowchart
			if (nodeDtos != null) {
				for (NodeDto nodeDto : nodeDtos) {
					Node node = nodeService.getNodeByLabelAndFlowchartId(nodeDto.getLabel(), flowchart.getId());
					if (node != null) {
						nodes.add(node);
					} else {
						throw new NodeNotFoundException("Node not found with label: " + nodeDto.getLabel() + " for "
								+ flowchart.getLabel() + ", Please try again...");
					}
				}
			}

			// Convert Edge DTOs to Entities and associate with Flowchart
			if (edgeDtos != null) {
				for (EdgeDto edgeDto : edgeDtos) {
					Edge edge = edgeService.getEdgeByLabelAndFlowchartId(edgeDto.getLabel(), flowchart.getId());
					if (edge != null) {
						edges.add(edge);
					} else {
						throw new EdgeNotFoundException("Edge not found with label: " + edgeDto.getLabel() + " for "
								+ flowchart.getLabel() + ", Please try again...");
					}
				}
			}

			// First, remove the nodes and edges from the old flowchart
			flowchart.getNodes().removeAll(nodes);
			flowchart.getEdges().removeAll(edges);

		}

		// Save the flowchart to persist the changes made to nodes and edges
		Flowchart savedFlowchart = flowchartRepository.save(flowchart);

		// Convert the updated Flowchart entity to DTO and return it
		return flowchartMapper.convertEntityToDto(savedFlowchart);
	}

	@Transactional
	public String deleteFlowchart(Long id) {

		// Check if the flowchart is present with the given Id
		Flowchart flowchart = flowchartRepository.findById(id).orElse(null);
		if (flowchart != null) {
			flowchartRepository.delete(flowchart);
			return "Deleted Successfully...";
		}
		throw new FlowchartNotFoundException("Flowchart not found with id: " + id + ", Please try again...");
	}

	public List<Edge> getOutgoingEdges(Long flowchartId, Long nodeId) {
		Flowchart flowchart = flowchartRepository.findById(flowchartId).orElse(null);
		if (flowchart == null) {
			throw new FlowchartNotFoundException(
					"Flowchart not found with id: " + flowchartId + ", please try again with a valid id");
		}
		return edgeService.getOutgoingEdges(flowchart, nodeId);
	}

	public List<Long> getAllConnectedNodes(Long flowchartId, Long nodeId) {
		Flowchart flowchart = flowchartRepository.findById(flowchartId).orElse(null);
		if (flowchart == null)
			throw new FlowchartNotFoundException(
					"Flowchart not found with id: " + flowchartId + ", please try again with a valid id");

		return nodeService.getAllConnectedNodes(flowchart, nodeId);
	}

}
