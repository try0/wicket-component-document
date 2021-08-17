package jp.try0.wicket.component.document.samples;

import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.JQueryResourceReference;

import jp.try0.wicket.component.document.ComponentDocumentOption;
import jp.try0.wicket.component.document.ComponentDocumentSetting;
import jp.try0.wicket.component.document.DocumentUrlAppender;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see jp.try0.wicket.component.document.samples.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		super.init();

		getJavaScriptLibrarySettings().setJQueryReference(JQueryResourceReference.getV3());

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

	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {

		return RuntimeConfigurationType.DEVELOPMENT;
	}
}
