node{
    withMaven(maven:'maven') {
        stage('clean workspace'){
            cleanWs()
        }
        stage('Checkout') {
            checkout scm
        }
        stage('build') {
            sh 'mvn clean install'
        }
        stage('docker login') {
            withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                sh 'docker login -u peterado -p ${dockerhubpwd}'
            }
        }
        stage('create images docker') {
            sh 'docker build entite-service -t peterado/tresosoft:entite-service'
            sh 'docker build compte-service -t peterado/tresosoft:compte-service'
            sh 'docker build documentcommercial-service -t peterado/tresosoft:documentcommercial-service'
            sh 'docker build fileupload-service -t peterado/tresosoft:fileupload-service'
            sh 'docker build notification-service -t peterado/tresosoft:notification-service'
            sh 'docker build transaction-service -t peterado/tresosoft:transaction-service'
            sh 'docker build utilisateur-service -t peterado/tresosoft:utilisateur-service'
        }
        stage('docker push') {

        }
    }
//     stage('checkout'){
//         checkout scm
//     }
//     stage('clean & install'){
//         sh 'mvn clean install -f compte-service'
//         sh 'mvn clean install -f documentcommercial-service'
//         sh 'mvn clean install -f entite-service'
//         sh 'mvn clean install -f fileupload-service'
//         sh 'mvn clean install -f notification-service'
//         sh 'mvn clean install -f transaction-service'
//         sh 'mvn clean install -f utilisateur-service'
//     }
//     stage('stop container'){
//             sh 'docker stop compte-service'
//             sh 'docker stop documentcommercial-service'
//             sh 'docker stop entite-service'
//             sh 'docker stop fileupload-service'
//             sh 'docker stop notification-service'
//             sh 'docker stop transaction-service'
//             sh 'docker stop utilisateur-service'
//     }
//         stage('remove container'){
//                 sh 'docker rm compte-service'
//                 sh 'docker rm documentcommercial-service'
//                 sh 'docker rm entite-service'
//                 sh 'docker rm fileupload-service'
//                 sh 'docker rm notification-service'
//                 sh 'docker rm transaction-service'
//                 sh 'docker rm utilisateur-service'
//         }
//                 stage('delete image'){
//                         sh 'docker rmi compte-service'
//                         sh 'docker rmi documentcommercial-service'
//                         sh 'docker rmi entite-service'
//                         sh 'docker rmi fileupload-service'
//                         sh 'docker rmi notification-service'
//                         sh 'docker rmi transaction-service'
//                         sh 'docker rmi utilisateur-service'
//                 }
//        stage('build image'){
//             sh 'docker build compte-service -t peterado/tresosoft:compte-service'
//             sh 'docker build documentcommercial-service -t peterado/tresosoft:documentcommercial-service'
//             sh 'docker build entite-service -t peterado/tresosoft:entite-service'
//             sh 'docker build fileupload-service -t peterado/tresosoft:fileupload-service'
//             sh 'docker build notification-service -t peterado/tresosoft:notification-service'
//             sh 'docker build transaction-service -t peterado/tresosoft:transaction-service'
//             sh 'docker build utilisateur-service -t peterado/tresosoft:utilisateur-service'
//         }
}