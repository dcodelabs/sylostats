package com.sylo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@Getter
@Setter
public class EquityHistory {
    private String exchange;
    private String stockId;
    private String interval;
    private int year;
    private int month;
    private double open;
    private double high;
    private double low;
    private double close;
}
