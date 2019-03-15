pipeline {
    stages {
	     def mvnHome = tool 'M3'
        stage(‘Build Development') {
            when {
                branch ‘development’ 
            }
            steps {
                //sh './jenkins/scripts/deliver-for-development.sh'
                //input message: 'Finished using the web site? (Click "Proceed" to continue)'
                //sh './jenkins/scripts/kill.sh'

   	   		// Run the maven build
   			sh "${mvnHome}/bin/mvn clean test"
   			step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
            }
        }
        stage(‘Build Integration’) {
            when {
                branch ‘integration'  
            }
            steps {
   	   		// Run the maven build
   			sh "${mvnHome}/bin/mvn clean test"
   			step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
            }
        }
    }
}
