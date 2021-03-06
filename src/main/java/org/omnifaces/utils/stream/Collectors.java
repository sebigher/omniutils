/*
 * Copyright 2020 OmniFaces
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.omnifaces.utils.stream;

import static java.util.Comparator.naturalOrder;
import static java.util.function.Function.identity;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

public final class Collectors {

	private Collectors() {
	}

	public static <T, K> Collector<T, ?, Map<K, T>> toMap(Function<? super T, ? extends K> keyMapper) {
		return java.util.stream.Collectors.toMap(keyMapper, identity());
	}

	public static <T, K> Collector<T, ?, Map<K, T>> toLinkedMap(Function<? super T, ? extends K> keyMapper) {
		return java.util.stream.Collectors.toMap(keyMapper, identity(), (l, r) -> l, LinkedHashMap::new);
	}

	public static <T> Collector<T, ?, Set<T>> toLinkedSet() {
		return java.util.stream.Collectors.toCollection(LinkedHashSet::new);
	}

	public static <T> Collector<T, ?, Void> forEachBatch(Consumer<List<T>> batchConsumer, int batchSize) {
		return new ForEachBatchCollector<>(batchConsumer, batchSize);
	}

	public static <T, R1, R2> Collector<T, ?, Map.Entry<R1, R2>> combine(Collector<T, ?, R1> collector1, Collector<T, ?, R2> collector2) {
		return new CombinedCollector<>(collector1, collector2);
	}

	/**
	 * Returns a collector which takes the elements of the current stream and returns a new stream with the same elements in reverse order.
	 *
	 * <p>This collector will collect all elements from a stream into memory in order to return the reversed stream. As a result this collector may not
	 * be suitable for extremely large or infinite streams.
	 * </p>
	 *
	 * @param <T> The type of the elements
	 * @return A Collector that returns the elements of a stream in reverse order.
	 */
	public static <T> Collector<T, ?, Stream<T>> reversedStream() {
		return new ReversedStreamCollector<>();
	}

	/**
	 * Returns a collector which will return the last element of a stream, if present.
	 *
	 * @param <T> The type of the elements
	 *
	 * @return An {@link Optional} containing the last element of the stream or {@link Optional#empty()} if the stream is empty.
	 */
	public static <T> Collector<T, ?, Optional<T>> findLast() {
		return new FindLastCollector<>();
	}

	public static <T extends Comparable<T>> Collector<T, ?, Summary<T>> summary() {
		return summaryBy(naturalOrder());
	}

	public static <T> Collector<T, ?, Summary<T>> summaryBy(Comparator<? super T> comparator) {
		return new SummaryCollector<>(comparator);
	}
}
