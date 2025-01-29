pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh "JCasC env.hello: ${env.hello}"
      }
    }

  }
}
