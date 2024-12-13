package com.flowchart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.flowchart.dto.EdgeDto;
import com.flowchart.dto.FlowchartDto;
import com.flowchart.dto.NodeDto;
import com.flowchart.model.Edge;
import com.flowchart.service.impl.FlowchartServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/flowcharts")
@RequiredArgsConstructor
public class FlowchartController {

	private final FlowchartServiceImpl flowchartService;

	@Operation(summary = "Only provide the Flowchart label with List of Node labels and List of Edge labels with starting and ending node id to create a new flowchart")
	@PostMapping
	public ResponseEntity<FlowchartDto> createFlowchart(@RequestBody FlowchartDto flowchartDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(flowchartService.createFlowchart(flowchartDto));
	}

	@Operation(summary = "Get the flowchart along with all nodes and edges using flowchart ID")
	@GetMapping("/{id}")
	public ResponseEntity<FlowchartDto> getFlowchart(@Parameter(description = "Enter the flowchart ID you want to get") @PathVariable Long id) {
		return ResponseEntity.ok(flowchartService.getFlowchart(id));
	}

	@Operation(summary = "Update a flowchart by using flowchart ID. You can add or remove nodes and edges")
	@PutMapping("/{id}")
	public ResponseEntity<FlowchartDto> updateFlowchart(
			@Parameter(description = "Enter the flowchart ID") @PathVariable Long id,
			@Parameter(description = "Enter the node labels you want to add or delete") @RequestPart(value = "nodes", required = false) List<NodeDto> nodeDtos,
			@Parameter(description = "Enter the edge labels you want to add or delete") @RequestPart(value = "edges", required = false) List<EdgeDto> edgeDtos,
			@Parameter(description = "Type the operation you want to perform 'add' or 'delete'") @RequestParam String operation) {
		FlowchartDto dto = flowchartService.updateFlowchart(id, nodeDtos, edgeDtos, operation);
		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Delete a flowchart by using flowchart ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFlowchart(@Parameter(description = "Enter the flowchart ID you want to delete") @PathVariable Long id) {
		return ResponseEntity.ok(flowchartService.deleteFlowchart(id));
	}

	@Operation(summary = "Get all outgoing edges from a node by using flowchart ID and node ID")
	@GetMapping("/{flowchartId}/nodes/{nodeId}/outgoing")
	public List<Edge> getOutgoingEdges(@Parameter(description = "Enter the flowchart ID") @PathVariable Long flowchartId, @Parameter(description = "Enter the node ID") @PathVariable Long nodeId) {
		return flowchartService.getOutgoingEdges(flowchartId, nodeId);
	}

	@Operation(summary = "Get all connected nodes by using flowchart ID and node ID")
	@GetMapping("/{flowchartId}/nodes/{nodeId}/connected")
	public List<Long> getConnectedNodes(@Parameter(description = "Enter the flowchart ID") @PathVariable Long flowchartId, @Parameter(description = "Enter the node ID") @PathVariable Long nodeId) {
		return flowchartService.getAllConnectedNodes(flowchartId, nodeId);
	}

}
