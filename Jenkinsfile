pipeline {
    agent any
    tools {
        maven 'Maven 3.8.2'
        jdk 'jdk11'
    }

    stages {
        stage('Compile') {
            bat 'mvn clean compile'
        }

        stage('Build') {
            bat 'mvn clean install -DskipTests'
        }

        stage('Test') {
            bat 'mvn test'
        }
    }
}