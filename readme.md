Joshua Keif

Runs automatically and the environment is setup with gradle (Google Java Style formatting & Junit is automatic and set to java 11)

WebJsonGrabber pulls from githubs job positions & Stackoverflow, loops until empty pages are found for github and only pulls first 1000 from rss feed for Stack. All items are converted into a list of POJO's to they can be manipulated to fit the SQLite database that is generated, checks are in place for existing databases but duplicate posts are thrown instead of check. Tests are in place for expected information.

A test for bad data is currently missing.(Missing Bad data test for github and stackoverflow)
