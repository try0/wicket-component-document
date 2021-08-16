package jp.try0.wicket.component.document.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

import jp.try0.wicket.component.document.resource.ComponentDocumentCssResourceReference;
import jp.try0.wicket.component.document.resource.ComponentDocumentJavaScriptResourceReference;

/**
 * Resource appender
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentResourcesBehavior extends Behavior {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		response.render(CssHeaderItem.forReference(ComponentDocumentCssResourceReference.getInstance()));
		response.render(JavaScriptHeaderItem.forReference(ComponentDocumentJavaScriptResourceReference.getInstance()));

	}

}
