package com.mergeco.oiljang.wishlist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "WishList")
@Table(name = "wish_list")
public class WishList {

    @Id
    @Column(name = "wish_code")
    private int wishCode;

    @Column(name = "ref_product_code")
    private int refProductCode;

    @Column(name = "ref_user_code")
    private UUID refUserCode;

}
