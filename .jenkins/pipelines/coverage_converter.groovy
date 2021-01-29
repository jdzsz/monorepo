#!/usr/bin/env groovy

COMPONENT_PATH = "metrics/coverage_converter"
FIREBASE_PROJECT_ID = "metrics-d9c67"
METRICS_PROJECT_ID = "jenkins_coverage_converter"

node('master'){
  timeout(time: 3, unit: 'MINUTES') {
    checkout scm
  }
  loadSharedLib(".jenkins/shared-library")
}

podTemplate(yaml: """
kind: Pod
spec:
  containers:
  - name: flutter
    image: gcr.io/jdzsz/monorepo/flutter-chromedriver:1
    imagePullPolicy: IfNotPresent
    command:
    - cat
    tty: true
"""
  ) {

  node(POD_LABEL) {
    container('flutter') {
      stage('Init') {
        timeout(time: 3, unit: 'MINUTES') {
          checkout scm
        }
      }
      commonDartBuild(COMPONENT_PATH, FIREBASE_PROJECT_ID, METRICS_PROJECT_ID, "${JOB_NAME}")
    }
  }
}

def loadSharedLib(String libraryPath){
  try{
    sh("""git config --global user.email "jenkins@localhost" && \
          git config --global user.name "Jenkins" && \
          cd ./$libraryPath && \
          (rm -rf .git || true) && \
          git init && \
          git add --all && \
          git commit -m init
          """)

    def repoPath = sh(returnStdout: true, script: 'pwd').trim() + "/$libraryPath"

    library identifier: 'local-lib@master',
          retriever: modernSCM([$class: 'GitSCMSource', remote: "$repoPath"]),
          changelog: false

    deleteDir()
    echo "Shared library correctly loaded"

  } catch (Exception e){
      currentBuild.result='ABORTED'
      error "Exception caught during shared library loading. Aborting build.\n" + e.toString()
      return
    }
}