#!/usr/bin/env groovy

pipelineJob('dummy') {
    displayName('Dummy')

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
                    branches('*/jenkins-k8s-dev')
                }
            }
            scriptPath('.jenkins/pipelines/dummy.groovy')
        }
    }
}
