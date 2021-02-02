#!/usr/bin/env groovy

pipelineJob('test_monorepo_git') {
    displayName('Monorepo - git')

    properties {
        pipelineTriggers {
            triggers {
                githubPush()
            }
        }
    }

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
            scriptPath('.jenkins/pipelines/monorepo.groovy')
        }
    }
}
