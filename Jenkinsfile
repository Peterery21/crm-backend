node{
    stage('checkout'){
        checkout scm
    }
    stage('Run Script'){
            sh 'chmod +x build-image.sh && ./build-image.sh'
    }
}