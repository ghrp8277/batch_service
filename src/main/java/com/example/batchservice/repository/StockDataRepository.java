package com.example.batchservice.repository;

import com.example.common.Stock;
import com.example.common.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockDataRepository extends JpaRepository<StockData, Long> {

    List<StockData> findByStock(Stock stock);

    @EntityGraph(attributePaths = {"movingAverage12"})
    @Query("SELECT sd FROM StockData sd WHERE sd.stock = :stock")
    Page<StockData> findByStockWithMovingAverage12(@Param("stock") Stock stock, Pageable pageable);

    @EntityGraph(attributePaths = {"movingAverage20"})
    @Query("SELECT sd FROM StockData sd WHERE sd.stock = :stock")
    Page<StockData> findByStockWithMovingAverage20(@Param("stock") Stock stock, Pageable pageable);

    @EntityGraph(attributePaths = {"movingAverage26"})
    @Query("SELECT sd FROM StockData sd WHERE sd.stock = :stock")
    Page<StockData> findByStockWithMovingAverage26(@Param("stock") Stock stock, Pageable pageable);

    @EntityGraph(attributePaths = {"bollingerBands"})
    @Query("SELECT sd FROM StockData sd WHERE sd.stock = :stock")
    Page<StockData> findByStockWithBollingerBands(@Param("stock") Stock stock, Pageable pageable);

    @EntityGraph(attributePaths = {"macd"})
    @Query("SELECT sd FROM StockData sd WHERE sd.stock = :stock")
    Page<StockData> findByStockWithMacd(@Param("stock") Stock stock, Pageable pageable);
}