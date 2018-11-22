pipeline {
  agent any
  stages {
      stage('Start') {
      steps {
        echo 'Testing...'
        echo '${env.BUILD_NUMBER}'
      }
    }
    stage('App_Build_ST') {
      steps {
        echo 'Testing...'
        echo '${env.BUILD_NUMBER}'
      }
    }
    stage('Unit_Tests_ST') {
      steps {
        echo 'Unit_Tests_ST Testing...'
        echo '${env.BUILD_NUMBER}'
        deleteDir()
      }
    }
    stage('Code_Analysis_ST') {
      steps {
        echo 'Code_Analysis_ST Testing...'
        echo '${env.BUILD_NUMBER}'
      }
    }
    stage('Deploy_Environment_ST') {
      steps {
        echo 'Testing Deploy_Environment_ST'
      }
    }
    stage('Test_Build_ST') {
      steps {
        echo 'Testing Test_Build_ST'
      }
    }
    stage('Continuous_Testing_ST') {
      steps {
        parallel(
          "Continuous_Testing_ST": {
            echo 'Testing...'
            
          },
          "01_Functional_Tests_ST": {
            echo 'Functioanl'
            
          },
		   "02-Platform_Tests_ST": {
            echo 'Functioanl'
            
          },
          "03_BDD_Regression_Tests_ST": {
            echo 'Testing BDD'
            
          },
          "04-API_Tests_ST": {
            echo 'Testing...'
            
          }
        )
      }
    }
	stage('End') {
      steps {
        echo 'End'
      }
    }
  }
  post {
    always {
      echo 'I will always say Hello again!'
      
    }
    
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }
  parameters {
    string(name: 'WORKSPACE_NAME', defaultValue: 'MySecondWebDriverProject', description: 'Who should I say hello to?')
    string(name: 'PROJECT_NAME', defaultValue: 'MySecondWebDriverProject', description: 'Who should I say hello to?')
  }
}