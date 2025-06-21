package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


	@Transactional(readOnly = true)
	public Page<SaleReportDTO> buildSalesReport(LocalDate initialDate, LocalDate finalDate, String sellerName, Integer offset, Integer limit){

		if(Objects.isNull(finalDate))
			finalDate = LocalDate.now();

		if(Objects.isNull(initialDate))
			initialDate = finalDate.minusYears(1L);

		return repository.querySalesReport(sellerName, initialDate, finalDate, PageRequest.of(offset, limit));
	}

	public List<SaleSummaryDTO> buildSalesSummary(LocalDate initialDate, LocalDate finalDate){

		if(Objects.isNull(finalDate))
			finalDate = LocalDate.now();

		if(Objects.isNull(initialDate))
			initialDate = finalDate.minusYears(1L);

		return repository.querySalesSummary(initialDate, finalDate);
	}
}
