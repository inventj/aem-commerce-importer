new hobs.TestSuite("aem-demo-site Tests", {path:"/apps/aem-demo-site/tests/SampleTests.js", register: true})

    .addTestCase(new hobs.TestCase("Hello World component on english page")
        .navigateTo("/content/aem-demo-site/en.html")
        .asserts.location("/content/aem-demo-site/en.html", true)
        .asserts.visible(".main", true)
    )
