TOOLS = [
    jdk: 'JDK 11',
    maven: 'Maven 3.6.3'
]

env.SKIP_TLS = true
def branch = env.BRANCH_NAME

@Library('jenkins-shared-library')_

if (branch.matches('feature/.+|bugfix/.+|hotfix/.+')) {
    node('git&&linux&&!master&&maven') {
        stage('Build') {
            sh 'mvn clean install -DskipTests'
        }
    }
} else if (branch == 'develop') {
    node('git&&linux&&!master&&maven') {
        stage('Compile') {
            sh 'mvn clean compile'
        }
    }
}
