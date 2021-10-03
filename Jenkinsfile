pipeline {
    agent any
    tools {
        maven 'Maven',
        jdk 'JDK 11'
    }

    triggers {
        cron('H/1 * * * *')
    }

    env.SKIP_TLS = true
    def branch = env.BRANCH_NAME

    stages {
        if (branch.matches('feature/.+|bugfix/.+|hotfix/.+')) {
            stage('Build') {
                steps {
                    bat 'mvn clean install -DskipTests'
                }
            }
        } else if (branch == 'develop') {
              stage('Compile') {
                  steps {
                      bat 'mvn clean compile'
                  }
              }
        }
    }
}