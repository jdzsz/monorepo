#!/usr/bin/env groovy

podTemplate(yaml: """
kind: Pod
spec:
  containers:
  - name: sync
    image: gcr.io/jdzsz/monorepo/sync-builddata:1
    imagePullPolicy: IfNotPresent
    command:
    - cat
    tty: true
"""
  ) {

  node(POD_LABEL) {
    properties(
  [
      parameters(
          [string(name: 'FIREBASE_PROJECT_ID'),
           string(name: 'METRICS_PROJECT_ID')]
          )

  ]
  )
    container('sync') {
      stage("Run CI Integrations"){
        println(params.FIREBASE_PROJECT_ID)
        checkout scm
        dir(".metrics/jenkins") {
          generateConfig('config_template.yml', "config.yml", params.FIREBASE_PROJECT_ID, params.METRICS_PROJECT_ID)
        }
    }
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