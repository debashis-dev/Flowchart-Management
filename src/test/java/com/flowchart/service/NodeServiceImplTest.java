package com.flowchart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flowchart.dto.NodeDto;
import com.flowchart.mapper.NodeMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.model.Node;
import com.flowchart.repository.NodeRepository;
import com.flowchart.service.impl.NodeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NodeServiceImplTest {

    @Mock
    private NodeRepository nodeRepository;

    @Mock
    private NodeMapper nodeMapper;

    @InjectMocks
    private NodeServiceImpl nodeService;

    private Node node;
    private NodeDto nodeDto;

    @BeforeEach
    public void setUp() {
        nodeDto = new NodeDto("Node 1");
        node = new Node(1L, "Node 1", null);
    }

    @Test
    public void testDtoToEntity() {
        when(nodeMapper.convertDtoToEntity(nodeDto)).thenReturn(node);

        Node result = nodeService.dtoToEntity(nodeDto);

        assertNotNull(result);
        assertEquals(node.getLabel(), result.getLabel());
        verify(nodeMapper, times(1)).convertDtoToEntity(nodeDto);
    }

    @Test
    public void testSaveAllNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        when(nodeRepository.saveAll(nodes)).thenReturn(nodes);

        List<Node> savedNodes = nodeService.saveAllNodes(nodes);

        assertNotNull(savedNodes);
        assertEquals(1, savedNodes.size());
        assertEquals(node, savedNodes.get(0));
        verify(nodeRepository, times(1)).saveAll(nodes);
    }

    @Test
    public void testGetNodeByLabelAndFlowchartId() {
        Long flowchartId = 1L;
        String label = "Node 1";
        when(nodeRepository.findByLabelAndFlowchartId(label, flowchartId)).thenReturn(Optional.of(node));

        Node result = nodeService.getNodeByLabelAndFlowchartId(label, flowchartId);

        assertNotNull(result);
        assertEquals(node.getLabel(), result.getLabel());
        verify(nodeRepository, times(1)).findByLabelAndFlowchartId(label, flowchartId);
    }

    @Test
    public void testGetNodeByLabelAndFlowchartId_NotFound() {
        Long flowchartId = 1L;
        String label = "Node 2";
        when(nodeRepository.findByLabelAndFlowchartId(label, flowchartId)).thenReturn(Optional.empty());

        Node result = nodeService.getNodeByLabelAndFlowchartId(label, flowchartId);

        assertNull(result);
        verify(nodeRepository, times(1)).findByLabelAndFlowchartId(label, flowchartId);
    }

    @Test
    public void testGetAllConnectedNodes() {
        Flowchart flowchart = new Flowchart();
        flowchart.setEdges(new ArrayList<>());
        Long nodeId = 1L;

        // Simulate adding edges
        Edge edge = new Edge();
        edge.setFromNodeId(1L);
        edge.setToNodeId(2L);
        flowchart.getEdges().add(edge);

        List<Long> visitedNodes = nodeService.getAllConnectedNodes(flowchart, nodeId);

        assertTrue(visitedNodes.contains(1L));
        assertTrue(visitedNodes.contains(2L));
    }
}

