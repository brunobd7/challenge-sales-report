package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(@RequestParam(required = false, name = "name", defaultValue = "") String sellerName,
														 @RequestParam(required = false, name = "minDate") LocalDate initialDate,
														 @RequestParam(required = false, name = "maxDate") LocalDate finalDate,
														 @RequestParam(required = false, defaultValue = "0") Integer offset,
														 @RequestParam(required = false, defaultValue = "10") Integer limit) {
		return ResponseEntity.ok(service.buildSalesReport(initialDate, finalDate, sellerName, offset, limit));
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(@RequestParam(required = false,name = "minDate") LocalDate initialDate,
														   @RequestParam(required = false, name = "maxDate") LocalDate finalDate) {
		return ResponseEntity.ok(service.buildSalesSummary(initialDate, finalDate));
	}
}
