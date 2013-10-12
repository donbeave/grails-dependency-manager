includeTargets << grailsScript("_GrailsInit")

addCompileDependency = { group, name, version, type = null ->
  def extraAttrs = type == null ? [:] : ['m:classifier': type]

  def ModuleRevisionId = grailsSettings.dependencyManager.getClass().classLoader.loadClass("org.apache.ivy.core.module.id.ModuleRevisionId")

  def mrid = ModuleRevisionId.newInstance(group, name, version, extraAttrs)
  def ret = addModuleToDependencies(mrid, 'compile')
  ret.each { file ->
    // add artifacts to the list of Grails provided dependencies
    // this enables SpringSource STS to build Eclipse's classpath properly
    if (grailsSettings.metaClass.hasProperty(grailsSettings, "providedDependencies")) {
      if (!grailsSettings.providedDependencies.contains(file)) {
        grailsSettings.providedDependencies << file
      }
    }
    println "File is ${file}"
    grailsSettings.compileDependencies << file
  }
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
