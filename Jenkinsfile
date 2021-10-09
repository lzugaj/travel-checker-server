pipeline {
    agent any

    stages {
        script {
            if (env.BRANCH_NAME == 'develop') {
                stage('Compile') {
                    steps {
                        sh 'mvn clean compile'
                    }
                }
            } else {
                stage('Build') {
                    steps {
                        sh 'mvn clean install -DskipTests'
                    }
                }
            }
        }
    }
}
