pipeline {
    agent any
    if (env.BRANCH_NAME == 'develop') {
        stages {
            stage('Compile') {
                steps {
                    echo 'Compile'
                }
            }
        }
    } else {
        stages {
            stage('Build') {
                steps {
                    echo 'Build'
                }
            }
        }
    }
}
