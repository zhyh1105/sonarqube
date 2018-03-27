/*
 * SonarQube
 * Copyright (C) 2009-2018 SonarSource SA
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
package org.sonar.server.project;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.function.Consumer;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class ProjectLifeCycleListenersImpl implements ProjectLifeCycleListeners {
  private static final Logger LOG = Loggers.get(ProjectLifeCycleListenersImpl.class);

  private final ProjectLifeCycleListener[] listeners;

  /**
   * Used by Pico when there is no ProjectLifeCycleListener implementation in container.
   */
  public ProjectLifeCycleListenersImpl() {
    this.listeners = new ProjectLifeCycleListener[0];
  }

  public ProjectLifeCycleListenersImpl(ProjectLifeCycleListener[] listeners) {
    this.listeners = listeners;
  }

  @Override
  public void onProjectDeleted(Project project) {
    Preconditions.checkNotNull(project, "project can't be null");

    Arrays.stream(listeners)
      .forEach(safelyCallListener(listener -> listener.onProjectDeleted(project)));
  }

  private static Consumer<ProjectLifeCycleListener> safelyCallListener(Consumer<ProjectLifeCycleListener> task) {
    return listener -> {
      try {
        task.accept(listener);
      } catch (Error | Exception e) {
        LOG.error("Call to ProjectLifeCycleListener \"{}\" failed", listener.getClass(), e);
      }
    };
  }
}