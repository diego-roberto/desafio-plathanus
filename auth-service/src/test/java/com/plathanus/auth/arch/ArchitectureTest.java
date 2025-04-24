package com.plathanus.auth.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@DisplayName("Testes de arquitetura do projeto")
class ArchitectureTest {

    @Test
    @DisplayName("testa se classes Controller estão no padrão correto")
    void testControllersClassesIfPatternAreCorrect() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.plathanus.auth.controller");
        ArchRule rule = classes().that().areAnnotatedWith(RestController.class)
                .and().haveSimpleNameNotStartingWith("Generic")
                .should().beAnnotatedWith(RequestMapping.class)
                .andShould().bePublic()
                .andShould().haveSimpleNameEndingWith("Controller");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("testa se classes Service estão no padrão correto")
    void testServiceClassesIfPatternAreCorrect() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.plathanus.auth.service");
        ArchRule rule = classes().that().areAnnotatedWith(Service.class)
                .and().haveSimpleNameNotStartingWith("Generic")
                .should().bePublic()
                .andShould().haveSimpleNameEndingWith("Service");

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("testa se classes de Entidade estão no padrão correto")
    void testModelClassesIfPatternAreCorrect() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.plathanus.auth.entity");
        ArchRule rule = classes().that().areAnnotatedWith(Entity.class)
                .should().bePublic();

        rule.check(importedClasses);
    }

    @Test
    @DisplayName("testa se classes Repository estão no padrão correto")
    void testRepositoriesClassesIfPatternAreCorrect() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.plathanus.auth.repository");
        ArchRule rule = classes()
                .should().beAnnotatedWith(Repository.class)
                .andShould().bePublic()
                .andShould().haveSimpleNameEndingWith("Repository");

        rule.check(importedClasses);
    }
}
