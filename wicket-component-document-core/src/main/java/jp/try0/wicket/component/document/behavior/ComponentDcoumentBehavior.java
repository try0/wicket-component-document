package jp.try0.wicket.component.document.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import jp.try0.wicket.component.document.ComponentDocumentOption;
import jp.try0.wicket.component.document.ComponentDocumentSetting;

/**
 * Core behavior
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDcoumentBehavior extends ComponentDocumentResourcesBehavior {
	private static final long serialVersionUID = 1L;

	/**
	 * Constractor
	 */
	public ComponentDcoumentBehavior() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);

		ComponentDocumentOption option = ComponentDocumentSetting.get().getDefaultOption();

		response.render(OnDomReadyHeaderItem.forScript(
				"const cdoc = new ComponentDocument;" +
						"cdoc.initialize(" + option.getOptiionString() + ");" + getAfterInitializedScript()));
	}

	public String getAfterInitializedScript() {
		return "cdoc.logAllUrls();";
	}
}
