node {
   stage('init') {
      checkout scm
      echo 'init'
   }
   stage('build') {
      bat label: '', script: '''mvn clean package
         cd target
         copy ../src/main/resources/web.config web.config
         copy invt-0.0.1-SNAPSHOT.jar app.jar 
         zip todo.zip app.jar web.config'''
   }
   stage('deploy') {
      azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID,
      resourceGroup: env.RES_GROUP, appName: env.WEB_APP, filePath: "**/todo.zip"
   }
}
