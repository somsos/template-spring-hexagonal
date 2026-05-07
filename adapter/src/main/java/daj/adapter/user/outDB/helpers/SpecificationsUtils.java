package daj.adapter.user.outDB.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import daj.adapter.user.outDB.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class SpecificationsUtils {

  static public void addEqualPredicateForColumn(Root<UserEntity> root, CriteriaBuilder criteriaBuilder,
      List<Predicate> predicates, String prop, Object val) {
    if (val instanceof String) {
      // Use LIKE for Strings (case insensitive match)
      //predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(prop)), "%" + val.toString().toLowerCase() + "%"));
      predicates.add(criteriaBuilder.equal(root.get(prop), (String) val));
    } else if (val instanceof Integer) {
      // Use exact match for Integers
      predicates.add(criteriaBuilder.equal(root.get(prop), (Integer) val));
    } else if (val instanceof Boolean) {
      // Use exact match for Boolean
      predicates.add(criteriaBuilder.equal(root.get(prop), (Boolean) val));
    } else if (val instanceof Double) {
      // Use exact match for Double
      predicates.add(criteriaBuilder.equal(root.get(prop), (Double) val));
    } else if (val instanceof LocalDate) {
      // Use exact match for LocalDate
      predicates.add(criteriaBuilder.equal(root.get(prop), (LocalDate) val));

      //Expression<String> dateAsText = criteriaBuilder.function("CAST", String.class, root.get(prop),
      //criteriaBuilder.literal("TEXT"));
      //predicates.add(criteriaBuilder.like(dateAsText, "%" + val.toString() + "%"));
    }
  }


  static public Predicate likePredicate(Root<?> root, CriteriaBuilder criteriaBuilder, String prop, Object val) {
    Expression<String> valueAsText = criteriaBuilder.function("CAST", String.class, root.get(prop), criteriaBuilder.literal("TEXT"));
    return criteriaBuilder.like(valueAsText, "%" + val.toString() + "%");
  }

  static public Map<String, Object> castValuesFromStringToWrapper(Map<String, String> arg0) {
    Map<String, Object> filters = new HashMap<>();
    arg0.forEach((prop, val) -> {
      Object valCasted = SpecificationsUtils._castToCorrectType(val);
      filters.put(prop, valCasted);
    });
    return filters;
  }

  static private Object _castToCorrectType(String value) {
    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
      return Boolean.parseBoolean(value);
    }

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
    }

    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException ignored) {
    }

    try {
      return LocalDate.parse(value);
    } catch (DateTimeParseException ignored) {
    }

    return value; // Default to String if no other type matches
  }

}

