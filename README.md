# wicket-component-document

Assists in browsing documents related to components.

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

