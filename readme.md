Joshua Keif

Runs automatically and the environment is setup with gradle (Google Java Style formatting & Junit is automatic and set to java 11)

WebJsonGrabber pulls from githubs job positions, loops until empty pages are found, and converts the page using gson into a JobPost class structure that is only strings, dbManager takes the list of JobPosts in and pulls each field into the respective db field. The tests run automatically and see if the db exists, if good data is added properly.

A test for bad data is currently missing.(Function currently only accepts the JobPost that is all Strings and Further attempts to cast to strings)
