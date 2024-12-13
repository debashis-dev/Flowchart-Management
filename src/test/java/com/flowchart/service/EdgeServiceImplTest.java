package com.flowchart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flowchart.dto.EdgeDto;
import com.flowchart.exception.NoOutgoingEdgesException;
import com.flowchart.mapper.EdgeMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.repository.EdgeRepository;
import com.flowchart.service.impl.EdgeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EdgeServiceImplTest {

    @Mock
    private EdgeRepository edgeRepository;

    @Mock
    private EdgeMapper edgeMapper;

    @InjectMocks
    private EdgeServiceImpl edgeService;

    private Flowchart flowchart;
    private Edge edge1;
    private Edge edge2;

    @BeforeEach
    void setUp() {
        flowchart = new Flowchart();
        flowchart.setId(1L);

        edge1 = new Edge(1L, "edge1", 1L, 2L, flowchart);
        edge2 = new Edge(2L, "edge2", 1L, 3L, flowchart);
    }

    @Test
    void testDtoToEntity() {
        EdgeDto edgeDto = new EdgeDto(null, "edge1", 1L, 2L);
        when(edgeMapper.convertDtoToEntity(edgeDto)).thenReturn(edge1);

        Edge edge = edgeService.dtoToEntity(edgeDto);

        assertNotNull(edge);
        assertEquals("edge1", edge.getLabel());
        assertEquals(1L, edge.getFromNodeId());
        assertEquals(2L, edge.getToNodeId());
    }

    @Test
    void testSaveAllEdges() {
        List<Edge> edges = Arrays.asList(edge1, edge2);
        when(edgeRepository.saveAll(edges)).thenReturn(edges);

        List<Edge> savedEdges = edgeService.saveAllEdges(edges);

        assertNotNull(savedEdges);
        assertEquals(2, savedEdges.size());
        verify(edgeRepository, times(1)).saveAll(edges);
    }

    @Test
    void testGetEdgeByLabelAndFlowchartId() {
        when(edgeRepository.findByLabelAndFlowchartId("edge1", 1L)).thenReturn(Optional.of(edge1));

        Edge resultEdge = edgeService.getEdgeByLabelAndFlowchartId("edge1", 1L);

        assertNotNull(resultEdge);
        assertEquals("edge1", resultEdge.getLabel());
        assertEquals(1L, resultEdge.getFromNodeId());
        assertEquals(2L, resultEdge.getToNodeId());
    }

    @Test
    void testGetEdgeByLabelAndFlowchartId_NotFound() {
        when(edgeRepository.findByLabelAndFlowchartId("edge1", 1L)).thenReturn(Optional.empty());

        Edge resultEdge = edgeService.getEdgeByLabelAndFlowchartId("edge1", 1L);

        assertNull(resultEdge);
    }

    @Test
    void testGetOutgoingEdges() {
        Flowchart flowchart = new Flowchart();
        flowchart.setId(1L);

        Edge edge1 = new Edge(1L, "edge1", 1L, 2L, flowchart);
        Edge edge2 = new Edge(2L, "edge2", 1L, 3L, flowchart);
        flowchart.setEdges(Arrays.asList(edge1, edge2));

        List<Edge> outgoingEdges = edgeService.getOutgoingEdges(flowchart, 1L);

        assertNotNull(outgoingEdges);
        assertEquals(2, outgoingEdges.size());
        assertTrue(outgoingEdges.contains(edge1));
        assertTrue(outgoingEdges.contains(edge2));
    }

    @Test
    void testGetOutgoingEdges_NoEdgesFound() {
        Flowchart flowchart = new Flowchart();
        flowchart.setId(1L);
        flowchart.setEdges(Arrays.asList());

        Exception exception = assertThrows(NoOutgoingEdgesException.class, () -> {
            edgeService.getOutgoingEdges(flowchart, 1L);
        });

        assertEquals("No outgoing edges for flowchart with id: 1 and node with id: 1", exception.getMessage());
    }

    @Test
    void testGetOutgoingEdges_SingleEdge() {
        Flowchart flowchart = new Flowchart();
        flowchart.setId(1L);

        Edge edge1 = new Edge(1L, "edge1", 1L, 2L, flowchart);
        flowchart.setEdges(Arrays.asList(edge1));

        List<Edge> outgoingEdges = edgeService.getOutgoingEdges(flowchart, 1L);

        assertNotNull(outgoingEdges);
        assertEquals(1, outgoingEdges.size());
        assertEquals("edge1", outgoingEdges.get(0).getLabel());
    }
}
