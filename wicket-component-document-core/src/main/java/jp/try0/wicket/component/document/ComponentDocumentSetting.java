package jp.try0.wicket.component.document;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.danekja.java.util.function.serializable.SerializableSupplier;

import jp.try0.wicket.component.document.behavior.ComponentDcoumentBehavior;

/**
 * wicket-component-document Setting
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentSetting {

	/**
	 * Setting initializer.
	 *
	 * @author  Ryo Tsunoda
	 *
	 */
	public final static class Initializer {
		/**
		 * App
		 */
		private Application application;

		/**
		 * Whether to append automatically to page
		 */
		private boolean autoAppendBehavior = true;
		/**
		 * URL prefix list
		 */
		private Map<String, String> baseUrls = new HashMap<>();
		/**
		 * Default option value
		 */
		private ComponentDocumentOption defaultOption = new ComponentDocumentOption();
		/**
		 * Behavior factory
		 */
		private SerializableSupplier<ComponentDcoumentBehavior> behaviorFactory = () -> new ComponentDcoumentBehavior();

		/**
		 * Url attribute appender
		 */
		private DocumentUrlAppender urlAppender = new DocumentUrlAppender(component -> {

			String className = component.getClass().getName();

			ComponentDocumentSetting setting = ComponentDocumentSetting.get();
			for (Map.Entry<String, String> packageName : setting.baseUrls.entrySet()) {
				if (className.startsWith(packageName.getKey())) {
					return packageName.getValue();
				}
			}

			return "";
		});

		Initializer setApplication(Application application) {
			this.application = application;
			return this;
		}

		public Initializer setAutoAppendBehavior(boolean autoAppendBehavior) {
			this.autoAppendBehavior = autoAppendBehavior;
			return this;
		}

		public Initializer addBaseUrl(String prefixPackageName, String baserUrl) {
			this.baseUrls.put(prefixPackageName, baserUrl);
			return this;
		}

		public Initializer setDefaultOption(ComponentDocumentOption defaultOption) {
			this.defaultOption = defaultOption;
			return this;
		}

		public Initializer setUrlAppender(DocumentUrlAppender urlAppender) {
			this.urlAppender = urlAppender;
			return this;
		}

		public void setBehaviorFactory(SerializableSupplier<ComponentDcoumentBehavior> behaviorFactory) {
			this.behaviorFactory = behaviorFactory;
		}

		/**
		 * Initializes setting.
		 *
		 * @return
		 */
		public ComponentDocumentSetting initialize() {

			if (application.getMetaData(META_DATA_KEY) != null) {
				throw new UnsupportedOperationException(
						"The setting has already been initialized.");
			}

			final ComponentDocumentSetting setting = new ComponentDocumentSetting();

			setting.baseUrls = baseUrls;
			setting.defaultOption = defaultOption;
			setting.behaviorFactory = behaviorFactory;

			if (autoAppendBehavior) {
				application.getComponentInstantiationListeners().add(new ComponentDocumentBehaviorAppender());
				application.getComponentInstantiationListeners().add(urlAppender);
			}

			application.setMetaData(META_DATA_KEY, setting);

			return setting;
		}
	}

	private static final MetaDataKey<ComponentDocumentSetting> META_DATA_KEY = new MetaDataKey<ComponentDocumentSetting>() {
	};

	/**
	 * Default option value
	 */
	private ComponentDocumentOption defaultOption;

	/**
	 * URL prefix list
	 *
	 * key: Package name<br>
	 * val: Base url
	 */
	private Map<String, String> baseUrls = new HashMap<>();

	/**
	 * Behavior factory
	 */
	private SerializableSupplier<ComponentDcoumentBehavior> behaviorFactory;
	/**
	 * Url attribute appender.
	 */
	private DocumentUrlAppender urlAppender;

	public DocumentUrlAppender getUrlAppender() {
		return urlAppender;
	}

	public String getBaseUrl(String key) {
		return baseUrls.getOrDefault(key, "");
	}

	public ComponentDocumentOption getDefaultOption() {
		return defaultOption;
	}

	public SerializableSupplier<ComponentDcoumentBehavior> getBehaviorFactory() {
		return behaviorFactory;
	}

	/**
	 * Creates initializer.
	 *
	 * @param application
	 * @return
	 */
	public static Initializer createInitializer(Application application) {
		Initializer initialier = new Initializer();
		initialier.setApplication(application);
		return initialier;
	}

	/**
	 * Gets setting.
	 *
	 * @return
	 */
	public static ComponentDocumentSetting get() {

		if (!Application.exists()) {
			throw new IllegalStateException("There is no active application.");
		}

		Application application = Application.get();
		ComponentDocumentSetting settings = application.getMetaData(META_DATA_KEY);

		if (settings != null) {
			return settings;
		}

		return ComponentDocumentSetting.createInitializer(application).initialize();
	}

	public static String getUrl(String baseUrl, Class<?> clazz) {
		return baseUrl + getUrl(clazz);
	}

	public static String getUrl(Class<?> clazz) {
		return clazz.getName().replaceAll(Pattern.quote("."), "/") + ".java";
	}

}
