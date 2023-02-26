node{
    stage('checkout'){
        checkout scm
    }
    stage('Run Script'){
            sh 'chmod +x build-image-linux.sh && ./build-image-linux.sh'
    }
}