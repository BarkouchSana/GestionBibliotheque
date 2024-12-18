pipeline {
    agent any
        tools {
        git 'Default Git'
        maven 'Maven 3.8.1' // Remplacez par la version de Maven installée sur Jenkins
        jdk 'JDK 17'        // Remplacez par la version de JDK installée sur Jenkins
    }
    environment {
         MAVEN_OPTS = '-Dmaven.test.failure.ignore=true' // Permet d'ignorer les échecs de tests
    }

    stages {
        stage('Checkout') {
            steps {
                //echo 'Clonage du dépôt...'
                 //git branch: 'main', url: 'https://github.com/BarkouchSana/GestionBibliotheque.git'
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
             echo 'Analyse de la qualité du code avec SonarQube...'
                withSonarQubeEnv('SonarQube') { // Remplacez 'SonarQube' par votre configuration
                    bat 'mvn sonar:sonar'
                }
            }
        }
        stage('Deploy') {
            steps {
                 echo 'Déploiement de l\'application...'
                bat 'mvn install'
            }
        }
    }
    post {
         always {
            echo 'Pipeline terminé.'
            junit '**/target/surefire-reports/*.xml' // Publie les résultats de tests
        }
        success {
            emailext to: 'sanabarkouch@gmail.com',
                subject: 'Build Success',
                body: 'Le build a été complété avec succès.'
        }
        failure {
            emailext to: 'sanabarkouch@gmail.com',
                subject: 'Build Failed',
                body: 'Le build a échoué.'
        }

       
    }
    
}
