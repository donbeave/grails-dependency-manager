includeTargets << grailsScript("_GrailsInit")

addTestDependency = { group, name, version, type = null ->
  getDeps(group, name, version, type).each { file ->
    grailsSettings.testDependencies << file
    return [file]
  }
}

addProvidedDependency = { group, name, version, type = null ->
  getDeps(group, name, version, type).each { file ->
    grailsSettings.providedDependencies << file
    return [file]
  }
}

addCompileDependency = { group, name, version, type = null ->
  getDeps(group, name, version, type).each { file ->
    grailsSettings.compileDependencies << file
    return [file]
  }
}

def getDeps(group, name, version, type) {
  def extraAttrs = type == null ? [:] : ['m:classifier': type]

  def ModuleRevisionId = grailsSettings.dependencyManager.getClass().classLoader.loadClass("org.apache.ivy.core.module.id.ModuleRevisionId")

  def mrid = ModuleRevisionId.newInstance(group, name, version, extraAttrs)
  addModuleToDependencies(mrid, 'compile')
}

def addModuleToDependencies(mrid, type) {

  def ResolveOptions = grailsSettings.dependencyManager.getClass().classLoader.loadClass("org.apache.ivy.core.resolve.ResolveOptions")
  def DownloadOptions = grailsSettings.dependencyManager.getClass().classLoader.loadClass("org.apache.ivy.core.resolve.DownloadOptions")

  def report = grailsSettings.dependencyManager.resolveEngine.resolve(mrid, ResolveOptions.newInstance(confs: [type] as String[], transitive:false, outputReport: true, download: true, useCacheOnly: false), false)

  if (report.hasError()) {
    println "Ivy Extended Dependency resolution has errors, exiting"
    exit(1)
  }
  report.artifacts.collect {
    def rep = grailsSettings.dependencyManager.resolveEngine.download(it, DownloadOptions.newInstance(log: DownloadOptions.LOG_DOWNLOAD_ONLY))
    rep.localFile
  }
}
