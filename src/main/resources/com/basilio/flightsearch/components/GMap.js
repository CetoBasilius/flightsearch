
  Hb.GMap = Class.create();
  Hb.GMap.prototype = {
  	initialize: function(elementId, key, errorCallbackFunction, options, dragendCallbackFunction)
  	{
  		this.options = Object.extend({
  			zoomLevel:3,
  			smallControl:false,
  			largeControl:true,
  			typeControl:true,
  			label:"location"

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

  	    this.addGeodesicPolyline(Polylinein.lat1,Polylinein.lng1,Polylinein.lat2,Polylinein.lng2);
  	    this.addGeodesicPolyline(Polylinein.lat2,Polylinein.lng2,Polylinein.lat3,Polylinein.lng3);
  	    this.addGeodesicPolyline(Polylinein.lat3,Polylinein.lng3,Polylinein.lat4,Polylinein.lng4);
  	    this.addGeodesicPolyline(Polylinein.lat4,Polylinein.lng4,Polylinein.lat5,Polylinein.lng5);
  	    this.addGeodesicPolyline(Polylinein.lat5,Polylinein.lng5,Polylinein.lat6,Polylinein.lng6);
  	    this.addGeodesicPolyline(Polylinein.lat6,Polylinein.lng6,Polylinein.lat7,Polylinein.lng7);
  	    this.addGeodesicPolyline(Polylinein.lat7,Polylinein.lng7,Polylinein.lat8,Polylinein.lng8);

  	},

  	addGeodesicPolyline: function(x1,y1,x2,y2){
  	    var routes = new Array();
        var fPoints = new Array();

        with (Math) {

            var lat1 = x1 * (PI/180);
            var lon1 = y1 * (PI/180);
            var lat2 = x2 * (PI/180);
            var lon2 = y2 * (PI/180);

            var d = 2*asin(sqrt( pow((sin((lat1-lat2)/2)),2) + cos(lat1)*cos(lat2)*pow((sin((lon1-lon2)/2)),2)));
            var bearing = atan2(sin(lon1-lon2)*cos(lat2), cos(lat1)*sin(lat2)-sin(lat1)*cos(lat2)*cos(lon1-lon2))  / -(PI/180);
            bearing = bearing < 0 ? 360 + bearing : bearing;

            for (var n = 0 ; n < 51 ; n++ ) {
                var f = (1/50) * n;
                f = f.toFixed(6);
                var A = sin((1-f)*d)/sin(d)
                var B = sin(f*d)/sin(d)
                var x = A*cos(lat1)*cos(lon1) +  B*cos(lat2)*cos(lon2)
                var y = A*cos(lat1)*sin(lon1) +  B*cos(lat2)*sin(lon2)
                var z = A*sin(lat1)           +  B*sin(lat2)

                var latN = atan2(z,sqrt(pow(x,2)+pow(y,2)))
                var lonN = atan2(y,x)
                var p = new GLatLng(latN/(PI/180), lonN/(PI/180));
                fPoints.push(p);
            }

        }

        routes.push(fPoints);
        var pLine = new GPolyline(fPoints,'#CC1100',3,1);
        this.map.addOverlay(pLine);
  	},

    setMarker: function(latitude, longitude, infoText ,index)
    {
        var baseIcon = new GIcon(G_DEFAULT_ICON);
        var point = new GLatLng(latitude, longitude);

        var letteredIcon = new GIcon(baseIcon);
        var letter = String.fromCharCode("A".charCodeAt(0) + index);
        letteredIcon.image = "http://www.google.com/mapfiles/marker" + letter + ".png";

        markerOptions = { icon:letteredIcon
                        };

        var marker = new GMarker(point,markerOptions);

        GEvent.addListener(marker, "click", function()
        {
            marker.openInfoWindowHtml(infoText);
        });

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