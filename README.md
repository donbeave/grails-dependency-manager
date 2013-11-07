Grails Extended Dependency Manager
=========================

Grails plugin that provides a simple API in the scripting/ build environment for resolving Jars
from the current DependencyManager and adding it to the compile (and provided) classpath.

This allows the classpath to be dynamically modified by plugins.

Supports both ***Aether*** and ***Ivy***

Examples
----------

For a plugin provided script that builds its own classpath

**scripts/Example.groovy**
```groovy
includeTargets << grailsScript("_GrailsInit")
includeTargets << grailsScript("_GrailsCompile")
includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_ExtendedDependencies.groovy")

target(example: "Some Example Script") {
  depends(compile)

  addCompileDependency("org.springframework.data",
      "spring-data-gemfire",
      "1.3.2.RELEASE")

  println "COMPILE=${grailsSettings.compileDependencies}" //will contain spring data

}
```

A plugin script that alters the main classpath during compilation.
**scripts/_Events.groovy**
```groovy
includeTargets << new File("${extendedDependencyManagerPluginDir}/scripts/_ExtendedDependencies.groovy")

eventCompileStart = {
  addCompileDependency("org.springframework.data",
        "spring-data-gemfire",
        "1.3.2.RELEASE")
}

eventSetClasspath = { ClassLoader rootLoader ->
  def files = addCompileDependency("org.springframework.data",
        "spring-data-gemfire",
        "1.3.2.RELEASE")

  files.each {
    rootLoader.addURL(file.toURL())
  }
}

target(example: "Some Example Script") {
  depends(compile)

  addCompileDependency("org.springframework.data",
      "spring-data-gemfire",
      "1.3.2.RELEASE")

  println "COMPILE=${grailsSettings.compileDependencies}" //will contain spring data

}
```
