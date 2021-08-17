package jp.try0.wicket.component.document;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	static final Set<String> EMPTY_SET = Collections.unmodifiableSet(new HashSet<>());

	public static interface BaseUrlFactory extends SerializableFunction<Component, Set<String>> {
	}

	public DocumentUrlAppender() {
	}

	@Override
	public void onInstantiation(Component component) {

		ComponentDocumentSetting setting = ComponentDocumentSetting.get();
		Set<String> baseUrls = getBaseUrls(component);

		String attrName = setting.getDefaultOption().getUrlAttributeName();
		if (attrName == null || attrName.isEmpty()) {
			attrName = ComponentDocumentOption.DEFAULT_URL_ATTRIBUTE_NAME;
		}

		String attrValue = baseUrls.stream().map(baseUrl -> getComponentDocumentUrl(baseUrl, component))
				.collect(Collectors.joining(setting.getDefaultOption().getUrlDelimiterOrDefault()));

		component.add(AttributeModifier.append(attrName, attrValue));
	}

	/**
	 * Gets document url of arg component.
	 *
	 * @param baseUrl
	 * @param component
	 * @return
	 */
	protected String getComponentDocumentUrl(String baseUrl, Component component) {
		if (baseUrl == null || baseUrl.isEmpty()) {
			return getComponentPath(component);
		} else {
			return baseUrl + (baseUrl.endsWith("/") ? "" : "/") + getComponentPath(component);
		}
	}

	/**
	 * Converts component to path.
	 *
	 * @param component
	 * @return
	 */
	protected String getComponentPath(Component component) {
		return ComponentDocumentSetting.getUrl(component.getClass());
	}

	/**
	 * Gets base urls.
	 *
	 * @param component
	 * @return
	 */
	protected Set<String> getBaseUrls(Component component) {
		String className = component.getClass().getName();

		ComponentDocumentSetting setting = ComponentDocumentSetting.get();

		for (Map.Entry<String, Set<String>> packageUrls : setting.getBaseUrls().entrySet()) {

			if (className.startsWith(packageUrls.getKey())) {
				return packageUrls.getValue();
			}
		}

		return EMPTY_SET;
	}

}
