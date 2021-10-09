if (env.BRANCH_NAME == 'develop') {
    node {
        stage('Compile') {
            steps {
                echo 'Compile'
            }
        }
    }
} else {
    node {
        stage('Build') {
            steps {
                echo 'Build'
            }
        }
    }
}
