pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }

    environment {
        DOCKER_IMAGE = 'employee-management'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Show Tool Versions') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'docker --version'
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn -B -DskipTests clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml',
                          allowEmptyResults: true
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn -B -DskipTests package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t %DOCKER_IMAGE%:%BUILD_NUMBER% .'
                bat 'docker tag  %DOCKER_IMAGE%:%BUILD_NUMBER% %DOCKER_IMAGE%:latest'
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Build #${BUILD_NUMBER} finished with status: ${currentBuild.currentResult}"
        }
        success {
            echo "Pipeline completed successfully. Image: ${DOCKER_IMAGE}:${BUILD_NUMBER}"
        }
        failure {
            echo "Pipeline failed. Check the stage logs."
        }
    }
}
