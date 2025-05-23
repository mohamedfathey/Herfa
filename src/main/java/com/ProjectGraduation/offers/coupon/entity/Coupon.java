package com.ProjectGraduation.offers.coupon.entity;

import com.ProjectGraduation.auth.entity.User;
import com.ProjectGraduation.offers.coupon.utils.DiscountType;
import com.ProjectGraduation.offers.autoOffer.utils.UserSegment;
import com.ProjectGraduation.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private double discount;

    private Double maxDiscount;

    private Integer availableQuantity;

    private LocalDateTime expiryDate;

    private boolean active = true;

    private Double fixedPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Double minOrderAmount;

    @Enumerated(EnumType.STRING)
    private UserSegment userSegment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

}
