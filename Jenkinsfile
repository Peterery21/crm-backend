node{
    stage('checkout'){
        checkout scm
    }
    stage('Run Script'){
        sh 'chmod +x build-image.sh && ./build-image.sh'
    }
    stage('build & push'){
        dir ('${env.WORKSPACE}') {
            sh "chmod +x -R ${env.WORKSPACE}/../${env.JOB_NAME}@script"
            sh './build-image.sh'
        }
    }
}