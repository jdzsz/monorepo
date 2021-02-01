#!/usr/bin/env groovy

pipelineJob('test_ci_integrations_git') {
    displayName('CI Integrations - git')
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
            scriptPath('.jenkins/pipelines/ci_integrations.groovy')
        }
    }
}
