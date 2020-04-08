package com.thomasory.guessmycategory;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.thomasory.guessmycategory");

        noClasses()
            .that()
            .resideInAnyPackage("com.thomasory.guessmycategory.service..")
            .or()
            .resideInAnyPackage("com.thomasory.guessmycategory.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.thomasory.guessmycategory.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
