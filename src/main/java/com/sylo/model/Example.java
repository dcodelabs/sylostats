package com.sylo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dhanavenkateshgopal on 27/5/20.
 * @project sylostats
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "amount",
    "date",
    "type",
    "data"
})
@Getter
@Setter
 class EventsDatum {

    @JsonProperty("amount")
    public Integer amount;
    @JsonProperty("date")
    public Integer date;
    @JsonProperty("type")
    public String type;
    @JsonProperty("data")
    public Integer data;

}


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "prices",
    "isPending",
    "firstTradeDate",
    "id",
    "timeZone",
    "eventsData"
})
@Getter
@Setter
public class Example {

    @JsonProperty("prices")
    public List<Price> prices = null;
    @JsonProperty("isPending")
    public Boolean isPending;
    @JsonProperty("firstTradeDate")
    public Integer firstTradeDate;
    @JsonProperty("id")
    public String id;
    @JsonProperty("timeZone")
    public TimeZone timeZone;
    @JsonProperty("eventsData")
    public List<EventsDatum> eventsData = null;

}


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "open",
    "high",
    "low",
    "close",
    "volume",
    "adjclose"
})
@Getter
@Setter
 class Price {

    @JsonProperty("date")
    public Integer date;
    @JsonProperty("open")
    public Integer open;
    @JsonProperty("high")
    public Integer high;
    @JsonProperty("low")
    public Float low;
    @JsonProperty("close")
    public Float close;
    @JsonProperty("volume")
    public Integer volume;
    @JsonProperty("adjclose")
    public Float adjclose;

}


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "gmtOffset"
})
@Getter
@Setter
 class TimeZone {

    @JsonProperty("gmtOffset")
    public Integer gmtOffset;

}
