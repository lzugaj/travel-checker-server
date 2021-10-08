pipeline {
    agent any

    def branch = env.BRANCH_NAME

    if (branch.matches('feature/.+|bugfix/.+|hotfix/.+')) {
        stages {
            stage('Build') {
                steps {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
    } else if (branch == 'develop') {
        stages {
            stage('Compile') {
                steps {
                    sh 'mvn clean compile'
                }
            }
        }
    }
}