package ru.otus.smolyanov.yajson;

import java.util.function.Predicate;
import java.util.function.Function;

/**
 * Created by Gregory Smolyanov.
 * <p>
 * Home work 08 - JSON object writer
 */

enum YAJsonType {
  PRIMITIVE(PredicateHelper.isPrimitive, FunctionHelper.processPrimitive),

  STRING(PredicateHelper.isString, FunctionHelper.processString),

  ARRAY(PredicateHelper.isArray, FunctionHelper.processArray),

  LIST(PredicateHelper.isList, FunctionHelper.processList),

  SET(PredicateHelper.isSet, FunctionHelper.processSet);

  private final Predicate<Class> predicate;
  private final Function<Object, String> function;

  YAJsonType(Predicate<Class> predicate, Function<Object, String> function) {
    this.predicate = predicate;
    this.function = function;
  }

  Predicate<Class> getPredicate() {
    return predicate;
  }

  Function<Object, String> getFunction() {
    return function;
  }

}
