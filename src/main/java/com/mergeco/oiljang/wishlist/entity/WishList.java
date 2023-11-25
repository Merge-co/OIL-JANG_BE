package com.mergeco.oiljang.wishlist.entity;

import com.mergeco.oiljang.product.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
@Entity(name = "WishList")
@Table(name = "wish_list")
public class WishList {

    @Id
    @Column(name = "wish_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishCode;

    @ManyToOne
    @JoinColumn(name = "ref_product_code")
    private Product product;

    @Column(name = "ref_user_code")
    private int refUserCode;

    protected WishList() {
    }

}
