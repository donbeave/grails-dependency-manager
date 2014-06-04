/*
 * Copyright 2013 the original author or authors
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

/**
 * @author <a href='david.dawson@simplicityitself.com'>David Dawson</a>
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
includeTargets << grailsScript('_GrailsInit')

latestReport = null

addTestDependency = { groupId, artifactId, version, type = 'jar' ->
    def files = getDeps(groupId, artifactId, version, type)
    grailsSettings.testDependencies.addAll(files)
    return files
}

addProvidedDependency = { groupId, artifactId, version, type = 'jar' ->
    def files = getDeps(groupId, artifactId, version, type)
    grailsSettings.providedDependencies.addAll(files)
    return files
}

addCompileDependency = { groupId, artifactId, version, type = 'jar' ->
    def files = getDeps(groupId, artifactId, version, type)
    grailsSettings.compileDependencies.addAll(files)
    return files
}

addRuntimeDependency = { groupId, artifactId, version, type = 'jar' ->
    def files = getDeps(groupId, artifactId, version, type)
    grailsSettings.runtimeDependencies.addAll(files)
    return files
}

def getDeps(groupId, artifactId, version, type) {
    def loader = grailsSettings.dependencyManager.getClass().classLoader

    def Dependency
    try {
        Dependency = loader.loadClass('org.sonatype.aether.graph.Dependency')
    } catch (ClassNotFoundException ex) {
        Dependency = loader.loadClass('org.eclipse.aether.graph.Dependency')
    }
    def DefaultArtifact
    try {
        DefaultArtifact = loader.loadClass('org.sonatype.aether.util.artifact.DefaultArtifact')
    } catch (ClassNotFoundException ex) {
        DefaultArtifact = loader.loadClass('org.eclipse.aether.artifact.DefaultArtifact')
    }
    final dependency = Dependency.newInstance(DefaultArtifact.newInstance(
            groupId, artifactId, type, version), 'compile')

    def result = []

    if (latestReport) {
        result = latestReport.resolvedArtifacts.findAll {
            it.dependency.group == groupId && it.dependency.name == artifactId
        }.collect {
            it.file
        }
    }

    if (result) {
        return result
    }

    grailsSettings.dependencyManager.addDependency(dependency)

    // save result to cache
    latestReport = grailsSettings.dependencyManager.resolve('compile')

    latestReport.resolvedArtifacts.findAll {
        it.dependency.group == groupId && it.dependency.name == artifactId
    }.collect {
        it.file
    }
}