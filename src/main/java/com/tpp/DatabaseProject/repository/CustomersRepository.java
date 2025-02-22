package com.tpp.DatabaseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.DatabaseProject.models.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
}