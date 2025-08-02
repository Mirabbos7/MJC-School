pipeline {
    agent any

    tools {
        gradle 'gradle-8.14.3'
        jdk 'corretto-17'
    }

    environment {
        // Добавляем сканер SonarQube из Jenkins -> Global Tool Configuration
        SONARQUBE_SCANNER_HOME = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        PATH = "${env.PATH};${SONAR_SCANNER_HOME}\\bin"
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {
          stage('Load .env') {
               steps {
                   script {
                       def props = readProperties file: '.env'
                       env.SONAR_TOKEN = props['SONAR_TOKEN']
                   }
                }
          }

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
                bat "\"%SONARQUBE_SCANNER_HOME%\\bin\\sonar-scanner.bat\" " +
                    "-Dsonar.projectKey=MJC-School " +
                    "-Dsonar.sources=. " +
                    "-Dsonar.java.binaries=module-main/build/classes/java/main " +
                    "-Dsonar.host.url=http://localhost:9000 " +
                    "-Dsonar.login=sqa_7168aadb806dc0068bc90624df5c4ee91b6bee36"
            }
        }

        stage('Test & Coverage') {
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
