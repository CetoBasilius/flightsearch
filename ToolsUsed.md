# Tools and Frameworks used #

The application was built using the Tapestry Maven archetype, with Tapestry 5.3.3.

Tapestry is a "Component oriented framework for creating dynamic, robust, highly scalable web applications in Java." http://tapestry.apache.org/ It has many advantages and features you can read about in their main page.

The application itself runs inside Jetty. Jetty provides an HTTP server and Servlet container capable of serving static and dynamic content either from a standalone or embedded instantiation. You can read more about Jetty on their main page http://jetty.codehaus.org/jetty/

Hibernate and HSLQLDB work together for the persistence layer, where Users and their saved data will be stored.

The project currently uses the following tools and frameworks:
  * [Maven](http://maven.apache.org/)
  * [Apache Tapestry](http://tapestry.apache.org/)
  * [Hibernate](http://www.hibernate.org/)
  * [HSQLDB](http://hsqldb.org/)
  * [Jetty](http://jetty.codehaus.org/jetty/)
  * [HttpClient](http://hc.apache.org/httpclient-3.x/)
  * [Jsoup](http://jsoup.org/)

And we plan on using these in the future:
  * GSON
  * CXF
  * JAX-WS
  * JDom