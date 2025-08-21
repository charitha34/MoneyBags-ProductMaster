package com.training.productmasterapp.service;

import com.training.productmasterapp.dao.ProductMasterDao;
import com.training.productmasterapp.model.ProductMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

    private final ProductMasterDao productDao;

    @Autowired
    public ProductMasterServiceImpl(ProductMasterDao productDao) {
        this.productDao = productDao;
    }

    // -------- Functional key lookups --------
    @Override
    public ProductMaster findByProductId(Long productId) {
        return productDao.findByProductId(productId).orElse(null);
    }

    @Override
    public List<ProductMaster> findByProductName(String productName) {
        return productDao.findByProductName(productName);
    }

    @Override
    public List<ProductMaster> findByProductType(String productType) {
        return productDao.findByProductType(productType);
    }

    @Override
    public List<ProductMaster> findByInterestRateGreaterThan(double interestRate) {
        return productDao.findByInterestRateGreaterThan(interestRate);
    }

    @Override
    public List<ProductMaster> findByStatus(String status) {
        return productDao.findByStatus(status);
    }

    @Override
    public List<ProductMaster> findProductsWithLowMinBalance(double amount) {
        return productDao.findProductsWithLowMinBalance(amount);
    }

    @Override
    public List<ProductMaster> findProductsByChargeRange(double minCharge, double maxCharge) {
        return productDao.findProductsByChargeRange(minCharge, maxCharge);
    }

    @Override
    public List<ProductMaster> findByProductTypeAndMinBalanceLessThan(String productType) {
        return productDao.findByProductTypeAndMinBalanceLessThan(productType);
    }

    @Override
    public List<Map<String, Object>> findProductWithFeatures(Long productId) {
        return productDao.findProductWithFeatures(productId);
    }

    // -------- Technical key operations --------
    @Override
    public ProductMaster findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public List<ProductMaster> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public boolean addProduct(ProductMaster productMaster) {
        if (productMaster.getId() != null && productDao.existsById(productMaster.getId())) {
            return false; // avoid duplicate technical key
        }
        productDao.save(productMaster);
        return true;
    }

    @Override
    public boolean updateProduct(ProductMaster productMaster) {
        if (productMaster.getId() == null || !productDao.existsById(productMaster.getId())) {
            return false;
        }
        productDao.save(productMaster);
        return true;
    }

    @Override
    public boolean deleteProductById(Long id) {
        if (!productDao.existsById(id)) {
            return false;
        }
        productDao.deleteById(id);
        return true;
    }

    @Override
    public boolean checkProductExists(Long id) {
        return productDao.existsById(id);
    }

    // -------- Optional delete by functional key --------
    @Override
    public boolean deleteProductByProductId(Long productId) {
        ProductMaster product = productDao.findByProductId(productId).orElse(null);
        if (product == null) {
            return false;
        }
        productDao.delete(product);
        return true;
    }
}
