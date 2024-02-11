pipeline {
    agent any
    
    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: "master", credentialsId: "conf-git", url: "https://github.com/conf312/issueMoa-mail.git"
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    sh "chmod 777 gradlew && ./gradlew bootJar"
                }
            }
        }
        stage('Dokcer Image Build') {
            steps {
                script {
                    sh "docker build -t issuemoa/mail ."
                }
            }
        }
        stage('Dokcer Run Conatiner') {
            steps {
                script {
                    def containerRunning = sh(script: 'docker inspect -f \'{{.State.Running}}\' issuemoa-mail 2>/dev/null', returnStatus: true) == 0

                    if (containerRunning) {
                        echo 'Container is already running'
                        sh "docker stop issuemoa-mail"
                        sh "docker rm issuemoa-mail"
                    }
                    
                    sh "docker run -d --name issuemoa-mail -p 17060:17060 -v /home/venh/logs:/var/log --network issuemoa -e TZ=Asia/Seoul issuemoa/mail"
                }
            }
        }
    }

    post {
        success {
            script {
                // 성공적인 빌드일 때 실행
                def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()

                slackSend (
                    channel: '#이슈모아', 
                    color: '#0100FF', 
                    message: "😄 이슈모아 Build & Release 성공! \n 💻 ${commitMessage} \n ${env.JOB_NAME},  buildNumber: [${env.BUILD_NUMBER}]"
                )
            }
        }
        
        failure {
            script {
                // 빌드 실패 시 실행
                def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()

                slackSend (
                    channel: '#이슈모아', 
                    color: '#FF0000', 
                    message: "😱 이슈모아 Build & Release 실패! \n 💻 ${commitMessage} \n ${env.JOB_NAME},  buildNumber: [${env.BUILD_NUMBER}]"
                )
            }
        }
    }
}
