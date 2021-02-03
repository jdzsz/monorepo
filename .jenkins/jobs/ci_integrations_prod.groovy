#!/usr/bin/env groovy

pipelineJob('test_prod_ci_integrations_git') {
    displayName('Prod CI Integrations - git')
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
            scriptPath('.jenkins/pipelines/ci_integrations_prod.groovy')
        }
    }
}
