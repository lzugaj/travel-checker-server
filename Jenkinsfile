pipeline {
    agent any

    stages {
        stage('Compile') {
            when { branch 'develop' }
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Compile') {
            when { not { branch 'develop' } }
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
    }
}
