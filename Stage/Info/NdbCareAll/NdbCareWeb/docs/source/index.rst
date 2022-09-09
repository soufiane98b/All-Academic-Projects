.. NdbCareWeb documentation master file, created by
   sphinx-quickstart on Thu Sep  8 15:18:05 2022.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to NdbCareWeb's documentation!
======================================

.. toctree::
   :maxdepth: 2
   :caption: Contents:



Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`


NdbCare Package Description
---------------------------

The purpose of this package is to facilitate the handling and performance of various operations on the database files of the medical software noxturnal.
A noxturnal database file is of the SQLite type and has the following extension: ndb .

Clone the project
-----------------

**Run from your terminal:**

>>> git clone https://gitlab.com/mitral.ai/apneal-analytics/-/tree/feature/noxturnal

**Import in nootebook:**

>>> import NdbCare

Quickstart
----------
.. toctree::
   Creation of a ndb with an EDF alone 
   Creation of a ndb with an EDF + RML (Alice file)
   Creation of a ndb with an EDF + CSV (signals + annotations Apneal)
   Merge Ndb files
   Putting in the same scoring sheet several annotations (from differents scorers)

Package Contents
----------------

.. toctree::
   mergeNdb
   annotationToDfNdb
   readNdb
   writeNdb
   dateNdb
   mappingNdb
   updateDependenciesNdb