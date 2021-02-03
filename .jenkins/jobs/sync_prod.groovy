#!/usr/bin/env groovy

pipelineJob('test_prod_sync_git') {
    displayName('Prod Sync - git')
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
            scriptPath('.jenkins/pipelines/prod_sync.groovy')
        }
    }
}
