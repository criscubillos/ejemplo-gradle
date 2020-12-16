/*
    forma de invocación de método call:
    def ejecucion = load 'script.groovy'
    ejecucion.call()
*/
def call() {
    stage('Compilar') {
        git( branch: 'main', url: 'https://github.com/criscubillos/ejemplo-maven.git')
        sh 'mvn clean compile -e'
    }
    stage('Test') {
        sh 'mvn test -e'
    }
    stage('Empaquetar') {
        sh 'mvn package -e -DskipTests=true'
    }
    stage('Ejecutar') {
        sh 'mvn spring-boot:run &'
    //sh 'sleep 10'
    //sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing' "
    }
    stage('Sonar') {
        withSonarQubeEnv(installationName: 'SonarQube Docker') {
            sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
        }
    }
}
return this
