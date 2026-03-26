package com.example.stockTrader.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class StockTraderTools {

    @Tool(description =  "Get stock price for a ticker symbol")
    public double getStockPrice(@ToolParam(description = "Stock ticker like AAPL, TSLA", required = true)
            String ticker)
    {
//        stimulate the price -> range = 100 - 250
        return 100 + Math.random() * 150;
    }
}
