package com.example.currencyconverter.repositories;

import com.example.currencyconverter.entities.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionHistoryRepo extends JpaRepository <ConversionHistory, String> {


}
