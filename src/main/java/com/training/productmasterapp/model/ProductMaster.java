package com.training.productmasterapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_MASTER")
public class ProductMaster {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;   // Technical key (must be set manually)

    @Column(name = "PRODUCT_ID", unique = true)
    private Long productId;   // Functional key

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRODUCT_TYPE", nullable = false)
    private String productType;

    @Column(name = "INTEREST_RATE")
    private Double interestRate;

    @Column(name = "MIN_BALANCE")
    private Double minBalance;

    @Column(name = "ACCOUNT_OPEN_CHARGE")
    private Double accountOpenCharge;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    // ===== Constructors =====
    public ProductMaster() {}

    public ProductMaster(Long id, Long productId, String productName, String productType,
                         Double interestRate, Double minBalance, Double accountOpenCharge,
                         String status, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.interestRate = interestRate;
        this.minBalance = minBalance;
        this.accountOpenCharge = accountOpenCharge;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Double minBalance) {
        this.minBalance = minBalance;
    }

    public Double getAccountOpenCharge() {
        return accountOpenCharge;
    }

    public void setAccountOpenCharge(Double accountOpenCharge) {
        this.accountOpenCharge = accountOpenCharge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ===== equals & hashCode =====
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductMaster other)) return false;
        return Objects.equals(id, other.id) &&
                Objects.equals(productId, other.productId) &&
                Objects.equals(productName, other.productName) &&
                Objects.equals(productType, other.productType) &&
                Objects.equals(interestRate, other.interestRate) &&
                Objects.equals(minBalance, other.minBalance) &&
                Objects.equals(accountOpenCharge, other.accountOpenCharge) &&
                Objects.equals(status, other.status) &&
                Objects.equals(createdAt, other.createdAt) &&
                Objects.equals(updatedAt, other.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, productName, productType,
                interestRate, minBalance, accountOpenCharge,
                status, createdAt, updatedAt);
    }

    // ===== toString =====
    @Override
    public String toString() {
        return "ProductMaster{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", interestRate=" + interestRate +
                ", minBalance=" + minBalance +
                ", accountOpenCharge=" + accountOpenCharge +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
