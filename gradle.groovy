/*
    forma de invocación de método call:
    def ejecucion = load 'script.groovy'
    ejecucion.call()
*/ 

def call() {
  stage('Build & Test') {
    sh './gradlew clean build'
  }
  stage('Sonar') {
    //corresponde a lo que se configuro en global tools config en jenkins local
    def scannerHome = tool 'sonar-scanner-4.5'
    //corresponse a lo que se configuro en Configuracion Global
    withSonarQubeEnv('SonarQube Docker') {
      sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=ejemplo-gradle -Dsonar.java.binaries=build"
    }
  }
  stage('Run') {
    sh 'nohup bash gradlew bootRun &'
    //sleep 20
  }
  stage('Rest') {    
    //sh "curl -X GET 'http://192.168.100.3:8090/rest/mscovid/test?msg=testing' "
  }
  stage('Nexus') {
    nexusPublisher nexusInstanceId: 'NEXUS_DOCKER', nexusRepositoryId: 'diplomado', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: 'jar', filePath: 'build/libs/DevOpsUsach2020-0.0.1.jar']], mavenCoordinate: [artifactId: 'DevOpsUsach2020', groupId: 'com.devopsusach2020', packaging: 'jar', version: '1.0.1']]]
  }
}

return this
