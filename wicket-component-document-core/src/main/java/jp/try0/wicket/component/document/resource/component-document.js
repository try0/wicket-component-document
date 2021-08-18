/**
 *
 */
function ComponentDocument() {

    const this_ = this;

    /**
     * Url attribute name
     */
    var urlAttributeName = "data-cdoc-url";
    /**
     * Url delimiter
     */
    var urlDelimiter = "|";
    /**
     * Enebaled output urls
     */
    var active = false;
    /**
     * Outputs url to console
     */
    var outputLog = true;
    /**
     * Outputs url as tooltip
     */
    var tooltip = false;
    /**
     * Enabled output urls while pressing alt key
     */
    var activeWhilePressingAltKey = false;



    /**
     * Initialize ComponentDocument
     *
     * @param {} options
     */
    this.initialize = function(options = {}) {

        // init options
        this_.setOptions(options);

        if (activeWhilePressingAltKey) {
            document.addEventListener("keydown", function(e) {
                active = e.altKey;
            });
            document.addEventListener("keyup", function(e) {
                active = e.altKey;
            });
        } else {
            active = true;
        }

        const cdocElements = this_.getCdocElements();
        const bodyComponent = document.getElementsByTagName("body")[0];

        for (var i = 0; i < cdocElements.length; i++) {
            const targetComponent = cdocElements[i];

            const urlAttrValue = targetComponent.getAttribute(urlAttributeName);
            const urls = urlAttrValue.split(urlDelimiter);

            targetComponent.addEventListener("mouseover", function(e) {

                e.stopPropagation();

                if (!active) {
                    return;
                }

                if (outputLog) {
                    for (var urlI = 0; urlI < urls.length; urlI++ ) {
                        const url = urls[urlI];
                        console.log(url);
                    }
                }

                if (!tooltip) {
                    return;
                }

                const dispTooltips = document.querySelectorAll('.cdoc-tooltip-container');
                for (var dtI = 0; dtI < dispTooltips.length; dtI++) {
                    var removeTarget = dispTooltips[dtI];
                    if (removeTarget.parentElement) {
                        removeTarget.parentElement.removeChild(removeTarget);
                    }
                }

                const x = this.getBoundingClientRect().left + window.pageXOffset;
                const y = this.getBoundingClientRect().top + window.pageYOffset;

                const tooltipContainer = document.createElement("div");
                tooltipContainer.style.position = "absolute";
                tooltipContainer.style.background = "rgb(32 142 228)";
                tooltipContainer.style.left = x + 10 + "px";
                tooltipContainer.style.top = y + 10 + "px";
                tooltipContainer.className = "cdoc-tooltip-container";

                tooltipContainer.addEventListener("mouseout", function(e) {
                    setTimeout(function() {
                        const isHovered = document.querySelectorAll('.cdoc-tooltip-container:hover').length != 0;
                        if (!isHovered && tooltipContainer.parentElement) {
                            tooltipContainer.parentElement.removeChild(tooltipContainer);
                        }
                    }, 1500);
                });


                for (var urlI = 0; urlI < urls.length; urlI++ ) {
                    const url = urls[urlI];
                    const tooltipLink = document.createElement("a");
                    tooltipLink.setAttribute("href", url);
                    tooltipLink.text = this_.getTooltipLabel(url);
                    tooltipLink.target = "_blank";

                    tooltipContainer.appendChild(tooltipLink);
                }



                bodyComponent.appendChild(tooltipContainer);

                targetComponent.addEventListener("mouseout", function(e) {
                    setTimeout(function() {
                        const isHovered = document.querySelectorAll('.cdoc-tooltip-container:hover').length != 0;
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
     * @param {} options
     */
    this.setOptions = function(options) {

        if (options === undefined) {
            return;
        }

        if (options['urlAttributeName'] !== undefined) {
            urlAttributeName = options['urlAttributeName'];
        }
        if (options['descriptionAttributeName'] !== undefined) {
            descriptionAttributeName = options['descriptionAttributeName'];
        }
        if (options['urlDelimiter'] !== undefined) {
            urlDelimiter = options['urlDelimiter'];
        }
        if (options['outputLog'] !== undefined) {
            outputLog = options['outputLog'];
        }
        if (options['tooltip'] !== undefined) {
            tooltip = options['tooltip'];
        }
        if (options['activeWhilePressingAltKey'] !== undefined) {
            activeWhilePressingAltKey = options['activeWhilePressingAltKey'];
        }

    }

    /**
     * Outputs all urls.
     */
    this.logAllUrls = function() {
        const cdocElements = this.getCdocElements();
        for (var i = 0; i < cdocElements.length; i++) {
            const targetComponent = cdocElements[i];
            const urlAttrValue = targetComponent.getAttribute(urlAttributeName);
            const urls = urlAttrValue.split(urlDelimiter);
            for (var urlI = 0; urlI < urls.length; urlI++ ) {
                const url = urls[urlI];
                console.log(url);
            }
        }
    }

    /**
     * Gets the elements with attributes
     */
    this.getCdocElements = function() {
        const cdocElements = document.querySelectorAll('[' + urlAttributeName + ']');
        return cdocElements;
    }

    /**
     * Gets tooltip label.
     * 
     * @param  url 
     * @returns 
     */
    this.getTooltipLabel = function(url) {
        return this.extractDomainName(url);
    }

    /**
     * 
     * @param url 
     * @returns 
     */
    this.extractDomainName = function(url) {
        var domain;

        if (url.indexOf("://") > -1) {
            domain = url.split('/')[2];
        } else {
            domain = url.split('/')[0];
        }
        
        domain = domain.split(':')[0];
        domain = domain.split('?')[0];
        return domain;
    }

}

