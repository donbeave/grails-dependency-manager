/*
 * Copyright 2013 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class ExtendedDependencyManagerGrailsPlugin {
    // the plugin version
    def version = '0.5.5'
    def grailsVersion = '2.0 > *'

    def title = 'Extended Dependency Manager Plugin'
    def author = 'David Dawson'
    def authorEmail = 'david.dawson@simplicityitself.com'
    def description = '''\
    Provides a simple API in the Grails scripting environment to resolve new libraries from the remote repositories
    and add them to the various classpaths.
'''
    def documentation = 'http://grails.org/plugin/dependency-manager'
    def license = 'APACHE'
    def organization = [name: 'Simplicity Itself', url: 'http://www.simplicityitself.com/']
    def issueManagement = [system: 'GitHub', url: 'https://github.com/simplicityitself/grails-dependency-manager/issues']
    def scm = [url: 'https://github.com/simplicityitself/grails-dependency-manager']
}
