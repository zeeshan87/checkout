package com.haiilo.checkout.repository;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.domainobject.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}