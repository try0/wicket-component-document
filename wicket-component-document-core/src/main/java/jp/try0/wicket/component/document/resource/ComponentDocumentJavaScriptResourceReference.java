package jp.try0.wicket.component.document.resource;

import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Reference of javascript
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentJavaScriptResourceReference extends JavaScriptResourceReference {
	private static final long serialVersionUID = 1L;

	private static class Holder {
		private static final ComponentDocumentJavaScriptResourceReference INSTANCE = new ComponentDocumentJavaScriptResourceReference();
	}

	/**
	 * Gets ComponentDocumentJavaScriptResourceReference instance.
	 *
	 * @return constant
	 */
	public static ComponentDocumentJavaScriptResourceReference getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Constractor
	 */
	public ComponentDocumentJavaScriptResourceReference() {
		super(ComponentDocumentJavaScriptResourceReference.class, "component-document.js");
	}

}
