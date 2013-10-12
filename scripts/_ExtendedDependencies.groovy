includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCompile")

if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("ivy")) {
  depends(compile)
  println "IVY DETECTED"
  includeTargets << new File("${dependencyManagerPluginDir}/scripts/_IvyDependencies.groovy")
} else if (grailsSettings.dependencyManager.getClass().simpleName.toLowerCase().contains("aether")) {
  println "AETHER DETECTED"
  includeTargets << new File("${dependencyManagerPluginDir}/scripts/_AetherDependencies.groovy")
} else {
  println "Unknown Dependency manager in use, WTF?"
}
