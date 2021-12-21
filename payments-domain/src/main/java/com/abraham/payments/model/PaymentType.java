package com.abraham.payments.model;

public enum PaymentType {
  ONLINE("online"), OFFLINE("offline");

  String value;

  PaymentType(final String value) {
    this.value = value;
  }
}
