
includeTargets << grailsScript("_GrailsInit")

addTestDependency = { groupId, artifactId, version, type = "jar" ->
  def files = getDeps(groupId, artifactId, version, type)
  grailsSettings.testDependencies = files
  return files
}

addProvidedDependency = { groupId, artifactId, version, type = "jar" ->
  def files = getDeps(groupId, artifactId, version, type)
  grailsSettings.providedDependencies = files
  return files
}

addCompileDependency = { groupId, artifactId, version, type = "jar" ->
  def files = getDeps(groupId, artifactId, version, type)
  grailsSettings.compileDependencies = files
  return files
}

addRuntimeDependency = { groupId, artifactId, version, type = "jar" ->
  def files = getDeps(groupId, artifactId, version, type)
  grailsSettings.runtimeDependencies = files
  return files
}

def getDeps( groupId, artifactId, version, type) {
  def loader = grailsSettings.dependencyManager.getClass().classLoader

  def Dependency = loader.loadClass("org.sonatype.aether.graph.Dependency")
  def DefaultArtifact = loader.loadClass("org.sonatype.aether.util.artifact.DefaultArtifact")

  final dependency = Dependency.newInstance(DefaultArtifact.newInstance(
          groupId, artifactId, type, version), "compile")

  grailsSettings.dependencyManager.addDependency(dependency)

  grailsSettings.dependencyManager.resolve("compile").resolvedArtifacts.findAll {
    it.dependency.group == groupId && it.dependency.name == artifactId
  }.collect {
    it.file
  }
}

