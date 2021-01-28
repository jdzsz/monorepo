#!/usr/bin/env groovy


def call(String firebaseProjectId = "dummy", String metricsProjectId = "dummy") {
  stage("Run CI Integrations"){
    dir(".metrics/jenkins") {
      generateConfig('config_template.yml', "config.yml", firebaseProjectId, metricsProjectId)
    }
  }
}

def generateConfig(String templateRelPath, String configRelPath, String firebaseProjectId, String metricsProjectId){
withEnv(["FIREBASE_PROJECT_ID=${firebaseProjectId}", "METRICS_PROJECT_ID=${metricsProjectId}"]) {
    withCredentials([usernamePassword(credentialsId: 'jenkins-app-credentials', passwordVariable: 'WEB_APP_USER_PASSWORD', usernameVariable: 'WEB_APP_USER_EMAIL')]) {
        withCredentials([string(credentialsId: 'jenkins-firebase-api-key', variable: 'CI_INTEGRATIONS_FIREBASE_API_KEY')]) {
            withCredentials([usernamePassword(credentialsId: 'jenkins-api-credentials', passwordVariable: 'JENKINS_API_KEY', usernameVariable: 'JENKINS_USERNAME')]) {
              sh "envsubst < ${templateRelPath} > ${configRelPath}"
            }
        }
    }
  }
  sh 'env'
  sh """echo `cat config.yml`"""
  sh "ci_integrations -v sync --config-file config.yml"
}

