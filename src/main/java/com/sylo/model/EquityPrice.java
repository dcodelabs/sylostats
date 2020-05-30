package com.sylo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@Getter
@Setter
public class EquityPrice {
    private double open;
    private double close;
    private double high;
    private double low;
    private int volume;
    private double adjclose;
    private long date;
}
