#!/usr/bin/env groovy

pipelineJob('test_web_git') {
    displayName('Web - git')
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/jdzsz/monorepo.git')
                    }
                    branches('*/jenkins-pipelines-demo')
                }
            }
            scriptPath('.jenkins/pipelines/web.groovy')
        }
    }
}
