package com.flowchart.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flowchart.exception.EdgeAlreadyExistsException;
import com.flowchart.exception.EdgeNotFoundException;
import com.flowchart.exception.FlowchartAlreadyExistException;
import com.flowchart.exception.FlowchartNotFoundException;
import com.flowchart.exception.NoOutgoingEdgesException;
import com.flowchart.exception.NodeAlreadyExistsException;
import com.flowchart.exception.NodeNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<String> handleEdgeAlreadyExistsException(EdgeAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleEdgeNotFoundException(EdgeNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleFlowchartAlreadyExistException(FlowchartAlreadyExistException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleFlowchartNotFoundException(FlowchartNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNodeAlreadyExistsException(NodeAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	@ExceptionHandler
	public ResponseEntity<String> handleNodeNotFoundException(NodeNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler
	public ResponseEntity<String> handleNoOutgoingEdgesException(NoOutgoingEdgesException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}
