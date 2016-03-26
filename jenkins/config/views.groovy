def views = [
        [
                name: "0815",
                description: "",
                regex: "^0815-.*",
                recurse: true,
        ],
        [
                name: "0816",
                description: "",
                regex: "^0816-.*",
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
