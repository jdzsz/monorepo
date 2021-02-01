#!/usr/bin/env groovy

pipelineJob('test_sync_git') {
    displayName('Sync - git')
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
            scriptPath('.jenkins/pipelines/sync.groovy')
        }
    }
}
