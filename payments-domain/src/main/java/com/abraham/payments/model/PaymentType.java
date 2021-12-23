package com.abraham.payments.model;

public enum PaymentType {
  ONLINE("online"), OFFLINE("offline");

  private final String value;

  PaymentType(final String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
