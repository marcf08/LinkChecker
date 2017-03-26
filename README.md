# LinkChecker
Salesforce Knowledge Article LinkChecker

Background
==========
I write technical documentation for [MapAnything](http://mapanything.com/), a tech startup based on the Salesforce platform. Therefore I write
within the Salesforce ecosystem. I use [Salesforce Knowledge](https://www.salesforce.com/products/service-cloud/features/knowledge-base/)
to develop our information architecture. 

In technical writing, it's important to ensure your users have access to the best content at all times. A 404 page can
kill a user's experience, hence it's important to check for dead links as frequently as possible

Currently, Salesforce Knowledge does not offer a way to check your content for dead links. I noticed our 
[Resource Center](https://mapanything.force.com/support/s/) uses dynamically generated Javascript to create the page. It creates
a nice enough site, but there was no simple way to rip the HTML from the page and check it for dead links. At the
end of the day, one would have to use a web crawler to sort it out, and, frankly, I don't know enough Javascript to fix that
quickly.

The LinkChecker relies on you exporting your Knowledge Articles via the [Heroku Knowledge Exporter](https://kbapps2.herokuapp.com/). 
If you can't export your artiles via the Heroku application, I apologize. This repo will not work for you just yet. Eventually,
I'll get this application working via the Salesforce API. For now, however, this scanner simply matches the article name
to the csv file exported via Heroku. It then pulls the links from the HTML and reports them to the user. 

Usage
=====
Note: This project is still quite early in development.

**Environment**
OS: Mac OSX
IDE: Eclipse Neon.2 Release (4.6.2)

**Instructions**
1. Clone this repo in Eclipse.
2. Add the correct JAR files to the build path.
  a. Download the JARs listed in the screenshot below.
  b. Right-click the project -> Build Path -> Configure Build Path -> Add External JARs.
