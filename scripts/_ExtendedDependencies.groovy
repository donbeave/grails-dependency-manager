includeTargets << grailsScript("_GrailsInit")


if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("ivy")) {
  println "IVY DETECTED"
  includeTargets << new File("${dependencyManagerPluginDir}/scripts/_IvyDependencies.groovy")
} else if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("aether")) {
  println "AETHER DETECTED"
  includeTargets << new File("${dependencyManagerPluginDir}/scripts/_AetherDependencies.groovy")
} else {
  println "Unknown Dependency manager in use, WTF?"
}
