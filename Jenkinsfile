node {
   stage('init') {
      checkout scm
      echo 'init'
   }
   stage('build') {
      bat 'mvn clean package'
      echo 'copy'
      bat label: '', script: '''
        cd target
        copy ..\\src\\main\\resources\\web.config web.config
        copy invt-0.0.1-SNAPSHOT.jar app.jar
        7z a todo.zip app.jar web.config
        '''
      bat 'mvn azure-webapp:deploy'
   }
   stage('deploy') {
      azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID,
      resourceGroup: env.RES_GROUP, appName: env.WEB_APP, filePath: "**/todo.zip"
   }
}
