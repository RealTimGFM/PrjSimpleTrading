package com.example.simpletradingapp.DAO;
import com.example.simpletradingapp.model.Category;
import com.example.simpletradingapp.model.StockDataset;
import java.util.*;

public interface DatasetDAO {
    /**
     * Find company information
     * @param symbol in the stock code
     * @return the stockdataset or null if not found
     */
    List<StockDataset> findBySymbol(String symbol);

    /**
     * Find closed stock by date
     * @param date in the stock code
     * @return the close stock (StockDataset) or null if not found
     */
    //this is useless
    //List<StockDataset> findCloseByDate(Date date);
    /**
     * METHOD OVERLOAD
     * Find closed stock by date and by symbol
     * @param date in the stock code
     * @return the close stock (StockDataset) or null if not found
     */
    StockDataset findCloseByDate(Date date, Category category);
}
