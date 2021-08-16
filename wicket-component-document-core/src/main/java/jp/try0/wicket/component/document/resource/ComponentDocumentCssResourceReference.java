package jp.try0.wicket.component.document.resource;

import org.apache.wicket.request.resource.CssResourceReference;

/**
 * Reference of css
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentCssResourceReference extends CssResourceReference {
	private static final long serialVersionUID = 1L;

	private static class Holder {
		private static final ComponentDocumentCssResourceReference INSTANCE = new ComponentDocumentCssResourceReference();
	}

	/**
	 * Gets ComponentDocumentCssResourceReference instance.
	 *
	 * @return constant
	 */
	public static ComponentDocumentCssResourceReference getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * Constractor
	 */
	public ComponentDocumentCssResourceReference() {
		super(ComponentDocumentCssResourceReference.class, "component-document.css");
	}

}
