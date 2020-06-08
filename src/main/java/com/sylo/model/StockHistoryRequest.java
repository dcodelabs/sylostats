package com.sylo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

/**
 * @author dhanavenkateshgopal on 7/6/20.
 * @project sylostats
 */
@Data
@AllArgsConstructor
public class StockHistoryRequest {

    private String stockId;
    private String frequency;
    private String filter;
    private Optional<String> period1;
    private Optional<String> period2;

}
