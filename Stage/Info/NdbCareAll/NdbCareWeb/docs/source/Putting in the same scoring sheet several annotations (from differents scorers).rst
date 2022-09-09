Putting in the same scoring sheet several annotations (from differents scorers)
===============================================================================

**Step 1 :** Create your ndb file for the edf file if you don’t have one.

**Step 2 :** Import the modules annotationToDfNdb and mergeNdb in your notebook with this command : 

>>> from NdbCare import annotationToDfNdb, mergeNdb

**Step 3 :** First we have to change the name of our annotations and their location with this function : 

>>> output = annotationToDfNdb.takeAndTransformDesiredAnnotationToTableNdb(your_ndb_file,name__of_scoring_sheet) 

For more details run this commend : 

>>> help(annotationToDfNdb.takeAndTransformDesiredAnnotationToTableNdb) 

**Step 4 :** Finally we put our new annotations (output of last function) in the scoring sheet of the ndb file that we want, so run this command : 

>>> mergeNdb.AddAnnotationToNdb(output,your_ndb_file,name__of_scoring_sheet) 

Then it's finish, you can check in noxturnal.