/*
    forma de invocación de método call:
    def ejecucion = load 'script.groovy'
    ejecucion.call()
*/

def call() {
  agent any

  tools {
        maven 'MVN 3.6.3'
  }

  stages {
        stage('Compilar') {
      steps {
        git( branch: 'main', url: 'https://github.com/criscubillos/ejemplo-maven.git')
        sh 'mvn clean compile -e'
      }
        }
        stage('Test') {
      steps {
        sh 'mvn test -e'
      }
        }
        stage('Empaquetar') {
      steps {
        sh 'mvn package -e -DskipTests=true'
      }
        }
        stage('Ejecutar') {
      steps {
        sh 'mvn spring-boot:run &'
      //sh 'sleep 10'
      //sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing' "
      }
        }

        stage('Sonar') {
        steps {
            withSonarQubeEnv(installationName: 'SonarQube Docker') {
          sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
            }
        }
        }
  }
}

return this
