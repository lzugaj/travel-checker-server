TOOLS = [
    maven: 'Maven 3.8.2'
]

env.SKIP_TLS = true
def branch = env.BRANCH_NAME

if (branch == 'develop') {
    node {
        stage('Compile') {
            mvn 'compile'
        }
    }
} else if (branch == 'master') {
    node {
        stage('Compile') {
            sh 'mvn compile'
        }
    }
} else {
    node {
        stage('Build') {
            sh 'mvn clean install -DskipTests'
        }
    }
}