pipeline {
    agent any
        tools {
        git 'Default Git'
        maven 'Maven 3.8.1' // Remplacez par la version de Maven installée sur Jenkins
        jdk 'JDK 17'        // Remplacez par la version de JDK installée sur Jenkins
    }
 

    stages {
        stage('Checkout') {
            steps {

            // Clone le dépôt Git
                echo 'Clonage du projet...'
                            checkout scm
            }
        }
        stage('Build') {
            steps {
                 echo 'Compilation du projet avec Maven...'
                bat 'mvn clean compile'

            }
            
        }
        stage('Test') {
            steps {
             echo 'Exécution des tests unitaires...'
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging du projet en fichier JAR...'
                bat 'mvn package'
            }
        }
        stage('Quality Analysis') {
            steps {
             echo 'Analyse de la qualite du code avec SonarQube...'
                withSonarQubeEnv('SonarQube') { // Remplacez 'SonarQube' par votre configuration
                    bat 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
             echo 'Déploiement simulé réussi'

            }
        }
    }
    post {
         always {
            echo 'Pipeline terminé.'
            junit '**/target/surefire-reports/*.xml' // Publie les résultats de tests
        }
        success {
            emailext to: 'sanaabarkouch2001@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            emailext to: 'sanaabarkouch2001@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }

       
    }
    
}
