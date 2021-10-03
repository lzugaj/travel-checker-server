pipeline {
    agent any
    tools {
        maven 'Maven 3.8.2'
        jdk 'jdk11'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
    }
}