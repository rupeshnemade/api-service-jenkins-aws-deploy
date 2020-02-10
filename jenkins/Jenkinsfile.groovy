pipeline {
    agent {
        label 'AwsJenkinsSlave'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    environment {
        REGION = "us-east-1"
        projectName = 'hotel_caseStudy'
        urlPrefix = 'https://github.com/rupesnemade/'
        projectUrl = "${urlPrefix}/ecs-deployement.git"
    }
    
    stages {
        stage('Docker login') {
            steps {
                sh '$(aws ecr get-login --no-include-email --region $REGION)'
            }
        }
        stage('Deployment') {
            stages {

                stage('Checkout') {
                    steps {
                        git(
                                url: projectUrl,
                                credentialsId: "git_credentials",
                                branch: 'master'
                        )
                        updateGitlabCommitStatus(state: 'running')

                    }
                }
                stage('Terraform - Plan') {
                    steps {
                        ansiColor('xterm') {
                            retry(3) {
                                sh 'make terraform-plan'
                            }
                        }
                    }
                }
                stage('Terraform - Apply') {
                    steps {
                        ansiColor('xterm') {
                            retry(3) {
                                sh 'make terraform-apply'
                            }
                        }
                    }
                }
            }
            post {
                always {
                    cleanWs()
                }
            }
        }
    }
    post {
        success {
            updateGitlabCommitStatus(state: 'success')
        }
        failure {
            updateGitlabCommitStatus(state: 'failed')
        }
        unstable {
            updateGitlabCommitStatus(state: 'failed')
        }
        always {
            echo "Send notifications for result: ${currentBuild.result}"
        }
    }
}