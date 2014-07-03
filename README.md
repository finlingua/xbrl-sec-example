xbrl-sec-example
================

Example Java project using JAXB to read SEC XBRL filings

There are two main challenges in binding to the XBRL schemas with JAXB:
  1. Configuring the binding for the Title property in `xl-2003-12-31.xsd`.
  2. Some of the SEC XSD schemas assume that their imports are local, while others use remote imports. If not made consistent, JAXB will fail due to duplicately defined elements

The project is based on Vladimir Klevko's Stack Overflow answer for "JAXB fails to generate Java classes for XBRL": https://stackoverflow.com/questions/12913445/jaxb-fails-to-generate-java-classes-for-xbrl
