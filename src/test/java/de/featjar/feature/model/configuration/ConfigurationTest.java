/*
< * Copyright (C) 2025 FeatJAR-Development-Team
 *
 * This file is part of FeatJAR-feature-model.
 *
 * feature-model is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * feature-model is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with feature-model. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-feature-model> for further information.
 */
package de.featjar.feature.model.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.featjar.base.data.identifier.Identifiers;
import de.featjar.feature.configuration.Configuration;
import de.featjar.feature.configuration.Configuration.SelectableFeature;
import de.featjar.feature.configuration.Configuration.SelectionNotPossibleException;
import de.featjar.feature.model.FeatureModel;
import de.featjar.feature.model.IFeature;
import de.featjar.feature.model.IFeatureTree;
import de.featjar.formula.structure.IFormula;
import de.featjar.formula.structure.connective.And;
import de.featjar.formula.structure.connective.BiImplies;
import de.featjar.formula.structure.connective.Implies;
import de.featjar.formula.structure.connective.Not;
import de.featjar.formula.structure.connective.Or;
import de.featjar.formula.structure.predicate.Literal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    private static FeatureModel featureModel;
    private static Configuration configuration;

    @BeforeAll
    public static void setupTestConfiguration() {

        FeatureModel featureModel = new FeatureModel(Identifiers.newCounterIdentifier());

        // features
        IFeatureTree rootTree =
                featureModel.mutate().addFeatureTreeRoot(featureModel.mutate().addFeature("root"));
        rootTree.mutate().toAlternativeGroup();

        IFeature childFeature1 = featureModel.mutate().addFeature("Test1");
        IFeatureTree childTree1 = rootTree.mutate().addFeatureBelow(childFeature1);
        childTree1.mutate().toAlternativeGroup();

        IFeature childFeature2 = featureModel.mutate().addFeature("Test2");
        IFeatureTree childTree2 = rootTree.mutate().addFeatureBelow(childFeature2);
        childTree2.mutate().toOrGroup();

        IFeature childFeature3 = featureModel.mutate().addFeature("Test3");
        IFeatureTree childTree3 = childTree1.mutate().addFeatureBelow(childFeature3);

        IFeature childFeature4 = featureModel.mutate().addFeature("Test4");
        childTree1.mutate().addFeatureBelow(childFeature4);

        IFeature childFeature5 = featureModel.mutate().addFeature("Test5");
        IFeatureTree childTree5 = childTree2.mutate().addFeatureBelow(childFeature5);

        IFeature childFeature6 = featureModel.mutate().addFeature("Test6");
        childTree2.mutate().addFeatureBelow(childFeature6);

        IFeature childFeature7 = featureModel.mutate().addFeature("Test7");
        IFeatureTree childTree7 = rootTree.mutate().addFeatureBelow(childFeature7);
        childTree7.mutate().makeMandatory();

        IFormula formula1 = new Or(
                new And(new Literal("Test1"), new Literal("Test2")),
                new BiImplies(new Literal("Test3"), new Literal("Test4")),
                new Implies(new Literal("Test5"), new Literal("Test6")),
                new Not(new Literal("Test7")));

        // constraints
        featureModel.mutate().addConstraint(formula1);

        ConfigurationTest.featureModel = featureModel;
        ConfigurationTest.configuration = new Configuration(featureModel);
    }

    @Test
    public void testfeatureModelToConfigurationAsSet() {
        for (SelectableFeature<?> feature : configuration.getSelectableFeatures()) {
            assertFalse(featureModel.getFeature(feature.getName()).isEmpty());
        }
        for (IFeature feature : featureModel.getFeatures()) {
            assertFalse(configuration
                    .getSelectableFeature(feature.getName().orElseThrow())
                    .isEmpty());
        }
    }

    @Test
    public void testConfigurationCloning() {

        // configuration setup
        FeatureModel featureModelForCloning = new FeatureModel(Identifiers.newCounterIdentifier());

        // features
        IFeatureTree rootTree = featureModelForCloning
                .mutate()
                .addFeatureTreeRoot(featureModelForCloning.mutate().addFeature("root"));

        IFeature childFeature1 = featureModelForCloning.mutate().addFeature("Test1");
        IFeatureTree childTree1 = rootTree.mutate().addFeatureBelow(childFeature1);
        childTree1.mutate().toAlternativeGroup();

        IFeature childFeature2 = featureModelForCloning.mutate().addFeature("Test2");
        IFeatureTree childTree2 = rootTree.mutate().addFeatureBelow(childFeature2);
        childTree2.mutate().toOrGroup();

        IFeature childFeature3 = featureModelForCloning.mutate().addFeature("Test3");
        IFeatureTree childTree3 = childTree1.mutate().addFeatureBelow(childFeature3);

        IFeature childFeature4 = featureModelForCloning.mutate().addFeature("Test4");
        childTree1.mutate().addFeatureBelow(childFeature4);

        IFeature childFeature5 = featureModelForCloning.mutate().addFeature("Test5");
        IFeatureTree childTree5 = childTree2.mutate().addFeatureBelow(childFeature5);

        IFeature childFeature6 = featureModelForCloning.mutate().addFeature("Test6");
        childTree2.mutate().addFeatureBelow(childFeature6);

        IFeature childFeature7 = featureModelForCloning.mutate().addFeature("Test7");
        IFeatureTree childTree7 = rootTree.mutate().addFeatureBelow(childFeature7);
        childTree7.mutate().makeMandatory();

        IFormula formula1 = new Or(
                new And(new Literal("Test1"), new Literal("Test2")),
                new BiImplies(new Literal("Test3"), new Literal("Test4")),
                new Implies(new Literal("Test5"), new Literal("Test6")),
                new Not(new Literal("Test7")));

        // constraints
        featureModelForCloning.mutate().addConstraint(formula1);

        Configuration configurationForCloning = new Configuration(featureModelForCloning);

        // selection setup
        configurationForCloning.getSelectableFeature("root").orElseThrow().setManual(Boolean.TRUE);
        configurationForCloning.getSelectableFeature("Test1").orElseThrow().setAutomatic(Boolean.TRUE);
        configurationForCloning.getSelectableFeature("Test2").orElseThrow().setManual(Boolean.TRUE);
        configurationForCloning.getSelectableFeature("Test3").orElseThrow().setAutomatic(Boolean.FALSE);
        configurationForCloning.getSelectableFeature("Test4").orElseThrow().setManual(Boolean.FALSE);

        // execute clone()
        Configuration clonedConfiguration = configurationForCloning.clone();

        // check all selectable features
        for (SelectableFeature<?> originalFeature : configurationForCloning.getSelectableFeatures()) {
            assertNotNull(clonedConfiguration.getSelectableFeature(originalFeature.getName()));
        }

        // check whether the selections of the clonedConfiguration are the same as for configurationForCloning
        assertEquals(
                Boolean.TRUE,
                clonedConfiguration.getSelectableFeature("root").orElseThrow().getManual());
        assertEquals(
                Boolean.TRUE,
                clonedConfiguration.getSelectableFeature("Test1").orElseThrow().getAutomatic());
        assertEquals(
                Boolean.TRUE,
                clonedConfiguration.getSelectableFeature("Test2").orElseThrow().getManual());

        assertEquals(
                Boolean.FALSE,
                clonedConfiguration.getSelectableFeature("Test3").orElseThrow().getAutomatic());
        assertEquals(
                Boolean.FALSE,
                clonedConfiguration.getSelectableFeature("Test4").orElseThrow().getManual());

        assertEquals(
                null,
                clonedConfiguration.getSelectableFeature("Test5").orElseThrow().get());
        assertEquals(
                null,
                clonedConfiguration.getSelectableFeature("Test6").orElseThrow().get());
        assertEquals(
                null,
                clonedConfiguration.getSelectableFeature("Test7").orElseThrow().get());
    }

    @Test
    public void testSelectionAttributesSetterAndGetterOfAutomaticAndManual() {
        SelectableFeature<Boolean> testFeature = new SelectableFeature<>("testFeature", Boolean.class);

        // Initial state
        assertEquals(null, testFeature.getAutomatic());
        assertEquals(null, testFeature.getManual());

        // Test manual selection to SELECTED
        testFeature.setManual(Boolean.TRUE);
        assertEquals(null, testFeature.getAutomatic());
        assertEquals(Boolean.TRUE, testFeature.getManual());
        assertThrows(SelectionNotPossibleException.class, () -> {
            testFeature.setAutomatic(Boolean.FALSE);
        });
        testFeature.setManual(null);

        // Test automatic selection to SELECTED
        testFeature.setAutomatic(Boolean.TRUE);
        assertEquals(Boolean.TRUE, testFeature.getAutomatic());
        assertEquals(null, testFeature.getManual());
        assertThrows(SelectionNotPossibleException.class, () -> {
            testFeature.setManual(Boolean.FALSE);
        });
        testFeature.setAutomatic(null);

        // Test manual selection to UNSELECTED
        testFeature.setManual(Boolean.FALSE);
        assertEquals(null, testFeature.getAutomatic());
        assertEquals(Boolean.FALSE, testFeature.getManual());
        assertThrows(SelectionNotPossibleException.class, () -> {
            testFeature.setAutomatic(Boolean.TRUE);
        });
        testFeature.setManual(null);

        // Test automatic selection to UNSELECTED
        testFeature.setAutomatic(Boolean.FALSE);
        assertEquals(Boolean.FALSE, testFeature.getAutomatic());
        assertEquals(null, testFeature.getManual());
        assertThrows(SelectionNotPossibleException.class, () -> {
            testFeature.setManual(Boolean.TRUE);
        });
        testFeature.setAutomatic(null);

        // Test setting both manual and automatic to SELECTED
        testFeature.setManual(Boolean.TRUE);
        testFeature.setAutomatic(Boolean.TRUE);
        assertEquals(Boolean.TRUE, testFeature.getManual());
        assertEquals(Boolean.TRUE, testFeature.getAutomatic());
        testFeature.setManual(null);
        testFeature.setAutomatic(null);

        // Test setting both manual and automatic to UNSELECTED
        testFeature.setManual(Boolean.FALSE);
        testFeature.setAutomatic(Boolean.FALSE);
        assertEquals(Boolean.FALSE, testFeature.getManual());
        assertEquals(Boolean.FALSE, testFeature.getAutomatic());
        testFeature.setManual(null);
        testFeature.setAutomatic(null);
    }

    @Test
    public void testSelectionAttributesSetterAndGetterOfAutomaticAndManualFromATestConfiguration() {
        Configuration testConfiguration = configuration.clone();

        assertEquals(
                null,
                testConfiguration.getSelectableFeature("Test1").orElseThrow().getAutomatic());
        assertEquals(
                null,
                testConfiguration.getSelectableFeature("Test2").orElseThrow().getManual());

        testConfiguration.get("Test1").setAutomatic(Boolean.TRUE);
        testConfiguration.get("Test2").setManual(Boolean.TRUE);

        assertEquals(
                Boolean.TRUE,
                testConfiguration.getSelectableFeature("Test1").orElseThrow().getAutomatic());
        assertEquals(
                Boolean.TRUE,
                testConfiguration.getSelectableFeature("Test2").orElseThrow().getManual());
        assertEquals(
                Boolean.TRUE,
                testConfiguration.getSelectableFeature("Test1").orElseThrow().get());
        assertEquals(
                Boolean.TRUE,
                testConfiguration.getSelectableFeature("Test2").orElseThrow().get());

        testConfiguration.get("Test1").setAutomatic(Boolean.FALSE);
        testConfiguration.get("Test2").setManual(Boolean.FALSE);

        assertEquals(
                Boolean.FALSE,
                testConfiguration.getSelectableFeature("Test1").orElseThrow().getAutomatic());
        assertEquals(
                Boolean.FALSE,
                testConfiguration.getSelectableFeature("Test2").orElseThrow().getManual());
        assertEquals(
                Boolean.FALSE,
                testConfiguration.getSelectableFeature("Test1").orElseThrow().get());
        assertEquals(
                Boolean.FALSE,
                testConfiguration.getSelectableFeature("Test2").orElseThrow().get());
    }

    @Test
    public void testResetValuesShouldClearSelectionAttributes() {

        Configuration testConfiguration = configuration.clone();

        // selection setup
        testConfiguration.get("root").setManual(Boolean.TRUE);
        testConfiguration.get("Test1").setAutomatic(Boolean.TRUE);
        testConfiguration.get("Test2").setManual(Boolean.TRUE);
        testConfiguration.get("Test3").setAutomatic(Boolean.FALSE);
        testConfiguration.get("Test4").setManual(Boolean.FALSE);

        // check all SELECTED features in LinkedHashMap selectableFeatures
        assertEquals("root", testConfiguration.get("root").getName());
        assertEquals(Boolean.TRUE, testConfiguration.get("root").get());
        assertEquals("Test1", testConfiguration.get("Test1").getName());
        assertEquals(Boolean.TRUE, testConfiguration.get("Test1").get());
        assertEquals("Test2", testConfiguration.get("Test2").getName());
        assertEquals(Boolean.TRUE, testConfiguration.get("Test2").get());

        // check all UNSELECTED features in LinkedHashMap selectableFeatures
        assertEquals("Test3", testConfiguration.get("Test3").getName());
        assertEquals(Boolean.FALSE, testConfiguration.get("Test3").get());
        assertEquals("Test4", testConfiguration.get("Test4").getName());
        assertEquals(Boolean.FALSE, testConfiguration.get("Test4").get());

        // check all UNDEFINED features in LinkedHashMap selectableFeatures
        assertEquals("Test5", testConfiguration.get("Test5").getName());
        assertEquals(null, testConfiguration.get("Test5").get());
        assertEquals("Test6", testConfiguration.get("Test6").getName());
        assertEquals(null, testConfiguration.get("Test6").get());
        assertEquals("Test7", testConfiguration.get("Test7").getName());
        assertEquals(null, testConfiguration.get("Test7").get());

        // execute resetValues
        testConfiguration.reset();

        // selected, unselected and automatic features should be empty after resetValues()
        assertTrue(testConfiguration.getManualFeatures().isEmpty());
        assertTrue(testConfiguration.getAutomaticFeatures().isEmpty());

        assertEquals(null, testConfiguration.get("root").get());
        assertEquals(null, testConfiguration.get("Test1").get());
        assertEquals(null, testConfiguration.get("Test2").get());
        assertEquals(null, testConfiguration.get("Test3").get());
        assertEquals(null, testConfiguration.get("Test4").get());
        assertEquals(null, testConfiguration.get("Test5").get());
        assertEquals(null, testConfiguration.get("Test6").get());
        assertEquals(null, testConfiguration.get("Test7").get());
    }

    @Test
    public void testResetAutomaticValuesShouldOnlyClearAutomaticAttributes() {
        Configuration testConfiguration = configuration.clone();

        testConfiguration.get("Test1").setAutomatic(Boolean.TRUE);
        testConfiguration.get("Test2").setAutomatic(Boolean.FALSE);
        testConfiguration.get("Test3").setManual(Boolean.TRUE);
        testConfiguration.get("Test4").setManual(Boolean.FALSE);

        // execute resetAutomaticValues()
        testConfiguration.resetAutomatic();

        // there should be no automatic features anymore
        assertTrue(testConfiguration.getAutomaticFeatures().isEmpty());

        assertTrue(testConfiguration.get("Test1").getAutomatic() == null);
        assertTrue(testConfiguration.get("Test2").getAutomatic() == null);
        assertTrue(testConfiguration.get("Test3").getAutomatic() == null);
        assertTrue(testConfiguration.get("Test4").getAutomatic() == null);

        assertTrue(testConfiguration.get("Test1").getManual() == null);
        assertTrue(testConfiguration.get("Test2").getManual() == null);
        assertTrue(testConfiguration.get("Test3").getManual() == Boolean.TRUE);
        assertTrue(testConfiguration.get("Test4").getManual() == Boolean.FALSE);
    }
}
