package jp.try0.wicket.component.document;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.danekja.java.util.function.serializable.SerializableFunction;

/**
 * data-cdoc-url ({@link ComponentDocumentOption#getUrlAttributeName()}) attribute appender
 *
 * @author Ryo Tsunoda
 *
 */
public class DocumentUrlAppender implements IComponentInstantiationListener {

	public static interface BaseUrlFactory extends SerializableFunction<Component, String> {
		static String toEmpty(Component component) {
			return "";
		}
	}

	private BaseUrlFactory baseUrlFactory = BaseUrlFactory::toEmpty;

	public DocumentUrlAppender() {
	}

	public DocumentUrlAppender(BaseUrlFactory baseUrlFactory) {
		this.baseUrlFactory = baseUrlFactory;
	}

	@Override
	public void onInstantiation(Component component) {
		String baseUrl = baseUrlFactory.apply(component);

		String url;
		if (baseUrl == null || baseUrl.isEmpty()) {
			url = getComponentPath(component);
		} else {
			url = baseUrl + (baseUrl.endsWith("/") ? "" : "/") + getComponentPath(component);
		}

		ComponentDocumentSetting setting = ComponentDocumentSetting.get();

		String attrName = setting.getDefaultOption().getUrlAttributeName();
		if (attrName == null || attrName.isEmpty()) {
			attrName = ComponentDocumentOption.DEFAULT_URL_ATTRIBUTE_NAME;
		}
		component.add(AttributeModifier.append("data-cdoc-url", url));
	}

	protected String getComponentPath(Component component) {
		return ComponentDocumentSetting.getUrl(component.getClass());
	}

	public BaseUrlFactory getBaseUrlFactory() {
		return baseUrlFactory;
	}

	public void setBaseUrlFactory(BaseUrlFactory baseUrlFactory) {
		this.baseUrlFactory = baseUrlFactory;
	}

}
