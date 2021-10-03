pipeline {
    agent any
    tools {
        maven 'Maven 3.8.2'
        jdk 'jdk11'
    }

    stages {
        stage('Compile') {
            sh 'mvn clean compile'
        }

        stage('Build') {
            sh 'mvn clean install -DskipTests'
        }

        stage('Test') {
            sh 'mvn test'
        }
    }
}