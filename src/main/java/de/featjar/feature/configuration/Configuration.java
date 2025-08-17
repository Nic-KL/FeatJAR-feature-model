/*
 * Copyright (C) 2025 FeatJAR-Development-Team
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
package de.featjar.feature.configuration;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Result;
import de.featjar.feature.model.IFeature;
import de.featjar.feature.model.IFeatureModel;
import de.featjar.formula.VariableMap;
import de.featjar.formula.assignment.BooleanAssignment;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a configuration and provides operations for the configuration process.
 */
public class Configuration implements Cloneable {

    public static final class FeatureNotFoundException extends RuntimeException {
        private static final long serialVersionUID = -4112750233088590678L;
    }

    public static final class IllegalSelectionTypeException extends RuntimeException {
        private static final long serialVersionUID = 1793844229871267311L;

        public IllegalSelectionTypeException(SelectableFeature<?> feature, Object selection) {
            super(String.format(
                    "Trying to set the value %s (of type %s) to the feature %s (of type %s)",
                    String.valueOf(selection),
                    String.valueOf(selection.getClass()),
                    feature.getName(),
                    feature.getType()));
        }
    }

    public static final class SelectionNotPossibleException extends RuntimeException {
        private static final long serialVersionUID = 1793844229871267311L;

        public SelectionNotPossibleException(String feature, Object selection) {
            super(String.format("The feature \"%s\" cannot be set to %s", feature, String.valueOf(selection)));
        }
    }

    public static class SelectableFeature<T> {

        private final String name;
        private final Class<T> type;
        private T manual, automatic;

        public SelectableFeature(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }

        private SelectableFeature(SelectableFeature<T> oldSelectableFeature) {
            name = oldSelectableFeature.name;
            type = oldSelectableFeature.type;
        }

        public String getName() {
            return name;
        }

        public Class<T> getType() {
            return type;
        }

        public T get() {
            return automatic == null ? manual : automatic;
        }

        public T getManual() {
            return manual;
        }

        public T getAutomatic() {
            return automatic;
        }

        @SuppressWarnings("unchecked")
        public void setManual(Object selection) {
            checkIfSelectionPossible(selection, automatic);
            manual = (T) selection;
        }

        @SuppressWarnings("unchecked")
        public void setAutomatic(Object selection) {
            checkIfSelectionPossible(selection, manual);
            automatic = (T) selection;
        }

        public void checkIfSelectionPossible(Object selection, Object current) {
            if (selection != null) {
                if (!type.isInstance(selection)) {
                    throw new IllegalSelectionTypeException(this, selection);
                }
                if ((current != null) && (current != selection)) {
                    throw new SelectionNotPossibleException(getName(), selection);
                }
            }
        }

        /**
         * Converts the automatic selection into a manual selection.
         */
        public void makeManual() {
            if (automatic != null) {
                manual = automatic;
                automatic = null;
            }
        }

        public void reset() {
            manual = null;
            automatic = null;
        }

        public void resetAutomatic() {
            automatic = null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public SelectableFeature<T> clone() {
            if (!this.getClass().equals(SelectableFeature.class)) {
                try {
                    return (SelectableFeature<T>) super.clone();
                } catch (final CloneNotSupportedException e) {
                    FeatJAR.log().error(e);
                    throw new RuntimeException("Cloning is not supported for " + this.getClass());
                }
            }
            SelectableFeature<T> selectableFeature = new SelectableFeature<>(this);
            selectableFeature.manual = this.manual;
            selectableFeature.automatic = this.automatic;
            return selectableFeature;
        }

        @Override
        public String toString() {
            return String.format("%s = %s", name, String.valueOf(get()));
        }
    }

    private final LinkedHashMap<String, SelectableFeature<?>> selectableFeatures = new LinkedHashMap<>();

    public Configuration() {}

    /**
     * Creates a configuration with the same features as the given feature model.
     *
     * @param featureModel the underlying feature model.
     */
    public Configuration(IFeatureModel featureModel) {
        for (final IFeature child : featureModel.getFeatures()) {
            String featureName = child.getName().get();
            selectableFeatures.put(featureName, new SelectableFeature<>(featureName, child.getType()));
        }
    }

    /**
     * Copy constructor. Copies the status of a given configuration.
     *
     * @param configuration The configuration to clone
     */
    protected Configuration(Configuration configuration) {
        for (final Entry<String, SelectableFeature<?>> entry : configuration.selectableFeatures.entrySet()) {
            selectableFeatures.put(entry.getKey(), entry.getValue().clone());
        }
    }

