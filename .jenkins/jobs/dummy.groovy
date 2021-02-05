#!/usr/bin/env groovy

pipelineJob('test_dummy_git') {
    displayName('Dummy - git')
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
            scriptPath('.jenkins/pipelines/dummy.groovy')
        }
    }
}
