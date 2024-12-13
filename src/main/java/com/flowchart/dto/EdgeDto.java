package com.flowchart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EdgeDto {
	@JsonIgnore
	private Long id;
	private String label;
	private Long fromNodeId;
    private Long toNodeId;
}
