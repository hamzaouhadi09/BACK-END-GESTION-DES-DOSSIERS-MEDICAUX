pipeline {
    agent any  // Utilise n'importe quel agent Jenkins

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'  // Ajuste selon ton environnement
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/zahamhadiouh09/MedicalRecord-Back.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'  // Compile le projet
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test'  // Exécute les tests unitaires
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t medical-records-app .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-credentials', url: '']) {
                    sh 'docker tag medical-records-app username/medical-records-app:latest'
                    sh 'docker push username/medical-records-app:latest'
                }
            }
        }

        stage('Deploy to Server') {
            steps {
                sshagent(['server-ssh-key']) {
                    sh 'ssh user@server "docker pull username/medical-records-app:latest && docker run -d -p 8080:8080 username/medical-records-app"'
                }
            }
        }
    }

    post {
        success {
            echo '🚀 Déploiement réussi !'
        }
        failure {
            echo '❌ Échec du pipeline.'
        }
    }
}
