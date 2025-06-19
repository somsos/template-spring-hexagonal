package daj.adapter.user.outDB.helpers;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.data.jpa.domain.Specification;

import daj.adapter.user.outDB.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecification {

  public static Specification<UserEntity> columnEqual(Map<String, String> arg0) {
    return new Specification<UserEntity>() {
      private static final long serialVersionUID = 1L;

      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final Map<String, Object> filters = SpecificationsUtils.castValuesFromStringToWrapper(arg0);
        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((prop, val) -> {
          SpecificationsUtils.addEqualPredicateForColumn(root, criteriaBuilder, predicates, prop, val);
        });

        // Combine predicates using OR condition
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
      }

    };

  }// columnEqual() function ends

  public static Specification<UserEntity> anyColumnInclude(final String overallQuery, final String[] columns) {
    return new Specification<UserEntity>() {
      private static final long serialVersionUID = 1L;
      
      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
          final Map<String, Date> arg0 = new HashMap<>();
          arg0.put("createdAt", new Date());
          List<Predicate> predicates = new ArrayList<>();

          arg0.forEach((prop, val) -> {
              predicates.add(SpecificationsUtils.likePredicate(root, criteriaBuilder, prop, val));
          });

          return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
      }
    };
  }
}
