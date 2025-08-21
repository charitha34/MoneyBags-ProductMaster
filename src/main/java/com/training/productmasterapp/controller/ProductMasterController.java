package com.training.productmasterapp.controller;

import com.training.productmasterapp.model.ProductMaster;
import com.training.productmasterapp.service.ProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5050", "http://localhost:4200", "http://localhost:8000"})
@RequestMapping("/products")
public class ProductMasterController {

    private final ProductMasterService productService;

    @Autowired
    public ProductMasterController(ProductMasterService productService) {
        this.productService = productService;
    }

    // -------- GET all products --------
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping
    public ResponseEntity<List<ProductMaster>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // -------- GET by functional key (productId) --------
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/byProductId/{productId}")
    public ResponseEntity<?> getProductByProductId(@PathVariable Long productId) {
        ProductMaster product = productService.findByProductId(productId);
        return product != null ? ResponseEntity.ok(product)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    // -------- GET by technical key (id) --------
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        ProductMaster product = productService.findById(id);
        return product != null ? ResponseEntity.ok(product)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    // -------- CREATE --------
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductMaster productMaster) {
        Map<String, String> response = new HashMap<>();
        if (productService.addProduct(productMaster)) {
            response.put("message", "Product inserted successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("message", "Product already exists");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // -------- UPDATE (technical key id) --------
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(
            @PathVariable Long id, @RequestBody ProductMaster productMaster) {
        productMaster.setId(id); // enforce technical key
        boolean updated = productService.updateProduct(productMaster);

        Map<String, String> response = new HashMap<>();
        if (updated) {
            response.put("message", "Product updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Product not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // -------- DELETE (technical key id) --------
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        boolean deleted = productService.deleteProductById(id);

        if (deleted) {
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Product not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // -------- SEARCH (functional fields) --------
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/name/{productName}")
    public ResponseEntity<?> getByProductName(@PathVariable String productName) {
        List<ProductMaster> products = productService.findByProductName(productName);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found")
                : ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/type/{productType}")
    public ResponseEntity<?> getByProductType(@PathVariable String productType) {
        List<ProductMaster> products = productService.findByProductType(productType);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found")
                : ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        List<ProductMaster> products = productService.findByStatus(status);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for given status")
                : ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/interestRateAbove/{rate}")
    public ResponseEntity<?> getByInterestRateAbove(@PathVariable double rate) {
        List<ProductMaster> products = productService.findByInterestRateGreaterThan(rate);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found above rate " + rate)
                : ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/minBalanceBelow/{amount}")
    public ResponseEntity<?> getLowMinBalanceProducts(@PathVariable double amount) {
        List<ProductMaster> products = productService.findProductsWithLowMinBalance(amount);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found with min balance below " + amount)
                : ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/search/chargeRange/{min}/{max}")
    public ResponseEntity<?> getByChargeRange(@PathVariable double min, @PathVariable double max) {
        List<ProductMaster> products = productService.findProductsByChargeRange(min, max);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products in charge range " + min + " - " + max)
                : ResponseEntity.ok(products);
    }

    // -------- CHECK existence --------
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable Long id) {
        return ResponseEntity.ok(productService.checkProductExists(id));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("/{accountType}")
    public ResponseEntity<?> getByAccountType(@PathVariable String accountType) {
        String name = String.valueOf(accountType.toLowerCase());
        return ResponseEntity.ok(productService.findByProductTypeAndMinBalanceLessThan(name));
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TELLER')")
    @GetMapping("withFetaures/{id}")
    public ResponseEntity<?> getWithFetauresById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductWithFeatures(id));
    }
}
