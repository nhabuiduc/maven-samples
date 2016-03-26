folder('project-a') {
    displayName('Project A')
    description('Folder for project A')
}


job('project-a/build2') {
    description('Builds the component')

    scm {
        git('https://github.com/nhabuiduc/maven-samples.git')
    }
    triggers {
        scm('*/15 * * * *')
    }
    steps {
        shell('''
CONFIG_NAME=`echo $JOB_NAME | tr '[:upper:]' '[:lower:]'`
export CONFIG_NAME=${CONFIG_NAME##*/}
echo $CONFIG_NAME
export STAGE=${CONFIG_NAME##*_}
echo $STAGE

# We can have sanity checks
# e.g. make sure that we compile only ONCE in the build step
# and not during deployments
grep "mvn compile" bin/build*

bash -e bin/${CONFIG_NAME}.sh
''')
    }
}
