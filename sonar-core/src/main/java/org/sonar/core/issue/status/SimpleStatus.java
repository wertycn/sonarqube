/*
 * SonarQube
 * Copyright (C) 2009-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.core.issue.status;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.issue.Issue;

public enum SimpleStatus {


  OPEN,
  CONFIRMED,
  FALSE_POSITIVE,
  ACCEPTED,
  FIXED;

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStatus.class);

  @CheckForNull
  public static SimpleStatus of(String status, @Nullable String resolution) {
    switch (status) {
      case Issue.STATUS_OPEN:
      case Issue.STATUS_REOPENED:
        return SimpleStatus.OPEN;
      case Issue.STATUS_CONFIRMED:
        return SimpleStatus.CONFIRMED;
      case Issue.STATUS_CLOSED:
        return SimpleStatus.FIXED;
      // Security hotspot should not return simple status as they are deprecated.
      case Issue.STATUS_REVIEWED:
      case Issue.STATUS_TO_REVIEW:
        return null;
      default:
    }
    if (Issue.STATUS_RESOLVED.equals(status) && resolution != null) {
      switch (resolution) {
        case Issue.RESOLUTION_FALSE_POSITIVE:
          return SimpleStatus.FALSE_POSITIVE;
        case Issue.RESOLUTION_WONT_FIX:
          return SimpleStatus.ACCEPTED;
        case Issue.RESOLUTION_FIXED:
          return SimpleStatus.FIXED;
        default:
      }
    }
    LOGGER.warn("Can't find mapped simple status for status '{}' and resolution '{}'", status, resolution);
    return null;
  }
}
