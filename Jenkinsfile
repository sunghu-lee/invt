node {
   stage('init') {
      checkout scm
      echo 'init'
   }
   stage('build') {
      bat '''
         echo 'build start'
         mvn clean package
         cd target
         echo 'move dir target'
         cp ../src/main/resources/web.config web.config
         echo 'copy config'
         cp invt-0.0.1-SNAPSHOT.jar app.jar 
         echo 'copy jar'
         zip todo.zip app.jar web.config
         echo 'Process zip'
      '''
   }
   stage('deploy') {
      azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID,
      resourceGroup: env.RES_GROUP, appName: env.WEB_APP, filePath: "**/todo.zip"
   }
}
