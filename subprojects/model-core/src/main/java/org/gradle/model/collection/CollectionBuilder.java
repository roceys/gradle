/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.model.collection;

import org.gradle.api.Action;
import org.gradle.api.Incubating;
import org.gradle.api.Nullable;

import java.util.Set;

/**
 * Allows the adding of items to a named collection where instantiation is managed.
 *
 * @param <T> the contract type for all items
 */
@Incubating
public interface CollectionBuilder<T> {
    /**
     * Returns a collection containing the items from this collection which are of the specified type.
     *
     * @param type The type.
     * @param <S> The type.
     * @return The collection.
     */
    <S> CollectionBuilder<S> withType(Class<S> type);

    /**
     * Returns the number of items in this collection.
     *
     * @return the size of this collection.
     */
    int size();

    /**
     * Returns true if this collection contains no items.
     *
     * @return true if this collection is empty.
     */
    boolean isEmpty();

    /**
     * Returns the item with the given name, if any.
     *
     * @param name The name of the item.
     * @return The item, or null if no such item.
     */
    @Nullable
    T get(Object name);

    /**
     * Returns the item with the given name, if any.
     *
     * @param name The name of the item.
     * @return The item, or null if no such item.
     */
    @Nullable
    T get(String name);

    /**
     * Returns true if this collection contains an item with the given name.
     *
     * @param name The name of the item.
     * @return true if this collection contains an item with the given name.
     */
    boolean containsKey(Object name);

    /**
     * Returns true if this collection contains the given item.
     *
     * @param item The item.
     * @return true if this collection contains the given item.
     */
    boolean containsValue(Object item);

    /**
     * Returns the names of the items in this collection.
     *
     * @return The names
     */
    Set<String> keySet();

    /**
     * Defines an item with the given name and type T. The item is not created immediately, but is instead created as it is required.
     *
     * @param name The name.
     */
    // TODO - exception when no default type
    void create(String name);

    /**
     * Defines an item with the given name and type T. The item is not created immediately, but is instead created as it is required.
     *
     * <p>The given action is invoked to configure the item when the item is required.
     *
     * @param name The name.
     * @param configAction An action that initialises the item. The action is executed when the item is required.
     */
    // TODO - exception when no default type
    void create(String name, Action<? super T> configAction);

    /**
     * Defines an item with the given name and type. The item is not created immediately, but is instead created as it is required.
     *
     * @param name The name.
     */
    // TODO - exception when type cannot be created
    <S extends T> void create(String name, Class<S> type);

    /**
     * Defines an item with the given name and type. The item is not created immediately, but is instead created as it is required.
     *
     * <p>The given action is invoked to configure the item when the item is required.
     *
     * @param name The name.
     * @param configAction An action that initialises the item. The action is executed when the item is required.
     */
    // TODO - exception when type cannot be created
    <S extends T> void create(String name, Class<S> type, Action<? super S> configAction);

    /**
     * Applies the given action to the given item, when the item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called after any actions provided to {@link #beforeEach(org.gradle.api.Action)} and {@link #create(String,
     * org.gradle.api.Action)}.
     *
     * @param name The name.
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    void named(String name, Action<? super T> configAction);

    /**
     * Applies the given action to each item in this collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called before any actions provided to {@link #create(String, org.gradle.api.Action)}.
     *
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    void beforeEach(Action<? super T> configAction);

    /**
     * Applies the given action to each item of the given type in this collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called before any actions provided to {@link #create(String, org.gradle.api.Action)}.
     *
     * @param type The type of elements to apply the action to.
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    <S> void beforeEach(Class<S> type, Action<? super S> configAction);

    /**
     * Applies the given action to each item in the collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called after any actions provided to {@link #beforeEach(org.gradle.api.Action)} and {@link #create(String,
     * org.gradle.api.Action)}.
     *
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    void all(Action<? super T> configAction);

    /**
     * Applies the given action to each item of the given type in the collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called after any actions provided to {@link #beforeEach(org.gradle.api.Action)} and {@link #create(String,
     * org.gradle.api.Action)}.
     *
     * @param type The type of elements to apply the action to.
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    <S> void withType(Class<S> type, Action<? super S> configAction);

    /**
     * Applies the given action to each item in the collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called after any actions provided to {@link #beforeEach(org.gradle.api.Action)}, {@link #create(String,
     * org.gradle.api.Action)}, and other mutation methods.
     *
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    void afterEach(Action<? super T> configAction);

    /**
     * Applies the given action to each item of the given type in the collection, as each item is required.
     *
     * <p>The given action is invoked to configure the item when the item is required. It is called after any actions provided to {@link #beforeEach(org.gradle.api.Action)}, {@link #create(String,
     * org.gradle.api.Action)}, and other mutation methods.
     *
     * @param type The type of elements to apply the action to.
     * @param configAction An action that configures the item. The action is executed when the item is required.
     */
    <S> void afterEach(Class<S> type, Action<? super S> configAction);
}
