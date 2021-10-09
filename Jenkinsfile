def branch = env.BRANCH_NAME

if (branch == 'develop') {
    node {
        stage('Compile') {
            echo 'Compile'
        }
    }
} else if (branch == 'master') {
    node {
        stage('Compile') {
            echo 'Compile'
        }
    }
} else {
    node {
        stage('Build') {
            echo 'Build'
        }
    }
}
