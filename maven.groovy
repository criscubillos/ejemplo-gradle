/*
    forma de invocación de método call:
    def ejecucion = load 'script.groovy'
    ejecucion.call()
*/
def call() {
    stage('Compilar') {
        git( branch: 'main', url: 'https://github.com/criscubillos/ejemplo-maven.git')
        sh './mvnw clean compile -e'
    }
    stage('Test') {
        sh './mvnw test -e'
    }
    stage('Empaquetar') {
        sh './mvnw package -e -DskipTests=true'
    }
    stage('Ejecutar') {
        sh './mvnw spring-boot:run &'
    //sh 'sleep 10'
    //sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing' "
    }
    stage('Sonar') {
        withSonarQubeEnv(installationName: 'SonarQube Docker') {
            sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
        }
    }
}
return this
