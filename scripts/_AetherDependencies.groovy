includeTargets << grailsScript("_GrailsInit")

addCompileDependency = { groupId, artifactId, version ->
  println "DEP MAN=${grailsSettings.dependencyManager.getClass()}"

  def loader = grailsSettings.dependencyManager.getClass().classLoader

  def Dependency = loader.loadClass("org.sonatype.aether.graph.Dependency")
  def DefaultArtifact = loader.loadClass("org.sonatype.aether.util.artifact.DefaultArtifact")

  final dependency = Dependency.newInstance(DefaultArtifact.newInstance(
      groupId, artifactId, "jar", version), "compile")

  grailsSettings.dependencyManager.addDependency(dependency)

  org.codehaus.groovy.grails.resolve.DependencyReport report = grailsSettings.dependencyManager.resolve("compile")

  grailsSettings.compileDependencies = report.allArtifacts
  return report.allArtifacts
}

