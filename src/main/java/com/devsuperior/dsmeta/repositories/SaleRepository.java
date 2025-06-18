package com.devsuperior.dsmeta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,
            value = """
                    SELECT sale.id, sale.date, sale.amount, seller.name
                    FROM tb_sales sale
                        INNER JOIN tb_seller seller on seller.id=sale.seller_id
                    WHERE
                        seller.name like :sellerName 
                            and sale.date between :initialDate and :finalDate
                    
                    """)
    List<Sale> querySalesReport(String sellerName, LocalDate initialDate, LocalDate finalDate);

    @Query(nativeQuery = true,
            value = """
                    SELECT sale.id, sale.date, sale.amount, seller.name
                    FROM tb_sales sale
                        INNER JOIN tb_seller seller on seller.id=sale.seller_id
                    WHERE sale.date between :initialDate and :finalDate
                    
                    """)
    List<Sale> querySalesSummary(LocalDate initialDate, LocalDate finalDate);
}
