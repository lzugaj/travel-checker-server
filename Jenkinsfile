TOOLS = [
    jdk: 'JDK 11',
    maven: [
        id: 'Maven 3.6.3',
        globalSettings: '1a20d0f5-59a6-4b51-96a0-13bd5c065779',
        settings: '5d77dfcc-5b32-4d9f-841a-90f768d94a58',
        mavenOpts: '-Xmx768m -XX:MaxPermSize=256m'
    ]
]

env.SKIP_TLS = true
def branch = env.BRANCH_NAME

if (branch.matches('feature/.+|bugfix/.+|hotfix/.+')) {
    node('git&&linux&&!master&&maven') {
        stage('Build') {
            mvn 'clean install -DskipTests'
        }
    }
} else if (branch == 'develop') {
    node('git&&linux&&!master&&maven') {
        stage('Compile') {
            mvn 'clean compile'
        }
    }
}

def mvn(cmd) {
    withMaven(
        maven: TOOLS.maven,
        jdk: TOOLS.jdk,
        globalMavenSettingsConfig: TOOLS.maven.globalSettings,
        mavenSettingsConfig: TOOLS.maven.settings,
        mavenOpts: TOOLS.maven.mavenOpts
    ) {
        sh "mvn ${cmd}"
    }
}
