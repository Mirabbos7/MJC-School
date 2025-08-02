
pipeline {
    agent any

    tools {
        gradle 'gradle-8.14.3'
    }

    environment {
    PATH = "${env.PATH};C:\\Windows\\System32"
        SONARQUBE_SCANNER_HOME = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Mirabbos7/MJC-School', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                bat 'gradle clean build'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                bat 'SonarQube'
            }
        }

        stage('Test') {
            steps {
                bat 'gradle test jacocoTestReport'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Pipeline succeeded.'
        }
    }
}