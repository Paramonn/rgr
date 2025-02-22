package com.tpp.DatabaseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.DatabaseProject.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
