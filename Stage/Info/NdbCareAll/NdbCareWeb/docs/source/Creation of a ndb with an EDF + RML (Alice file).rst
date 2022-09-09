Creation of a ndb with an EDF + RML (Alice file)
================================================

This steps also work if the ndb file already has annotations (import rml in existing ndb file).

**Step 1 :** Create your ndb file for the edf file if you don't have one.

**Step 2 :** Import the modules annotationToDfNdb and mergeNdb in your notebook with this command : 

>>> from NdbCare import annotationToDfNdb, mergeNdb

**Step 3 :** Run this function : 

>>> output = annotationToDfNdb.RmlToDfNdb(your_rml_file)

Then run the output in this function : 

>>> mergeNdb.AddAnnotationToNdb(output,your_ndb_file,name__of_scoring_sheet) 

Then it's finish, you   can check in noxturnal.







