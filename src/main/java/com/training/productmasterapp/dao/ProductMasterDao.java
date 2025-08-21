package com.training.productmasterapp.dao;

import com.training.productmasterapp.model.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductMasterDao extends JpaRepository<ProductMaster, Long> {

    // Functional Key lookups
    Optional<ProductMaster> findByProductId(Long productId);
    List<ProductMaster> findByProductName(String productName);
    List<ProductMaster> findByProductType(String productType);
    List<ProductMaster> findByInterestRateGreaterThan(double threshold);
    List<ProductMaster> findByStatus(String status);

    // Custom Queries
    @Query("FROM ProductMaster p WHERE p.minBalance < :amount")
    List<ProductMaster> findProductsWithLowMinBalance(@Param("amount") double amount);

    @Query("FROM ProductMaster p WHERE p.accountOpenCharge BETWEEN :min AND :max")
    List<ProductMaster> findProductsByChargeRange(@Param("min") double min,
                                                  @Param("max") double max);
    @Query("FROM ProductMaster p WHERE lower(p.productName) = :productType ")
    List<ProductMaster> findByProductTypeAndMinBalanceLessThan(@Param("productType") String productType);
    @Query(
            value = "SELECT p.product_ID as productId, p.PRODUCT_NAME, p.PRODUCT_TYPE, p.INTEREST_RATE, " +
                    "p.MIN_BALANCE, p.ACCOUNT_OPEN_CHARGE, p.STATUS, " +
                    "f.FEATURE_ID, f.FEATURE_NAME, f.IS_ENABLED, f.FEE_AMOUNT, f.FEE_FREQUENCY " +
                    "FROM PRODUCT_MASTER p " +
                    "LEFT JOIN PRODUCT_FEATURES f ON p.PRODUCT_ID = f.PRODUCT_ID " +
                    "WHERE p.PRODUCT_ID = :productId",
            nativeQuery = true
    )
    List<Map<String, Object>> findProductWithFeatures(@Param("productId") Long productId);


}
