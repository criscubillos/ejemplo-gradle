pipeline {
    agent any

    stages {
        stage('Pipeline') {
            steps {
                script{
                    stage('Build & Test'){
                        sh "./gradlew clean build" 
                    }
                    stage('Sonar'){
                        //corresponde a lo que se configuro en global tools config en jenkins local
                        def scannerHome = tool 'sonar-scanner-4.5';
                        //corresponse a lo que se configuro en Configuracion Global
                        withSonarQubeEnv('SonarQube Docker') { 
                            sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
                        }
                    }
                    stage('Run'){

                    }
                    stage('Rest'){

                    }
                    stage('Nexus'){

                    }                    
                }
            }
        }
    }
}
