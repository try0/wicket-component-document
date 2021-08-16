package jp.try0.wicket.component.document;

import java.io.Serializable;

/**
 * js option values
 *
 * @author Ryo Tsunoda
 *
 */
public class ComponentDocumentOption implements Serializable {

	public static final String DEFAULT_URL_ATTRIBUTE_NAME = "data-cdoc-url";
	
	/**
	 * Url attribute name
	 */
	private String urlAttributeName;
	/**
	 * Url delimiter
	 */
	private String urlDelimiter;

	/**
	 * Outputs url to console
	 */
	private Boolean outputLog;
	/**
	 * Outputs url as tooltip
	 */
	private Boolean tooltip;
	/**
	 * Enabled output urls while pressing alt key
	 */
	private Boolean activeWhilePressingAltKey;

	public String getUrlAttributeName() {
		return urlAttributeName;
	}

	public void setUrlAttributeName(String urlAttributeName) {
		this.urlAttributeName = urlAttributeName;
	}

	public String getUrlDelimiter() {
		return urlDelimiter;
	}

	public void setUrlDelimiter(String urlDelimiter) {
		this.urlDelimiter = urlDelimiter;
	}

	public Boolean isEnabledOutputLog() {
		return outputLog;
	}

	public void setEnabledOutputLog(Boolean outputLog) {
		this.outputLog = outputLog;
	}

	public Boolean isEnabledTooltip() {
		return tooltip;
	}

	public void setEnabledTooltip(Boolean tooltip) {
		this.tooltip = tooltip;
	}

	public Boolean isEnabledActiveWhilePressingAltKey() {
		return activeWhilePressingAltKey;
	}

	public void setEnabledActiveWhilePressingAltKey(Boolean activeWhilePressingAltKey) {
		this.activeWhilePressingAltKey = activeWhilePressingAltKey;
	}

	boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public String getOptiionString() {

		StringBuilder sb = new StringBuilder("{");

		if (!isNullOrEmpty(urlAttributeName)) {
			sb.append("urlAttributeName:'").append(urlAttributeName).append("',");
		}
		if (!isNullOrEmpty(urlDelimiter)) {
			sb.append("urlDelimiter:'").append(urlDelimiter).append("',");
		}
		if (outputLog != null) {
			sb.append("outputLog:").append(outputLog).append(",");
		}
		if (tooltip != null) {
			sb.append("tooltip:").append(tooltip).append(",");
		}
		if (activeWhilePressingAltKey != null) {
			sb.append("activeWhilePressingAltKey:").append(activeWhilePressingAltKey).append(",");
		}

		sb.append("}");
		return sb.toString();
	}
}
