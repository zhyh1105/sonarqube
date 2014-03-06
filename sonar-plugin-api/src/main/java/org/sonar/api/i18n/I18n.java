/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.i18n;

import org.sonar.api.BatchComponent;
import org.sonar.api.ServerComponent;

import javax.annotation.Nullable;

import java.util.Date;
import java.util.Locale;

/**
 * Main component that provides translation facilities.
 *
 * @since 2.10
 */
public interface I18n extends ServerComponent, BatchComponent {

  /**
   * Searches the message of the <code>key</code> for the <code>locale</code> in the list of available bundles.
   * <br>
   * If not found in any bundle, <code>defaultValue</code> is returned.
   * <p/>
   * If additional parameters are given (in the objects list), the result is used as a message pattern
   * to use in a MessageFormat object along with the given parameters.
   *
   * @param locale       the locale to translate into
   * @param key          the key of the pattern to translate
   * @param defaultValue the default pattern returned when the key is not found in any bundle
   * @param parameters   the parameters used to format the message from the translated pattern.
   * @return the message formatted with the translated pattern and the given parameters
   */
  String message(final Locale locale, final String key, @Nullable final String defaultValue, final Object... parameters);

  /**
   * Return the distance in time for a duration in milliseconds.
   * <br>
   * Examples :
   * <ul>
   * <li>age(Locale.ENGLISH, 1000) -> less than a minute</li>
   * <li>age(Locale.ENGLISH, 60000) -> about a minute</li>
   * <li>age(Locale.ENGLISH, 120000) -> 2 minutes</li>
   * <li>age(Locale.ENGLISH, 3600000) -> about an hour</li>
   * <li>age(Locale.ENGLISH, 7200000) -> 2 hours</li>
   * <li>age(Locale.ENGLISH, 86400000) -> a day</li>
   * <li>age(Locale.ENGLISH, 172800000) -> 2 days</li>
   * </ul>
   *
   * @since 4.2
   */
  String age(Locale locale, long durationInMillis);

  /**
   * Return the distance in time between two dates.
   *
   * @see I18n#age(java.util.Locale, long durationInMillis)
   * @since 4.2
   */
  String age(Locale locale, Date fromDate, Date toDate);

  /**
   * Reports the distance in time a date and now.
   *
   * @see I18n#age(java.util.Locale, java.util.Date, java.util.Date)
   * @since 4.2
   */
  String ageFromNow(Locale locale, Date date);

  /**
   * Return the formatted datetime.
   * <br>
   * Example : formatDateTime(Locale.ENGLISH, DateUtils.parseDateTime("2014-01-22T19:10:03+0100")) -> Jan 22, 2014 7:10 PM
   *
   * @since 4.2
   */
  String formatDateTime(Locale locale, Date date);

  /**
   * Return the formatted date.
   * <br>
   * Example : formatDateTime(Locale.ENGLISH, DateUtils.parseDateTime("2014-01-22")) -> Jan 22, 2014
   *
   * @since 4.2
   */
  String formatDate(Locale locale, Date date);

}
