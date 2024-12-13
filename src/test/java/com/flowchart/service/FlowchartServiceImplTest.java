package com.flowchart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flowchart.dto.EdgeDto;
import com.flowchart.dto.FlowchartDto;
import com.flowchart.dto.NodeDto;
import com.flowchart.exception.FlowchartAlreadyExistException;
import com.flowchart.exception.FlowchartNotFoundException;
import com.flowchart.mapper.FlowchartMapper;
import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;
import com.flowchart.model.Node;
import com.flowchart.repository.FlowchartRepository;
import com.flowchart.service.impl.FlowchartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FlowchartServiceImplTest {

    @InjectMocks
    private FlowchartServiceImpl flowchartService;

    @Mock
    private FlowchartRepository flowchartRepository;

    @Mock
    private FlowchartMapper flowchartMapper;

    @Mock
    private NodeService nodeService;

    @Mock
    private EdgeService edgeService;

    private Flowchart flowchart;
    private FlowchartDto flowchartDto;
    private Node node;
    private Edge edge;

    @BeforeEach
    public void setUp() {
        flowchart = new Flowchart();
        flowchart.setId(1L);
        flowchart.setLabel("Flowchart 1");

        node = new Node();
        node.setId(1L);
        node.setLabel("Node 1");

        edge = new Edge();
        edge.setId(1L);
        edge.setLabel("Edge 1");
        edge.setFromNodeId(1L);
        edge.setToNodeId(2L);

        flowchartDto = new FlowchartDto();
        flowchartDto.setLabel("Flowchart 1");
        flowchartDto.setNodes(Collections.singletonList(new NodeDto("Node 1")));
        flowchartDto.setEdges(Collections.singletonList(new EdgeDto(1L, "Edge 1", 1L, 2L)));
    }

    @Test
    public void testCreateFlowchart_WhenFlowchartAlreadyExists_ShouldThrowException() {
        when(flowchartRepository.findByLabel(flowchartDto.getLabel())).thenReturn(Optional.of(flowchart));

        FlowchartAlreadyExistException exception = assertThrows(
                FlowchartAlreadyExistException.class,
                () -> flowchartService.createFlowchart(flowchartDto)
        );

        assertEquals("Flowchart is already present with name: Flowchart 1, Please try again...", exception.getMessage());
    }

    @Test
    public void testCreateFlowchart_ShouldCreateFlowchart() {
        when(flowchartRepository.findByLabel(flowchartDto.getLabel())).thenReturn(Optional.empty());
        when(flowchartRepository.save(any(Flowchart.class))).thenReturn(flowchart);
        when(nodeService.dtoToEntity(any(NodeDto.class))).thenReturn(node);
        when(edgeService.dtoToEntity(any(EdgeDto.class))).thenReturn(edge);
        when(flowchartMapper.convertEntityToDto(any(Flowchart.class))).thenReturn(flowchartDto);

        FlowchartDto result = flowchartService.createFlowchart(flowchartDto);

        assertNotNull(result);
        assertEquals("Flowchart 1", result.getLabel());
        verify(flowchartRepository, times(1)).save(any(Flowchart.class));
    }

    @Test
    public void testGetFlowchart_WhenFlowchartNotFound_ShouldThrowException() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.empty());

        FlowchartNotFoundException exception = assertThrows(
                FlowchartNotFoundException.class,
                () -> flowchartService.getFlowchart(1L)
        );

        assertEquals("Flowchart not found with id: 1, Please try again...", exception.getMessage());
    }

    @Test
    public void testGetFlowchart_ShouldReturnFlowchartDto() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.of(flowchart));
        when(flowchartMapper.convertEntityToDto(any(Flowchart.class))).thenReturn(flowchartDto);

        FlowchartDto result = flowchartService.getFlowchart(1L);

        assertNotNull(result);
        assertEquals("Flowchart 1", result.getLabel());
    }

    @Test
    public void testUpdateFlowchart_ShouldAddNodesAndEdges() {
        List<NodeDto> nodeDtos = Collections.singletonList(new NodeDto("Node 1"));
        List<EdgeDto> edgeDtos = Collections.singletonList(new EdgeDto(null, "Edge 1", 1L, 2L));

        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.of(flowchart));
        when(nodeService.getNodeByLabelAndFlowchartId(anyString(), anyLong())).thenReturn(null);
        when(edgeService.getEdgeByLabelAndFlowchartId(anyString(), anyLong())).thenReturn(null);
        when(nodeService.dtoToEntity(any(NodeDto.class))).thenReturn(node);
        when(edgeService.dtoToEntity(any(EdgeDto.class))).thenReturn(edge);
        when(flowchartRepository.save(any(Flowchart.class))).thenReturn(flowchart);
        when(flowchartMapper.convertEntityToDto(any(Flowchart.class))).thenReturn(flowchartDto);

        FlowchartDto result = flowchartService.updateFlowchart(1L, nodeDtos, edgeDtos, "add");

        assertNotNull(result);
        verify(flowchartRepository, times(1)).save(any(Flowchart.class));
    }

    @Test
    public void testDeleteFlowchart_WhenFlowchartNotFound_ShouldThrowException() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.empty());

        FlowchartNotFoundException exception = assertThrows(
                FlowchartNotFoundException.class,
                () -> flowchartService.deleteFlowchart(1L)
        );

        assertEquals("Flowchart not found with id: 1, Please try again...", exception.getMessage());
    }

    @Test
    public void testDeleteFlowchart_ShouldDeleteSuccessfully() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.of(flowchart));

        String result = flowchartService.deleteFlowchart(1L);

        assertEquals("Deleted Successfully...", result);
        verify(flowchartRepository, times(1)).delete(flowchart);
    }

    @Test
    public void testGetOutgoingEdges_ShouldReturnEdges() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.of(flowchart));
        when(edgeService.getOutgoingEdges(any(Flowchart.class), anyLong())).thenReturn(Collections.singletonList(edge));

        List<Edge> result = flowchartService.getOutgoingEdges(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllConnectedNodes_ShouldReturnNodes() {
        when(flowchartRepository.findById(anyLong())).thenReturn(Optional.of(flowchart));
        when(nodeService.getAllConnectedNodes(any(Flowchart.class), anyLong())).thenReturn(Collections.singletonList(1L));

        List<Long> result = flowchartService.getAllConnectedNodes(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
