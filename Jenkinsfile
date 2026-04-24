pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Repository checked out successfully"
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    echo "Running Selenium Tests..."
                    sh 'mvn clean test -DsuiteXmlFile=src/testng.xml'
                }
            }
        }

        stage('Generate Report') {
            steps {
                script {
                    echo "Report generation handled by ExtentReports during test execution"
                }
            }
        }
    }

    post {
        always {
            script {
                echo "Test execution completed"
                // Archive the ExtentReport
                archiveArtifacts artifacts: 'test-output/ExtentReport.html',
                                 allowEmptyArchive: true,
                                 fingerprint: true
            }
        }

        success {
            echo "✅ All tests passed successfully!"
        }

        failure {
            echo "❌ Tests failed. Check the ExtentReport for details."
        }

        unstable {
            echo "⚠️ Some tests failed or were skipped."
        }

        cleanup {
            cleanWs()
        }
    }
}

