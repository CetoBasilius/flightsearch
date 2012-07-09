
  Hb.GMap = Class.create();
  Hb.GMap.prototype = {
  	initialize: function(elementId, key, errorCallbackFunction, options, dragendCallbackFunction)
  	{
  		this.options = Object.extend({
  			zoomLevel:		 2,
  			smallControl:	  false,
  			largeControl:	  true,
  			typeControl:	   true,
  			label:			 "location"
  		}, options || {});

  		this.elementId = elementId;
  		this.key = key;
  		this.map = null;
  		this.errorCallbackFunction = errorCallbackFunction;
  		this.dragendCallbackFunction = dragendCallbackFunction;

  		if (GBrowserIsCompatible())
  		{
  			this.map = new GMap2($(elementId));

  			if (this.options.smallControl)
  				this.map.addControl(new GSmallMapControl());

  			if (this.options.typeControl)
  				this.map.addControl(new GMapTypeControl());

  			if (this.options.largeControl)
  				this.map.addControl(new GLargeMapControl());

  		}

  	},

  	setCenter: function(latitude, longitude)
  	{
  		var point = new GLatLng(latitude, longitude);
  		this.map.setCenter(point, this.options.zoomLevel);
  	},

  	addPolyline: function(Polylinein)
  	{
  	    var polyline = new GPolyline([
      		  new GLatLng(Polylinein.lat1,Polylinein.lng1),
      		  new GLatLng(Polylinein.lat2,Polylinein.lng2),
      		  new GLatLng(Polylinein.lat3,Polylinein.lng3),
      		  new GLatLng(Polylinein.lat4,Polylinein.lng4),
      		  new GLatLng(Polylinein.lat5,Polylinein.lng5),
      		  new GLatLng(Polylinein.lat6,Polylinein.lng6)
        ], "#ff0000", 10);
        this.map.addOverlay(polyline);
  	},

    setMarker: function(latitude, longitude, infoText, hiddenLatId, hiddenLngId, draggable)
    {
        var point = new GLatLng(latitude, longitude);
        var marker = new GMarker(point, {draggable: draggable});

        GEvent.addListener(marker, "click", function()
        {
            marker.openInfoWindowHtml(infoText);
        });

        if (draggable) {

            GEvent.addListener(marker, "dragstart", function() {
                this.closeInfoWindow();
            });

            GEvent.addListener(marker, "dragend", function() {
                document.getElementById(hiddenLatId).value = marker.getPoint().lat();
                document.getElementById(hiddenLngId).value = marker.getPoint().lng();
            });

        }

        this.map.addOverlay(marker);
    },

  	callException: function()
  	{
  		if (this.errorCallbackFunction.length > 0)
  			eval(this.errorCallbackFunction + "('" + this.elementId + "')");
  	},
  	/**
  	 * helper method.
  	 */
  	__getString:function(stringValue, preString, postString)
  	{
  		var returnString = "";

  		if (stringValue == null)
  			stringValue = "";

  		if (stringValue != "")
  			returnString += preString + stringValue + postString;

  		return returnString;
  	},

  	closeInfoWindow: function() {
  		this.map.closeInfoWindow();
  	}

  }