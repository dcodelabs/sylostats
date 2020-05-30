package com.sylo.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dhanavenkateshgopal on 23/5/20.
 * @project sylostats
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiError {

  private int errorCode;
  private String message;
}
