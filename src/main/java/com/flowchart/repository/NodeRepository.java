package com.flowchart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowchart.model.Node;


@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
	Node findByLabel(String label);
	
	Optional<Node> findByLabelAndFlowchartId(String label, Long flowchartId);
	
}
