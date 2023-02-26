node{
    stage('checkout'){
        checkout scm
    }
    stage('Run Script'){
        steps {
            script {
                sh('cd relativePathToFolder && chmod +x build-image.sh && ./build-image.sh')
            }
        }
    }
    stage('build & push'){
        dir ('<your new directory>') {
            sh "chmod +x -R ${env.WORKSPACE}/../${env.JOB_NAME}@script"
            sh './build-image.sh'
        }
    }
}