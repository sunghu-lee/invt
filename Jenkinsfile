node {
   stage('init') {
      checkout scm
      echo 'init'
   }
   stage('build') {
      bat 'mvn clean package'
      bat 'cd target'
      bat 'echo test'
   }
   stage('deploy') {
      azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID,
      resourceGroup: env.RES_GROUP, appName: env.WEB_APP, filePath: "**/todo.zip"
   }
}
