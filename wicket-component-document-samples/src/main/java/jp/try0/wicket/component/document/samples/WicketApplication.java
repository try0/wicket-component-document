package jp.try0.wicket.component.document.samples;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.JQueryResourceReference;

import jp.try0.wicket.component.document.ComponentDocumentOption;
import jp.try0.wicket.component.document.ComponentDocumentSetting;

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
							"https://github.com/try0/wicket-component-document/tree/main/wicket-component-document-core/src/main/java/")
					// wicket component document
					.addBaseUrl("org.apache.wicket",
							"https://github.com/apache/wicket/tree/wicket-8.x/wicket-core/src/main/java/")
					.initialize();
		}

	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {

		return RuntimeConfigurationType.DEVELOPMENT;
	}
}
