pipeline {
    agent any
    tools {
        maven 'apache-maven-3.8.2'
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