/**
 * ComponentDocument
 * 
 * @constructor
 * @this {ComponentDocument}
 */
function ComponentDocument() {

    const cdoc = this;
    const util = new ComponentDocumentUtil;

    /**
     * @type {string} Url attribute name
     */
    var urlAttributeName = "data-cdoc-url";
    /**
     * @type {string} Description attribute name
     */
    var descriptionAttributeName = "data-cdoc-desc";
    /**
     * @type {string} Url delimiter
     */
    var urlDelimiter = "|";
    /**
     * @type {boolean} Enebaled output urls
     */
    var active = false;
    /**
     * @type {boolean} Outputs url to console
     */
    var outputLog = true;
    /**
     * @type {boolean} Outputs url as tooltip
     */
    var tooltip = false;
    /**
     * @type {boolean} Enabled while pressing key
     */
    var activeWhilePressingKey = false;



    /**
     * Initializes ComponentDocument.
     *
     * @param {Object} options
     */
    this.initialize = function (options = {}) {

        // init options
        cdoc.setOptions(options);


        const bodyComponent = document.getElementsByTagName("body")[0];

        if (activeWhilePressingKey) {
            document.addEventListener("keydown", function (e) {
                active = cdoc.isActiveKey(e);
            });
            document.addEventListener("keyup", function (e) {
                active = false;
            });
        } else {
            active = true;
        }

        const cdocElements = cdoc.getCdocElements();


        for (var i = 0; i < cdocElements.length; i++) {
            const targetComponent = cdocElements[i];

            const descriptionAttrValue = targetComponent.getAttribute(descriptionAttributeName);
            const urlAttrValue = targetComponent.getAttribute(urlAttributeName);
            const urls = urlAttrValue.split(urlDelimiter);

            targetComponent.addEventListener("mouseover", function (e) {
                e.stopPropagation();

                if (!active) {
                    return;
                }

                // log
                if (outputLog) {
                    if (!util.isNullOrUndefined(descriptionAttrValue)) {
                        console.log(descriptionAttrValue);
                    }
                    for (var urlI = 0; urlI < urls.length; urlI++) {
                        const url = urls[urlI];
                        console.log(url);
                    }
                }

                if (!tooltip) {
                    return;
                }

                // show tooltip

                cdoc.removeAllTooltips();

                const x = this.getBoundingClientRect().left + window.pageXOffset;
                const y = this.getBoundingClientRect().top + window.pageYOffset;

                // container
                const tooltipContainer = document.createElement("div");
                tooltipContainer.style.position = "absolute";
                tooltipContainer.style.background = "rgb(32 142 228)";
                tooltipContainer.style.left = x + 10 + "px";
                tooltipContainer.style.top = y + 10 + "px";
                tooltipContainer.className = "cdoc-tooltip-container";

                tooltipContainer.addEventListener("mouseout", function (e) {
                    setTimeout(function () {
                        const isHovered = document.querySelectorAll(".cdoc-tooltip-container:hover").length != 0;
                        if (!isHovered && tooltipContainer.parentElement) {
                            tooltipContainer.parentElement.removeChild(tooltipContainer);
                        }
                    }, 1500);
                });

                // link
                for (var urlI = 0; urlI < urls.length; urlI++) {
                    const url = urls[urlI];
                    const tooltipLink = document.createElement("a");
                    tooltipLink.setAttribute("href", url);
                    tooltipLink.text = cdoc.getTooltipLabel(url);
                    tooltipLink.target = "_blank";
                    tooltipLink.rel = "noopener noreferrer";

                    tooltipContainer.appendChild(tooltipLink);
                }

                // description text
                if (!util.isNullOrUndefined(descriptionAttrValue)) {
                    const descriptionArea = document.createElement("div");
                    descriptionArea.textContent = descriptionAttrValue;
                    descriptionArea.className = "desc";
                    tooltipContainer.appendChild(descriptionArea);
                }

                bodyComponent.appendChild(tooltipContainer);

                targetComponent.addEventListener("mouseout", function (e) {
                    setTimeout(function () {
                        const isHovered = document.querySelectorAll(".cdoc-tooltip-container:hover").length != 0;
                        if (!isHovered && tooltipContainer.parentElement) {
                            tooltipContainer.parentElement.removeChild(tooltipContainer);
                        }
                    }, 1500);
                });
            });
        }
    }

    /**
     * Sets option values.
     *
     * @param {Object} options
     */
    this.setOptions = function (options) {

        if (util.isNullOrUndefined(options)) {
            return;
        }

        if (!util.isNullOrUndefined(options["urlAttributeName"])) {
            urlAttributeName = options["urlAttributeName"];
        }
        if (!util.isNullOrUndefined(options["descriptionAttributeName"])) {
            descriptionAttributeName = options["descriptionAttributeName"];
        }
        if (!util.isNullOrUndefined(options["urlDelimiter"])) {
            urlDelimiter = options["urlDelimiter"];
        }
        if (!util.isNullOrUndefined(options["outputLog"])) {
            outputLog = options["outputLog"];
        }
        if (!util.isNullOrUndefined(options["tooltip"])) {
            tooltip = options["tooltip"];
        }
        if (!util.isNullOrUndefined(options["activeWhilePressingKey"])) {
            activeWhilePressingKey = options["activeWhilePressingKey"];
        }

    }

    /**
     * Outputs all urls.
     */
    this.logAllUrls = function () {
        const cdocElements = this.getCdocElements();
        for (var i = 0; i < cdocElements.length; i++) {
            const targetComponent = cdocElements[i];
            const descriptionAttrValue = targetComponent.getAttribute(descriptionAttributeName);
            if (!util.isNullOrUndefined(descriptionAttrValue)) {
                console.log(descriptionAttrValue);
            }

            const urlAttrValue = targetComponent.getAttribute(urlAttributeName);
            const urls = urlAttrValue.split(urlDelimiter);
            for (var urlI = 0; urlI < urls.length; urlI++) {
                const url = urls[urlI];
                console.log(url);
            }
        }
    }

    /**
     * Removes all tooltip elements.
     */
    this.removeAllTooltips = function () {
        const dispTooltips = document.querySelectorAll(".cdoc-tooltip-container");
        for (var i = 0; i < dispTooltips.length; i++) {
            const removeTarget = dispTooltips[i];
            if (removeTarget.parentElement) {
                removeTarget.parentElement.removeChild(removeTarget);
            }
        }
    }

    /**
     * Gets the elements with attributes.
     */
    this.getCdocElements = function () {
        const cdocElements = document.querySelectorAll("[" + urlAttributeName + "]");
        return cdocElements;
    }

    /**
     * Gets tooltip label.
     * 
     * @param {string} url 
     * @returns 
     */
    this.getTooltipLabel = function (url) {
        return util.extractDomainName(url);
    }

    /**
     * 
     * @param {KeyboardEvent} e 
     * @returns 
     */
    this.isActiveKey = function (e) {
        return e.ctrlKey;
    }


    /**
     * Utilities
     */
    function ComponentDocumentUtil() {

        /**
         * Extracts domain name.
         * 
         * @param {string} url 
         * @returns 
         */
        this.extractDomainName = function (url) {
            var domain;

            if (url.indexOf("://") > -1) {
                domain = url.split("/")[2];
            } else {
                domain = url.split("/")[0];
            }

            domain = domain.split(":")[0];
            domain = domain.split("?")[0];
            return domain;
        }

        /**
         * 
         * @param {Object} obj 
         * @returns 
         */
        this.isNullOrUndefined = function (obj) {
            return obj === undefined || obj === null;
        };
    }


}

