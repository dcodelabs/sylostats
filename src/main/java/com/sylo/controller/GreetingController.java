package com.sylo.controller;

import com.sylo.config.Stocks;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author dhanavenkateshgopal on 13/5/20.
 * @project sylostats
 */
@Controller
public class GreetingController {
  private final Stocks stocks;

  public GreetingController(Stocks stocks) {
    this.stocks = stocks;
  }

  @GetMapping("/greeting")
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  @GetMapping("/api/equities")
  public String getEquities(Model model) {
    Map<String, List<String>> stockMap = stocks.getOptions();
    model.addAttribute("stocks", stockMap);
    return "equity-data-lookup";
  }
}
