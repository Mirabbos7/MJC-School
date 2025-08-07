pipeline {
    agent any

    tools {
        gradle 'gradle-8.14.3'
        jdk 'corretto-17'
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
            post {
                success {
                    archiveArtifacts artifacts: '**/build/libs/*.war'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('MySonar') {
                    withCredentials([string(credentialsId: 'sonar-token2', variable: 'SONAR_TOKEN')]) {
                        bat """
                        "${tool 'SonarQube Scanner'}\\bin\\sonar-scanner.bat" ^
                        -Dsonar.projectKey=MJC-School ^
                        -Dsonar.token=%SONAR_TOKEN% ^
                        -Dproject.settings=sonar-project.properties
                        """
                    }
                }
            }
        }

        stage('Test & Coverage') {
            steps {
                bat 'gradle test jacocoTestReport'
            }
        }

        stage('Quality Gate') {
            steps {
                sleep(time: 10, unit: 'SECONDS')
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                deploy adapters: [
                    tomcat9(
                        alternativeDeploymentContext: '',
                        credentialsId: 'tomcat-cred',
                        path: '',
                        url: 'http://localhost:8081/'
                    )
                ], contextPath: null, war: '**/*.war'
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
