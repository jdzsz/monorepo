#!/usr/bin/env groovy

pipelineJob('test_coverage_converter_git') {
    displayName('Coverage converter - git')
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
            scriptPath('.jenkins/pipelines/coverage_converter.groovy')
        }
    }
}
