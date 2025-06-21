package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = """
                    SELECT sale.id, CAST(sale.date as VARCHAR), sale.amount, seller.name sellerName
                    FROM tb_sales sale
                        INNER JOIN tb_seller seller on seller.id=sale.seller_id
                    WHERE
                        sale.date between :initialDate and :finalDate
                        and seller.name like concat('%',:sellerName,'%') ESCAPE '\'
                    
                    """)
    Page<SaleReportDTO> querySalesReport(String sellerName, LocalDate initialDate, LocalDate finalDate,
                                         Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    SELECT tb_seller.name sellerName, CAST(SUM(tb_sales.amount) AS DOUBLE) amount
                    FROM tb_sales
                             INNER JOIN tb_seller on tb_sales.seller_id = tb_seller.id
                    WHERE tb_sales.date between :initialDate and :finalDate
                    GROUP BY tb_seller.name
                    """)
    List<SaleSummaryDTO> querySalesSummary(LocalDate initialDate, LocalDate finalDate);
}
