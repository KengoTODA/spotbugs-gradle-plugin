/*
 * Contributions to SpotBugs
 * Copyright (C) 2018, Kengo TODA
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.github.spotbugs;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @see <a href="https://github.com/spotbugs/spotbugs-gradle-plugin/issues/31">GitHub issue</a>
 */
public class Issue31Test {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void createProject() throws IOException {
      Files.copy(Paths.get("src/test/resources/Issue31.gradle"), folder.getRoot().toPath().resolve("build.gradle"),
          StandardCopyOption.COPY_ATTRIBUTES);

      File sourceDir = folder.newFolder("src", "main", "java");
      File to = new File(sourceDir, "Foo.java");
      File from = new File("src/test/java/com/github/spotbugs/Foo.java");
      Files.copy(from.toPath(), to.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
    }

    @Test
    public void test() throws Exception {
        BuildResult result = GradleRunner.create().withProjectDir(folder.getRoot())
                .withArguments(Arrays.asList("spotbugsMain")).withPluginClasspath().build();
    }
}
