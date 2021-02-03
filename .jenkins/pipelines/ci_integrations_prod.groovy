pipeline {
    agent {
        docker {
            image 'testmnrp/dart-converter:2'
            args '-u root:root'
        }
    }
    options {
        timeout(time: 2, unit: 'HOURS')
    }

    stages {

        stage('Git clone') {
            steps {
                git branch: 'master', url: 'https://github.com/platform-platform/monorepo.git'
            }
        }

        stage('Get Dependencies') {
            steps {
                dir('metrics/ci_integrations'){
                    sh 'pub get'
                }
            }
        }

        stage('Run tests') {
                    failFast true
                    parallel {
                        stage('Run Analyzer') {
                            steps {
                                dir('metrics/ci_integrations'){
                                    sh 'dartanalyzer . --fatal-infos --fatal-warnings'
                                }
                            }
                        }
                        stage('Test coverage') {
                            steps {
                                dir('metrics/ci_integrations'){
                                    sh 'pub run test_coverage --print-test-output --no-badge --port 9498'
                                }
                            }
                        }
                   }
               }

        stage('Convert coverage report') {
            steps {
                dir('metrics/ci_integrations'){
                    sh 'coverage_converter lcov -i coverage/lcov.info -o coverage-summary.json'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'metrics/ci_integrations/coverage-summary.json', fingerprint: true, allowEmptyArchive: true
            cleanWs cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, deleteDirs: true, notFailBuild: true
            build job: 'test_prod_sync_git', propagate: false, wait: false, parameters: [string(name: 'FIREBASE_PROJECT_ID', value: "${env.PROD_FIREBASE_PROJECT_ID}"), string(name: 'METRICS_PROJECT_ID', value: "${env.PROD_CI_INTEGRATIONS_METRICS_PROJECT_ID}"), string(name: 'ANCESTOR_JOB_NAME', value: "${env.JOB_NAME}")]
        }
    }
}
