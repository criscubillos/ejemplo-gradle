pipeline {
    agent any

    parameters {
        choice(name:'herramienta', choices:['gradle', 'maven'])
    }

    stages {
        stage('Pipeline') {
            steps {
                script {

                    env.TAREA = 'SIN ESPECIFICAR'
                    

                    println params.herramienta


                    if (params.herramienta == 'gradle') {
                        def ejecucion = load 'gradle.groovy'
                        ejecucion.call()
                    }else {
                        println 'Ejecutando MAVEN GROOVY'
                        def ejecucion = load 'maven.groovy'
                        ejecucion.call()
                    }
                }
            }
        }
    }
    post {
        success {
            println "TERMINO Y LA ULTIMA TAREA FUE: ${env.TAREA}" 
            slackSend (message: "[Cristian Cubillos][${env.JOB_NAME}][${params.herramienta}] Ejecución exitosa")
        }
        failure {
            println "FALLO Y LA ULTIMA TAREA FUE: ${env.TAREA}" 
            slackSend (message: "[Cristian Cubillos][${env.JOB_NAME}][${params.herramienta}] Ejecución fallida en stage [${env.TAREA}]")
        }
    }
}
