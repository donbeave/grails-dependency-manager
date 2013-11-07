includeTargets << grailsScript("_GrailsInit")
includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_ExtendedDependencies.groovy")

target(test: "The description of the script goes here!") {

  addCompileDependency("org.springframework.data",
      "spring-data-gemfire",
      "1.3.2.RELEASE")

  println "COMPILE=${grailsSettings.compileDependencies}"
}

setDefaultTarget(test)

