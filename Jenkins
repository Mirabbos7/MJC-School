pipeline {
    agent any

    tools {
        maven '3.9.11'
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
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('LocalSonar') {
                    sh 'mvn sonar:sonar'
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

        stage('Deploy to Tomcat') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat-cred', path: '', url: 'http://localhost:8080')], contextPath: 'yourapp', war: 'target/yourapp.war'
            }
        }
    }
}
