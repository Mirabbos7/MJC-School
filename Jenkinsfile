pipeline {
    agent any

    tools {
        gradle 'gradle-8.14.3'
        jdk 'corretto-17'
    }

    environment {
        SONARQUBE = 'LocalSonar'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-cred', url: 'https://github.com/Mirabbos7/MJC-School'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'gradle clean build'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('LocalSonar') {
                    bat 'gradle sonarqube'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
