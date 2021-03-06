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
package org.omnifaces.utils.text;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

@FunctionalInterface
public interface SubFormatterFactory extends BiFunction<String, Locale, SubFormatter> {

	SubFormatter createFormatter(String modifier, Locale locale);

	@Override
	default SubFormatter apply(String modifier, Locale locale) {
		return createFormatter(modifier, locale);
	}

	static Map<String, SubFormatterFactory> defaultFormatterFactories() {
		return FormatterUtil.DEFAULT_FORMATTER_FACTORIES;
	}

}
