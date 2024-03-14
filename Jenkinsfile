pipeline {
  agent any
  tools {
    maven 'maven-3.9.6'
  }
  stages {
    stage ('Install') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage ('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage ('Deploy') {
      steps {
        script {
          deploy adapters: [tomcat9(credentialsId: 'admin', path: '', url: 'http://localhost:9090/')], contextPath: '/pipeline', onFailure: false, war: 'target/*.war'
        }
      }
    }
  }
}
