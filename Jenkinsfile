TOOLS = [
    jdk: 'JDK 11',
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
            mvn 'compile'
        }
    }
} else {
    node {
        stage('Build') {
            mvn 'clean install -DskipTests'
        }
    }
}

def mvn(cmd) {
    withMaven(
        maven: TOOLS.maven,
        jdk: TOOLS.jdk
    ) {
        sh 'mvn ${cmd}'
    }
}