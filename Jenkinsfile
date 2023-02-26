node{
    stage('checkout'){
        checkout scm
    }
    stage('build & push'){
        sh './build-image.sh'
    }
}