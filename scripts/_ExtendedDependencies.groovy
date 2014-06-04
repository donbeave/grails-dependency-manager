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
 */
includeTargets << grailsScript('_GrailsInit')
includeTargets << grailsScript('_GrailsCompile')

if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains('ivy')) {
    println 'Dependency Manager - Ivy'
    includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_IvyDependencies.groovy")
} else if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains('aether')) {
    println 'Dependency Manager - Aether'
    includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_AetherDependencies.groovy")
} else {
    println 'Unknown Dependency manager in use, WTF?'
}
