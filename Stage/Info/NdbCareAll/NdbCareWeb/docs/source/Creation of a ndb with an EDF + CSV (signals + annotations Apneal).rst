Creation of a ndb with an EDF + CSV (signals + annotations Apneal)
==================================================================

This steps also work if the ndb file already has annotations (import csv in existing ndb file).

**Step 1 :** Create your ndb file for the edf file if you don't have one.

**Step 2 :** Import the modules annotationToDfNdb and mergeNdb in your notebook with this command : 

>>> from NdbCare import annotationToDfNdb, mergeNdb

**Step 3 :** Run this function : 

>>> output = annotationToDfNdb.CsvToDfNdb(fichierCsv, start_date)

Then run the output in this function : 

>>> mergeNdb.AddAnnotationToNdb(output,your_ndb_file,name__of_scoring_sheet) 

Then it's finish, you   can check in noxturnal.