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
package de.featjar.feature.model;

import de.featjar.base.data.IAttributable;
import de.featjar.base.data.Range;
import de.featjar.base.data.Result;
import de.featjar.base.tree.structure.ARootedTree;
import de.featjar.base.tree.structure.IRootedTree;
import de.featjar.feature.model.FeatureTree.Group;
import de.featjar.feature.model.mixins.IHasFeatureTree;
import java.util.List;

/**
 * An ordered {@link ARootedTree} labeled with {@link Feature features}.
 * Implements some concepts from feature-oriented domain analysis, such as
 * mandatory/optional features and groups.
 *
 * @author Elias Kuiter
 */
public interface IFeatureTree extends IRootedTree<IFeatureTree>, IAttributable, IHasFeatureTree {

    IFeature getFeature();

    List<Group> getGroups();

    Group getGroup(int groupID);

    List<IFeatureTree> getChildren(int groupID);

    Group getParentGroup();

    int getFeatureCardinalityLowerBound();

    int getFeatureCardinalityUpperBound();

    default boolean isMandatory() {
        return getFeatureCardinalityLowerBound() > 0;
    }

    default boolean isOptional() {
        return getFeatureCardinalityLowerBound() <= 0;
    }

    default IMutableFeatureTree mutate() {
        return (IMutableFeatureTree) this;
    }

    static interface IMutableFeatureTree extends IFeatureTree, IMutatableAttributable {

        default IFeatureTree addFeatureBelow(IFeature newFeature) {
            return addFeatureBelow(newFeature, getChildrenCount(), 0);
        }

        default IFeatureTree addFeatureBelow(IFeature newFeature, int index) {
            return addFeatureBelow(newFeature, index, 0);
        }

        default IFeatureTree addFeatureBelow(IFeature newFeature, int index, int groupID) {
            FeatureTree newTree = new FeatureTree(newFeature);
            addChild(index, newTree);
            newTree.setParentGroupID(groupID);
            return newTree;
        }

        default IFeatureTree addFeatureAbove(IFeature newFeature) {
            FeatureTree newTree = new FeatureTree(newFeature);
            Result<IFeatureTree> parent = getParent();
            if (parent.isPresent()) {
                parent.get().replaceChild(this, newTree);
            }
            newTree.addChild(this);
            setParentGroupID(0);
            return newTree;
        }

        default void removeFromTree() { // TODO what about the containing constraints?
            Result<IFeatureTree> parent = getParent();
            if (parent.isPresent()) {
                int childIndex = parent.get().getChildIndex(this).orElseThrow();
                parent.get().removeChild(this);
                // TODO improve group handling, probably needs slicing
                for (Group group : getGroups()) {
                    parent.get().mutate().addCardinalityGroup(group.getLowerBound(), group.getUpperBound());
                }
                for (IFeatureTree child : getChildren()) {
                    parent.get().mutate().addChild(childIndex++, child);
                }
            }
        }

        void setParentGroupID(int groupID);

        void setFeatureCardinality(Range featureRange);

        void makeMandatory();

        void makeOptional();

        int addCardinalityGroup(int lowerBound, int upperBound);

        default int addCardinalityGroup(Range groupRange) {
            return addCardinalityGroup(groupRange.getLowerBound(), groupRange.getUpperBound());
        }

        default int addAndGroup() {
            return addCardinalityGroup(0, Range.OPEN);
        }

        default int addAlternativeGroup() {
            return addCardinalityGroup(1, 1);
        }

        default int addOrGroup() {
            return addCardinalityGroup(1, Range.OPEN);
        }

        /**
         * Change children group to cardinality group.
         *
         * @param groupId          the id of the group to change
         * @param groupCardinality the new cardinality
         */
        void toCardinalityGroup(int groupId, Range groupCardinality);

        /**
         * Change children group to and group.
         *
         * @param groupId the id of the group to change
         */
        void toAndGroup(int groupId);

        /**
         * Change children group to or group.
         *
         * @param groupId the id of the group to change
         */
        void toOrGroup(int groupId);

        /**
         * Change children group to alternative group.
         *
         * @param groupId the id of the group to change
         */
        void toAlternativeGroup(int groupId);

        /**
         * Change first children group to cardinality group.
         *
         * @param groupCardinality the new cardinality
         */
        default void toCardinalityGroup(Range groupCardinality) {
            toCardinalityGroup(0, groupCardinality);
        }

        /**
         * Change first children group to and group.
         */
        default void toAndGroup() {
            toAndGroup(0);
        }

        /**
         * Change first children group to alternative group.
         */
        default void toAlternativeGroup() {
            toAlternativeGroup(0);
        }

        /**
         * Change first children group to or group.
         */
        default void toOrGroup() {
            toOrGroup(0);
        }
    }
}
