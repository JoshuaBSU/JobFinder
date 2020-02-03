Joshua Keif

Runs automatically and the environment is setup with gradle (Google Java Style formatting & Junit is automatic and set to java 11)

WebJsonGrabber pulls from githubs job positions, loops until empty pages are found, and converts the page using gson into a JobPost class structure, prints structures to a json file using gson. The tests run automatically and see if the jobs pulled exceed 100.

Missing Test for fileWriter()