    /**
     * Creates configuration from literal set.
     *
     * @param booleanAssignment contains literals with truth values.
     * @param variableMap mapping of variable names to indices. Is used to link a literal index in a {@link BooleanAssignment}.
     */
    public Configuration(BooleanAssignment booleanAssignment, VariableMap variableMap) {
        for (final String variable : variableMap.getVariableNames()) {
            SelectableFeature<Boolean> value = new SelectableFeature<>(variable, Boolean.class);
            selectableFeatures.put(variable, value);
        }
        adopt(booleanAssignment, variableMap);
    }

    /**
     * Adopts the values from this assignment.
     *
     * @param assignment the assignment to adopt
     * @param variableMap maps the literals in the assignments to feature names
     */
    public void adopt(BooleanAssignment assignment, VariableMap variableMap) {
        assignment.stream().filter(literal -> literal != 0).forEach(literal -> variableMap
                .get(Math.abs(literal))
                .flatMap(this::getSelectableFeature)
                .ifPresent(f -> f.setManual(literal > 0)));
    }

    /**
     * Adopts the values from the given configuration.
     * Features not
     *
     * @param configuration the configuration to adopt
     */
    public void adopt(Configuration configuration) {
        for (final Entry<String, SelectableFeature<?>> entry : configuration.selectableFeatures.entrySet()) {
            getSelectableFeature(entry.getKey()).ifPresent(f -> {
                f.setManual(entry.getValue().manual);
                f.setAutomatic(entry.getValue().automatic);
            });
        }
    }

    public List<SelectableFeature<?>> select(Collection<String> features) {
        return select(features.stream()).collect(Collectors.toList());
    }

    public Stream<SelectableFeature<?>> select(Stream<String> features) {
        return features.map(this::getSelectableFeature)
                .filter(Result::isPresent)
                .map(Result::get);
    }

    public Collection<SelectableFeature<?>> getSelectableFeatures() {
        return Collections.unmodifiableCollection(selectableFeatures.values());
    }

    /**
     * {@return a list of all features that have a manual and no automatic value}
     */
    public List<SelectableFeature<?>> getManualFeatures() {
        return getFeatureStream()
                .filter(f -> f.getAutomatic() == null && f.getManual() != null)
                .collect(Collectors.toList());
    }

    /**
     * {@return a list of all features that have a automatic value}
     */
    public List<SelectableFeature<?>> getAutomaticFeatures() {
        return getFeatureStream().filter(f -> f.getAutomatic() != null).collect(Collectors.toList());
    }

    private Stream<SelectableFeature<?>> getFeatureStream() {
        return selectableFeatures.entrySet().stream().map(Entry::getValue);
    }

    public Result<SelectableFeature<?>> getSelectableFeature(String name) {
        return Result.ofNullable(name).map(selectableFeatures::get);
    }

    public SelectableFeature<?> get(String name) {
        return Result.ofNullable(name).map(selectableFeatures::get).orElseThrow();
    }

    public Result<SelectableFeature<?>> getSelectableFeature(IFeature feature) {
        return feature.getName().<SelectableFeature<?>>map(selectableFeatures::get);
    }

    public List<String> getFeatureNames(final Object selection) {
        return getFeatureStream()
                .filter(f -> f.get() == selection)
                .map(SelectableFeature::getName)
                .collect(Collectors.toList());
    }

    /**
     * Turns all automatic into manual values.
     */
    public void makeManual() {
        getFeatureStream().forEach(SelectableFeature::makeManual);
    }

    /**
     * Resets all values to undefined.
     */
    public void reset() {
        getFeatureStream().forEach(SelectableFeature::reset);
    }

    /**
     * Resets all automatic values to undefined.
     */
    public void resetAutomatic() {
        getFeatureStream().forEach(SelectableFeature::resetAutomatic);
    }

    /**
     * Resets automatic values that equal the given selection.
     *
     * @param selection the selection to reset
     */
    public void resetAutomatic(Object selection) {
        getFeatureStream().filter(f -> f.getAutomatic() == selection).forEach(SelectableFeature::resetAutomatic);
    }

    /**
     * Creates and returns a copy of this configuration.
     *
     * @return configuration a clone of this configuration.
     */
    @Override
    public Configuration clone() {
        if (!this.getClass().equals(Configuration.class)) {
            try {
                return (Configuration) super.clone();
            } catch (final CloneNotSupportedException e) {
                FeatJAR.log().error(e);
                throw new RuntimeException("Cloning is not supported for " + this.getClass());
            }
        }
        return new Configuration(this);
    }

    @Override
    public String toString() {
        return getFeatureStream().map(SelectableFeature::toString).collect(Collectors.joining("\n"));
    }
}
