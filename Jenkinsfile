pipeline {
    agent any
    environment {
        MAVEN_HOME = tool name: 'Maven', type: 'Maven'
    }

    stages {
        stage('Checkout') {
            steps {
                dir('GestionBibliothequePipeline') {
                 //git branch: 'main', url: 'https://github.com/BarkouchSana/GestionBibliotheque.git'
            // Clone le dépôt Git
                            checkout scm
            }
            }
        }
        stage('Build') {
            steps {
                  dir('GestionBibliothequePipeline') {
                // Utilisation de la commande Maven pour Windows
               
               bat "\"${MAVEN_HOME}\\bin\\mvn\" clean compile"

            }
            }
        }
        stage('Test') {
            steps {
              dir('GestionBibliothequePipeline') {
            bat "\"${MAVEN_HOME}\\bin\\mvn\" test"
            }
            }
        }
        stage('Quality Analysis') {
            steps {
                dir('GestionBibliothequePipeline') {
                withSonarQubeEnv('SonarQube') { // Assurez-vous que "SonarQube" est correctement configuré dans Jenkins
                    bat "\"${MAVEN_HOME}\\bin\\mvn\" sonar:sonar"
                }
            }
            }
        }
        stage('Deploy') {
            steps {
                  dir('GestionBibliothequePipeline') {
                echo 'Déploiement simulé réussi'
            }
            }
        }
    }
    post {
         always {
            echo 'Nettoyage après l\'exécution du pipeline.'
        }
        success {
            echo 'Le pipeline a été exécuté avec succès !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
       // success {
       //  echo 'Le build a réussi, envoi de l\'email...'
        //    emailext(
          //                  to: 'sanaabarkouch2001@gmail.com',
          //                  subject: 'Build Success',
             //               body: 'Le pipeline a été complété avec succès.'
             //           )
            // }
         //failure {
              //      echo 'Le build a échoué, envoi de l\'email...'
              //      emailext(
               //         to: 'sanaabarkouch2001@gmail.com',
                //        subject: 'Build Failed',
                 //       body: 'Le pipeline a échoué. Veuillez vérifier les logs Jenkins pour plus de détails.'
                  //  )
              //  }
    }
}
