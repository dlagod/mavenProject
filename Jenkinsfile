pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        echo 'Descargando el proyecto'
        git(url: 'https://github.com/dlagod/mavenProject.git', branch: 'master', credentialsId: 'Github')
      }
    }

    stage('Build') {
      steps {
        echo 'Compilando'
        bat(script: 'mvn clean compile', label: 'compilando')
      }
    }

  }
}