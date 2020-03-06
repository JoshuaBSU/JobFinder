Joshua Keif

How to run:
4 code modules that can be turned on and off through the bools inside of main(First time run set all to true)

runDownloaders: establishes a connection to Github and stackoverflow that pulls all job information into the primary database
uniquePopulateTable: filters all duplicates of our primary database and puts them into a new unique table
runGeoCoder: Using the above unique table we geocode all the viable location data and using that information populates the coordinate location data in the primary database. (Reducing API calls with 2,500 per day cap)
runGui: Launches the main gui that lets you enter filter parameters then loads a map based on that

The gui has labels that update based on the text enterred, once the text fields are updated, pressing the respective button will sort the list.
Once you have the parameters you wish to display, clicking the load map will pin all the relevent jobs

The environment is setup with gradle (Google Java Style formatting & Junit is automatic and set to java 1.8)
WebJsonGrabber pulls from githubs job positions & Stackoverflow, loops until empty pages are found for github and only pulls first 1000 from rss feed for Stack. All items are converted into a list of POJO's to they can be manipulated to fit the SQLite database that is generated, checks are in place for existing databases but duplicate posts are thrown instead of check. Tests are in place for expected information.

Missing Items:
A test for bad data is currently missing.(Missing Bad data test for github and stackoverflow)
Gui, map pin hover and clickability is currently missing aswell as tests for some search functions
A way to enter a date to search by, the search functions exists but no user input for a date (Statically set in FilterGUI.java as "Tue, 10 Feb 2020 08:02:24 Z")
