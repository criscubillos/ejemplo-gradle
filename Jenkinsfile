pipeline {
    agent any

    parameters {
        choice(name:'herramienta', choices:['gradle', 'maven'])
    }

    stages {
        stage('Pipeline') {
            steps {
                script {
                    println params.herramienta

                    if (params.herramienta == 'gradle') {
                        def ejecucion = load 'gradle.groovy'
                        ejecucion.call()
                    }else {
                        println "Ejecutando MAVEN GROOVY"
                        def ejecucion = load 'maven.groovy'
                        ejecucion.call()
                    }
                }
            }
        }
    }
}
