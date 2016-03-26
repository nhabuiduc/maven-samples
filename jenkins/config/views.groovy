def views = [
        [
                name: "View1",
                description: "",
                regex: ".*",
                recurse: true,
        ],
        [
                name: "View2",
                description: "",
                regex: ".*",
                recurse: true,
        ]
].each { i ->
    listView(i['name']) {
        description i['description']

        filterBuildQueue(false)
        filterExecutors(false)

        jobs {
            regex(i['regex'])
        }

        columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
        }

        recurse(true)
    }
}