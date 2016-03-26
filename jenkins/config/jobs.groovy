/**
 * JOB CONFIGURATIONS
 */
def jobs = [
        [
                id: "0815",
                name: "projectname",
                repository: "https://github.com/nhabuiduc/maven-samples.git",
        ],
        [
                id: "0816",
                name: "projectname",
                repository: "https://github.com/nhabuiduc/maven-samples.git",
                recipient_list: "email1@example.com,email2@example.com",
                label: "master",
        ]
].each { i ->
    job_name_base = i['id'] + "-" + i['name'].replaceAll(" ", "-")

    for ( j in 0..1500 ) {
      job_name = job_name_base + j
      folder(job_name) {
          displayName('Project ' + job_name)
          description('Folder for ' + job_name)
      }

      if (i.containsKey('label')) {
          label(i['label'])
      }

      job( job_name + '/build') {
          description('Builds the component')
          using "template-defaults"
          disabled(false)

          scm {
              git(i['repository'])
          }
          triggers {
              scm('*/15 * * * *')
          }
          steps {
              shell("""\
                  CONFIG_NAME=`echo \$JOB_NAME | tr '[:upper:]' '[:lower:]'`
                  export CONFIG_NAME=\${CONFIG_NAME##*/}
                  echo \$CONFIG_NAME
                  export STAGE=\${CONFIG_NAME##*_}
                  echo \$STAGE

                  # We can have sanity checks
                  # e.g. make sure that we compile only ONCE in the build step
                  # and not during deployments
                  grep "mvn compile" bin/build*

                  bash -e bin/\${CONFIG_NAME}.sh
                  """.stripIndent().trim()
              )
          }
      }
    }
    // Example: Override DEFAULT_RECIPIENT S for extendedEmail plugin
    // recipient_list = i.containsKey('recipient_list') ? i['recipent_list'] : '$DEFAULT_RECIPIENTS'


}
