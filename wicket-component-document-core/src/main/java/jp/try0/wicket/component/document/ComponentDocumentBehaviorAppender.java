package jp.try0.wicket.component.document;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInstantiationListener;

import jp.try0.wicket.component.document.behavior.ComponentDcoumentBehavior;

/**
 * {@link ComponentDcoumentBehavior} appender
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentBehaviorAppender implements IComponentInstantiationListener {

	@Override
	public void onInstantiation(Component component) {
		if (component instanceof Page) {
			ComponentDcoumentBehavior behavior = ComponentDocumentSetting.get().getBehaviorFactory().get();
			component.add(behavior);
		}
	}

}
