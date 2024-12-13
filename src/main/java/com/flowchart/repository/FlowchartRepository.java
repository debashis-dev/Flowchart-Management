package com.flowchart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowchart.model.Flowchart;


public interface FlowchartRepository extends JpaRepository<Flowchart, Long> {
	Optional<Flowchart> findByLabel(String label);
}
