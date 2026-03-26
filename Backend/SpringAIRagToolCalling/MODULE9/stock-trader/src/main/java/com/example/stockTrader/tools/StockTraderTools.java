package com.example.stockTrader.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;


@Service
public class StockTraderTools {

    @Tool(description =  "Get stock price for a ticker symbol")
    public double getStockPrice(@ToolParam(description = "Stock ticker like AAPL, TSLA", required = true)
            String ticker)
    {
//        stimulate the price -> range = 100 - 250
        return 100 + Math.random() * 150;
    }

    @Tool(description = "Buy stock with given quantity")
    public String buyStock(
            @ToolParam(description = "Stock ticker like AAPL", required = true)
            String ticker,

            @ToolParam(description = "Number of shares to buy", required = true)
            int quantity

    ) {
        return "Bought" + quantity + " shares of " + ticker;
    }
}
