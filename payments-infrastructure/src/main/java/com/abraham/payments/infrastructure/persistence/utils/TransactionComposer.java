package com.abraham.payments.infrastructure.persistence.utils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/**
 * Utility class that allows to execute various DAOs executions inside the same transaction.
 *
 * Spring @Transactional uses AOP to manage transactions. This requires the transactional method to be public and also
 * not to be an internal class call to the method. This imposes some restrictions as any method annotated
 * with @Transactional won't be able to handle the transaction exceptions internally, but throw them.
 * This can lead to a situation where internal database exceptions are thrown to another layers that shouldn't need to
 * know the persistence specifics.
 *
 * In order to prevent that, without needing to create more classes just to hold the transactional method, and be able
 * to handle the transaction exceptions inside the infrastructure layer, this utility class allows to execute a
 * transaction composed by the provided Runnables
 *
 */
@Component
public class TransactionComposer {

  /**
   * Execute the provided Runnables in the specified order inside a transaction
   *
   * @param runnables the elements to execute **ORDERED**
   */
  @Transactional
  public void execute(final Runnable ... runnables) {
    Stream.of(runnables).sequential().forEach(Runnable::run);
  }
}
