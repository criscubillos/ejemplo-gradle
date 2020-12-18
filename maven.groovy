/*
    forma de invocación de método call:
    def ejecucion = load 'script.groovy'
    ejecucion.call()
*/
def call() {
    stage('Compilar') {
        env.TAREA = env.STAGE_NAME
        git( branch: 'main', url: 'https://github.com/criscubillos/ejemplo-maven.git')
        sh './mvnw clean compile -e'
    }
    stage('Test') {
        env.TAREA = env.STAGE_NAME
        sh './mvnw test -e'
    }
    stage('Empaquetar') {
        env.TAREA = env.STAGE_NAME
        sh './mvnw package -e -DskipTests=true'
    }
    stage('Ejecutar') {
        env.TAREA = env.STAGE_NAME
        sh './mvnw spring-boot:run &'
    //sh 'sleep 10'
    //sh "curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing' "
    }
    stage('Sonar') {
        env.TAREA = env.STAGE_NAME
        withSonarQubeEnv(installationName: 'SonarQube Docker') {
            sh './mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
        }
    }
}
return this
