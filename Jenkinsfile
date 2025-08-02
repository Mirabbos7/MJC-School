pipeline {
    agent any

    tools {
        gradle 'gradle-8.14.3'
        jdk 'corretto-17'
    }

    environment {
        SONARQUBE_SCANNER_HOME = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
            }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Mirabbos7/MJC-School', branch: 'main', credentialsId: 'github-cred'
            }
        }

        stage('Build') {
            steps {
                bat 'gradle clean build'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server'){
                bat """
                 "%SONAR_SCANNER_HOME%\\bin\\sonar-scanner.bat" ^
                -Dsonar.projectKey=MJC-School ^
                -Dsonar.sources=. ^
                -Dsonar.java.binaries=module-main/build/classes/java/main
                                    """
                                    }
            }
        }

        stage('Test & Coverage') {
            steps {
                bat 'gradle test jacocoTestReport'
            }
        }

        stage('Quality Gate'){
            steps{
            timeout(time:2, unit: 'MINUTES'){
                waitForQualityGate abortPipeline: true
                }
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
