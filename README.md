# wicket-component-document

Assists in browsing documents related to components.


## console
```java
if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {

    ComponentDocumentOption defaultOption = new ComponentDocumentOption();

    ComponentDocumentSetting.createInitializer(this)
            .setDefaultOption(defaultOption)
            // wicket-component-document-samples component document
            .addBaseUrl("jp.try0.wicket",
                    "https://github.com/try0/wicket-component-document/tree/main/wicket-component-document-samples/src/main/java/")
            // wicket component document
            .addBaseUrl("org.apache.wicket",
                    "https://github.com/apache/wicket/tree/wicket-8.x/wicket-core/src/main/java/")
            .initialize();
}
```

![wicket-component-document_console](https://user-images.githubusercontent.com/17096601/129600941-fe81c4db-3ca2-4b5d-b044-7a1d12ff41d8.gif)




## multiple documents, tooltip

```java
if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {

    ComponentDocumentOption defaultOption = new ComponentDocumentOption();
    defaultOption.setEnabledTooltip(true);

    ComponentDocumentSetting.createInitializer(this)
            .setDefaultOption(defaultOption)
            // wicket-component-document-samples component document
            .addBaseUrl("jp.try0.wicket",
                    "https://github.com/try0/wicket-component-document/tree/main/wicket-component-document-samples/src/main/java/")
            // wicket component document
            .addBaseUrl("org.apache.wicket",
                    "https://github.com/apache/wicket/tree/wicket-8.x/wicket-core/src/main/java/")
            // wicket javadoc
            .addBaseUrl("org.apache.wicket",
                    "https://javadoc.io/doc/org.apache.wicket/wicket-core/8.13.0/")
            .setUrlAppender(new DocumentUrlAppender() {
                @Override
                protected String getComponentDocumentUrl(String baseUrl, Component component) {

                    String url = super.getComponentDocumentUrl(baseUrl, component);
                    if (baseUrl.startsWith("https://javadoc.io")) {
                        url = url.replaceAll("java$", "html");
                    }

                    return url;
                }
            })
            .initialize();
}
```

![wicket-component-document_tooltip](https://user-images.githubusercontent.com/17096601/129847128-b185d333-68b6-4ccb-9fab-14ff00096d2b.gif)

