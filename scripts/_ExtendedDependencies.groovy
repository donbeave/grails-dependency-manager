includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCompile")

if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("ivy")) {
  println "Dependency Manager - Ivy"
  includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_IvyDependencies.groovy")
} else if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("aether")) {
  println "Dependency Manager - Aether"
  includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_AetherDependencies.groovy")
} else {
  println "Unknown Dependency manager in use, WTF?"
}
