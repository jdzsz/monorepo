#!/usr/bin/env groovy

pipelineJob('sync_build_data') {
    displayName('Sync build data')
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/jdzsz/monorepo.git')
                    }
                    branches('*/jenkins-k8s-dev')
                }
            }
            scriptPath('.jenkins/pipelines/sync.groovy')
        }
    }
}
