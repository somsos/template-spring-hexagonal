pipeline {

  agent any

  stages {
      
      stage('1. build') {
        steps {
            echo '######### build starts ######### ';
            sh '''
              bash ./build.sh
            '''
            echo '######### build ends ######### ';
        }
      }


      stage('2. deploy') {
        steps {
            echo '######### deploy starts ######### ';
            sh '''
              bash ./run.sh
            '''
            echo '######### deploy ends ######### ';
        }
      }

  }

}