pipeline {
    agent any
    tools {
        maven 'Maven',
        jdk 'JDK 11'
    }

    env.SKIP_TLS = true
    def branch = env.BRANCH_NAME

    stages {
        stage('Build') {
            when { branch.matches('feature/.+|bugfix/.+|hotfix/.+' }
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }
    }

    stages {
        stage('Compile') {
            when { branch 'develop' }
            steps {
                bat 'mvn clean compile'
            }
        }
    }
}