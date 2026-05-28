pipeline {
    agent any

    tools {
        // The name 'Maven' must match exactly the name configured in:
        //   Manage Jenkins -> Tools -> Maven installations
        maven 'Maven'
    }

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
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn -B -DskipTests clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                // Run only unit tests for now. Integration tests need MySQL,
                // which we'll wire up in a later iteration.
                sh 'mvn -B test -Dtest=EmployeeServiceTest,EmployeeControllerTest'
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
                sh 'mvn -B -DskipTests package'
            }
        }

        // -----------------------------------------------------------------
        // Docker Build stage is temporarily disabled.
        //
        // To enable it, the Jenkins container needs:
        //   1) The Docker CLI installed inside it.
        //   2) The host Docker socket mounted:
        //        -v /var/run/docker.sock:/var/run/docker.sock
        //      (set when starting the Jenkins container)
        //
        // Once those are in place, uncomment this stage.
        // -----------------------------------------------------------------
        //
        // stage('Docker Build') {
        //     steps {
        //         sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER} ."
        //         sh "docker tag  ${DOCKER_IMAGE}:${BUILD_NUMBER} ${DOCKER_IMAGE}:latest"
        //     }
        // }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo "Build #${BUILD_NUMBER} finished with status: ${currentBuild.currentResult}"
        }
        success {
            echo 'Pipeline completed successfully. JAR archived.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
