package daj.adapter.architecture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class PackagesInternalOnlyShouldBeAccededBySiblingVisible {

  static private JavaClasses allMyClasses;

  @BeforeAll
  static public void setupAll() {
    allMyClasses = new ClassFileImporter().importPackages("daj..");
  }
  

  @Test
  public void productInternals_onlyShouldHaveDependenciesIn_productVisible() {
      ArchRule rule = classes().that().resideInAPackage("daj.product.internal..")
          .should().onlyHaveDependentClassesThat().resideInAnyPackage("daj.product.visible..");
      
      rule.check(allMyClasses);
  }

  @Test
  public void usersInternals_onlyShouldHaveDependenciesIn_userVisible() {
      ArchRule rule = classes().that().resideInAPackage("daj.user.internal..")
          .should().onlyHaveDependentClassesThat().resideInAnyPackage("daj.user.visible..");
      
      rule.check(allMyClasses);
  }
  
}
