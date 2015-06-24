# Flightsearch #
**Introduction**

Ever wanted to find a flight that adjusts to your budget? Thats what Flightsearch is all about. finding flights from several dealers and travel sites with an easy to use and intuitive user interface.

**News**
  * 7/30/2012 - Second release: Results page is really informative now, results can be printed, flight routes can be previewed, and many validation bugs fixed and enhancements made. this release can be downloaded [here](http://code.google.com/p/flightsearch/downloads/detail?name=flightsearch-0.2.war&can=2&q=#makechanges). the latest revision for this release is revision [80](http://code.google.com/p/flightsearch/source/detail?r=80).

  * 7/13/2012 - Integrated Google map to results page and flight search results are now stored in proper Java classes.

  * 7/06/2012 - Application now has flight search results in Json raw form. Search results  and additional parameters are being worked on

  * 6/29/2012 - Uploaded first release, it [can be found here](http://code.google.com/p/flightsearch/downloads/detail?name=flightsearch-0.1.war&can=2&q=#makechanges). The wiki is being worked on and will be up soon. to see how time is being spent you can visit the following [link](https://docs.google.com/a/nearsoft.com/spreadsheet/pub?key=0Arb4o3BtCUHJdHljaURNUEJNcElOSE4zQzdTaFozWkE&output=html).

**Background**

The main Aim of the project is to gain popular framework knowledge and develop better design pattern skills, as well as improving certain programming practices.

For more information you can visit the [Wiki](http://code.google.com/p/flightsearch/wiki/Index)


Future Features:
  * Easy travel date selection.
  * Easy to read results list or map.
  * User login and preferences.
  * Save specific search results.


Wanted Features:
  * Travel Suggestion: recommend places that will be nearby or that you might be passing through.

**Information**

The application was built using the Tapestry Maven archetype, with Tapestry 5.3.3.

Tapestry is a "Component oriented framework for creating dynamic, robust, highly scalable web applications in Java." http://tapestry.apache.org/ It has many advantages and features you can read about in their main page.

The application itself runs inside Jetty. Jetty provides an HTTP server and Servlet container capable of serving static and dynamic content either from a standalone or embedded instantiation. You can read more about Jetty on their main page http://jetty.codehaus.org/jetty/

The servlet can also be run on Tomcat or other containers.

Hibernate and HSLQLDB work together for the persistence layer, where Users and their saved data will be stored.

Please note, that the application is in a very early, no purpose stage. All you can do currently is register as a user, and login. Search functions do not completely work yet.