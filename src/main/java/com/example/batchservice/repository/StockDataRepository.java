package com.example.batchservice.repository;

import com.example.batchservice.entity.Stock;
import com.example.batchservice.entity.StockData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockDataRepository extends JpaRepository<StockData, Long> {
    List<StockData> findByStock(Stock stock);
}