pipeline {
    agent any
    stages {
        if (env.BRANCH_NAME == 'develop') {
            stage('Compile') {
                steps {
                    echo 'Compile'
                }
            }
        } else {
            stage('Build') {
                steps {
                    echo 'Build'
                }
            }
        }
    }
}
