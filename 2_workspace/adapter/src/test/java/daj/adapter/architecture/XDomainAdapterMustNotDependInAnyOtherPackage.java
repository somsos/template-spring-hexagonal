package daj.adapter.architecture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class XDomainAdapterMustNotDependInAnyOtherPackage {

  static private JavaClasses allMyClasses;

  @BeforeAll
  static public void setupAll() {
    allMyClasses = new ClassFileImporter().importPackages("daj..");
  }

  //Product Adapter
  @Test
  public void productWebAdapter_mustBeAccededOnlyBy_ItselfAndProductUtils() {
      ArchRule rule = classes().that().resideInAPackage("daj.adapter.product.inWeb..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("daj.adapter.product.inWeb..", "daj.adapter.product.utils..");
      
      rule.check(allMyClasses);
  }
  

  @Test
  public void productDBAdapter_mustBeAccededOnlyBy_ItselfAndProductUtils() {
      ArchRule rule = classes().that().resideInAPackage("daj.adapter.product.outDB..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("daj.adapter.product.outDB..", "daj.adapter.product.utils..");
      
      rule.check(allMyClasses);
  }





  //User Adapter
  @Test
  public void userWebAdapter_mustBeAccededOnlyBy_ItselfAndUserUtils() {
      ArchRule rule = classes().that().resideInAPackage("daj.adapter.user.inWeb..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("daj.adapter.user.inWeb..", "daj.adapter.user.utils..");
      
      rule.check(allMyClasses);
  }
  

  @Test
  public void productDBAdapter_mustBeAccededOnlyBy_Itself() {
      ArchRule rule = classes().that().resideInAPackage("daj.adapter.user.outDB..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage(
          "daj.adapter.user.outDB..",
          "daj.adapter.user.utils..",
          //TODO: Watchout little violation here ) think it should access to dto not to entity but I should repeat code
          "daj.adapter.product.outDB.entity..",
          "daj.adapter.product.utils",
          "daj.adapter.common.."
        );
      
      rule.check(allMyClasses);
  }
}
