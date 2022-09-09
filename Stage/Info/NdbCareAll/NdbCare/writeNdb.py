
import sqlite3 as sq
import pandas as pd 
import math
from datetime import datetime
import numpy as np
from datetime import timedelta
import time
from dateutil.relativedelta import relativedelta, MO
import os
import NdbCare.updateDependenciesNdb
import NdbCare.mappingNdb
import NdbCare.mergeNdb
import NdbCare.annotationToDfNdb
import NdbCare.readNdb
import NdbCare.dateNdb

def UpdateId(nomFichier,iD):
    """
    Update the ID of the patient in a ndb file.

    :Parameters:
    ----------
    nomFichier     :   str
                       Name of the .ndb file.
    iD             :   int
                    ::
    """
    conn = sq.connect(nomFichier)
    cur = conn.cursor()
    cur.execute("UPDATE internal_property SET value ="+str(iD)+" WHERE key = 'ID' ")
    conn.commit()
    conn.close()


def GenerateNewNdbForEdf(FileNDB,NewName):
    """
    Generate an new ndb for edf file.

    :Parameters:
    ----------
    FileNDB:  ndb
              any ndb file that serves as our structure
    NewName  :str
              New name we want to give to the ndb file
    :Notes:
    -----
    Avoid to create a new ndb for an edf file manually.
    
    Then just put this new file ndb in the same folder of the file edf.
    
    """
    
    conn = sq.connect(FileNDB)
    cur = conn.cursor()
    requet="SELECT name FROM sqlite_master WHERE type='table'"
    df = pd.read_sql_query(requet,conn)
    for tableName in list(df['name']):
        requet=" DELETE FROM '"+ tableName+ "'"
        cur.execute(requet)
        conn.commit()
    os.rename(FileNDB,NewName)