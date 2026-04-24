pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    environment {
        JAVA_HOME = tool name: 'JDK17', type: 'jenkins.plugins.shiningpanda.tools.JavaInstallation'
        PATH = "${JAVA_HOME}/bin:${PATH}"
        MAVEN_HOME = tool name: 'Maven3', type: 'hudson.tasks.Maven$MavenInstallation'
        PATH = "${MAVEN_HOME}/bin:${PATH}"
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

