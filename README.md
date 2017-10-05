# LinkChecker
Salesforce Knowledge Article LinkChecker

**Contents**
* [Background](https://github.com/marcf08/LinkChecker#background)
* [Screenshots](https://github.com/marcf08/LinkChecker#screenshots)
* [Usage](https://github.com/marcf08/LinkChecker#usage)
* [Development Backlog](https://github.com/marcf08/LinkChecker#development-backlog)
* [Release Notes/Issues](https://github.com/marcf08/LinkChecker#release-notesissues)

Background
==========
I write technical documentation for [MapAnything](http://mapanything.com/), a tech startup based on the Salesforce platform. Therefore I write
within the Salesforce ecosystem. I use [Salesforce Knowledge](https://www.salesforce.com/products/service-cloud/features/knowledge-base/)
to develop our information architecture. 

One of the limitations of working in Salesforce Knowledge is that there is not currently a tool to check for dead links. Links change all the 
time, and it's important to keep them updated to maintain a good user experience. Moreover, I noticed our 
[Resource Center](https://mapanything.force.com/support/s/) uses dynamically generated JavaScript to create the page. It creates
a nice enough site, but there was no simple way to rip the HTML from the page and check it for dead links. At the
end of the day, one would have to use a web crawler to sort it out, and, frankly, I don't know enough JavaScript to implement such a fix efficiently.

The LinkChecker relies on you exporting your Knowledge Articles via the [Heroku Knowledge Exporter](https://kbapps2.herokuapp.com/). 
If you can't export your articles via the Heroku application, I apologize. This repository will not work for you just yet. Eventually,
I'll get this application working via the Salesforce API. For now, however, this scanner simply matches the article name
to the CSV file exported via Heroku. It then pulls the links from the HTML and reports them to the user. 

Although this project was not designed to work with HTML files from outside of Salesforce, it should. The LinkChecker will simply
report that the filename is null.

Screenshots
===========

![alt text](https://github.com/marcf08/LinkChecker/blob/master/LinkChecker/Output.png?raw=true)

Usage
=====
Note: This project is still quite early in development.

**Environment**
* OS: Mac OSX
* IDE: Eclipse Neon Release (4.6.2)

**Instructions**
1. Export your articles via the [Heroku Knowledge Exporter](https://kbapps2.herokuapp.com/). Unzip them. 
2. If you have multiple article types, simply add them all to one folder. I create a folder called "html" and drop everything in it.
Make sure you keep the CSV file that Heroku generates. LinkChecker uses this to map the filename to the article name.
3. Download the LinkChecker jar file from this repository.
4. Double-click the jar file.
5. Point to the folder where you stored your articles. LinkChecker performs a recursive search (files within files), so just point it to the top level directory.
6. LinkChecker should run and give you all the output you need to fix your links. LinkChecker reports the HTTP status of your article and the links within it.

**Output**
LinkChecker reports the HTTP status code. Generally speaking, you want to look for status codes that are not 200. Redirects (3xx) won't affect your
end user, but you may want to fix them regardless.

|HTTP Status|Meaning  |
|-----------|---------|
|2xx        |OK       |
|3xx        |Redirect |
|4xx        |Not Found|

Development Backlog
===================
Short Term
* Test with Linux.
* Change Links from thread class to SingleThreadExecutor to prevent multiple threads running.
* Add Enable/Disable recursive search option.
* Resolve command line stack trace for bad file path.
* Add increased support for non-Salesforce articles.

Long Term
* Add support for Salesforce API. Allow users to log in directly and scan their articles from one interface.

Release Notes/Issues
====================
* If LinkChecker is running, a user can browse to another file and run the LinkChecker again. This spans another thread and is not desirable since both threads output to the same text area. This issue has been added to the development backlog.
