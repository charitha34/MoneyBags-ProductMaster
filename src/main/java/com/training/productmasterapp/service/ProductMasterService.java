package com.training.productmasterapp.service;

import com.training.productmasterapp.dao.ProductMasterDao;
import com.training.productmasterapp.model.ProductMaster;
import java.util.List;
import java.util.Map;

public interface ProductMasterService {

    // Functional key-based lookups
    ProductMaster findByProductId(Long productId);
    List<ProductMaster> findByProductName(String productName);
    List<ProductMaster> findByProductType(String productType);
    List<ProductMaster> findByInterestRateGreaterThan(double interestRate);
    List<ProductMaster> findByStatus(String status);
    List<ProductMaster> findProductsWithLowMinBalance(double amount);
    List<ProductMaster> findProductsByChargeRange(double minCharge, double maxCharge);
    List<ProductMaster> findByProductTypeAndMinBalanceLessThan(String productTyp);
    List<Map<String, Object>> findProductWithFeatures(Long productId);
    // Technical key-based
    ProductMaster findById(Long id);  // 🔹 added
    List<ProductMaster> getAllProducts();
    boolean addProduct(ProductMaster productMaster);
    boolean updateProduct(ProductMaster productMaster);
    boolean deleteProductById(Long id);
    boolean checkProductExists(Long id);

    // Optional delete by functional key
    boolean deleteProductByProductId(Long productId);
}
