#!/usr/bin/env groovy

pipelineJob('metrics_web') {
    displayName('Metrics Web')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('git@github.com:jdzsz/monorepo.git')
                        credentials('jenkins-key')
                    }
                    branches('*/feat-jenkins')
                }
            }
            scriptPath('.jenkins/pipelines/metrics_web.groovy')
        }
    }
}