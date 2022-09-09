Creation of a ndb with an EDF alone
===================================

Method 1 /
----------

**Step 1 :** Import the module writeNdb in you notebook with this command : 

>>> from NdbCare import writeNdb

**Step 2 :** Put in your directory (which contains your notebook file) any ndb file.

**Step 3 :** Run this function : 

>>> writeNdb.GenerateNewNdbForEdf( FileNDB = name_of_your_ndb_file_with_extension , NewName = name_of_your_edf_file_without_extension.ndb)

**Step 4 :** Put in a folder your edf file and your ndb file ( they should have the same name ). Then you can start to add annotation or anything you want to the ndb file.

Method 2 /
----------

Take the ndb file from this S3 URI adress : 

