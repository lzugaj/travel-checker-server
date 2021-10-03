node {
    agent any
    tools {
        maven 'Maven',
        jdk 'JDK 11'
    }

    env.SKIP_TLS = true
    def branch = env.BRANCH_NAME

    if (branch.matches('feature/.+|bugfix/.+|hotfix/.+') {
        stages {
            stage('Build') {
                steps {
                    bat 'mvn clean install -DskipTests'
                }
            }
        }
    } else if (branch == 'develop') {
        stages {
            stage('Compile') {
                steps {
                    bat 'mvn clean compile'
                }
            }
        }
    }
}