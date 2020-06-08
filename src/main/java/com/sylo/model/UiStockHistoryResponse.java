package com.sylo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dhanavenkateshgopal on 8/6/20.
 * @project sylostats
 */
@Data
@AllArgsConstructor
public class UiStockHistoryResponse {
    private String exchange;
    private String stockSymbol;
    private String year;
    private String month;
    private String open;
    private String high;
    private String low;
    private String  close;
}
