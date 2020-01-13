node {
   stage('init') {
      checkout scm
      echo 'init'
   }
   stage('build') {
      bat 'mvn clean package'
      
   }
   stage('deploy') {
      bat 'mvn azure-webapp:deploy'
   }
}
