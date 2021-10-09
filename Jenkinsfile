TOOLS = [
    jdk: 'JDK 8',
    maven: [
        id: 'Maven 3.8.2',
        globalSettings: '1a20d0f5-59a6-4b51-96a0-13bd5c065779',
        settings: '5d77dfcc-5b32-4d9f-841a-90f768d94a58',
        mavenOpts: '-Xmx768m -XX:MaxPermSize=256m'
    ]
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
        maven: TOOLS.maven.id,
        jdk: TOOLS.jdk,
        globalMavenSettingsConfig: TOOLS.maven.globalSettings,
        mavenSettingsConfig: TOOLS.maven.settings,
        mavenOpts: TOOLS.maven.mavenOpts
    ) {
        sh 'mvn ${cmd}'
    }
}