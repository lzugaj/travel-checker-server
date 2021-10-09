pipeline {
    agent any
    stages {
        stage('Compile') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Compile'
            }
        }

        stage('Build') {
            steps {
                echo 'Build'
            }
        }
    }
}
