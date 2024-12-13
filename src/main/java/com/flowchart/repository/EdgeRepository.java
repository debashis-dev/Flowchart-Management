package com.flowchart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowchart.model.Edge;
import com.flowchart.model.Flowchart;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
	
	Optional<Edge> findByLabelAndFlowchartId(String label, Long flowchartId);
	
	Optional<Flowchart> findFlowchartById(Long flowchartId);
}
