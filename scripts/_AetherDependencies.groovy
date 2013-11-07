includeTargets << grailsScript("_GrailsInit")

addTestDependency = { groupId, artifactId, version, type = "jar" ->
  org.codehaus.groovy.grails.resolve.DependencyReport report = getDeps(groupId, artifactId, version, type)
  grailsSettings.testDependencies = report.allArtifacts
  return report.allArtifacts
}

addProvidedDependency = { groupId, artifactId, version, type = "jar" ->
  org.codehaus.groovy.grails.resolve.DependencyReport report = getDeps(groupId, artifactId, version, type)
  grailsSettings.providedDependencies = report.allArtifacts
  return report.allArtifacts
}

addCompileDependency = { groupId, artifactId, version, type = "jar" ->
  org.codehaus.groovy.grails.resolve.DependencyReport report = getDeps(groupId, artifactId, version, type)
  grailsSettings.compileDependencies = report.allArtifacts
  return report.allArtifacts
}

def getDeps( groupId, artifactId, version, type) {
  def loader = grailsSettings.dependencyManager.getClass().classLoader

  def Dependency = loader.loadClass("org.sonatype.aether.graph.Dependency")
  def DefaultArtifact = loader.loadClass("org.sonatype.aether.util.artifact.DefaultArtifact")

  final dependency = Dependency.newInstance(DefaultArtifact.newInstance(
      groupId, artifactId, type, version), "compile")

  grailsSettings.dependencyManager.addDependency(dependency)

  grailsSettings.dependencyManager.resolve("compile")
}

