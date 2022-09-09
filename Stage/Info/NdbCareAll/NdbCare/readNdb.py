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
import NdbCare.writeNdb
import NdbCare.dateNdb

def ShowExistingScoringSheet(nomFichier):
    """
    Show in a list the existing scoring sheet in the .ndb file.

    :Parameters:
    ----------
    nomFichier     :   str
                       Name of the .ndb file.
    :Returns:
    -------
    L              :   list
                    ::
    """
    conn = sq.connect(nomFichier)
    requet = " SELECT * FROM  scoring_revision WHERE is_deleted = 0 "
    df = pd.read_sql_query(requet,conn)
    return list(df['name'].unique())

def ShowTablesName(nomFichier):
    """
    Show in a list the name of the tables in the .ndb file.

    :Parameters:
    ----------
    nomFichier     :   str
                       Name of the .ndb file.
    :Returns:
    -------
    L              :   list
                    ::
    
    Notes
    -----
    Sub-functions of MergeByAddAll, AddAnnotationToNdb, MergeDataFiles.
    """
    conn = sq.connect(nomFichier)
    requet="SELECT name FROM sqlite_master WHERE type='table'"
    df = pd.read_sql_query(requet,conn)
    return list(df['name'])

def ShowInformationPatient(nomFichier):
    """
    Show in a data frame information about the patient.

    :Parameters:
    ----------
    nomFichier     :   str
                       Name of the .ndb file.
    :Returns:
    -------
    df             :   pandas.DataFrame
                    ::
    """
    conn = sq.connect(nomFichier)
    requet = " SELECT * FROM  internal_property "
    df = pd.read_sql_query(requet,conn)
    return df

def real_annot(nameFile):
    """
    Shows all the annotations (including sleep stages) in the .ndb file by ascending order of starting date.

    :Parameters:
    ----------
    name_file                   :   str
                                    Name of the .ndb file.                             
    :Returns:
    -------
    df         : pandas.DataFrame
                 return a date frame with the shape of the table scoring_marker and the dates are decoded. 
    """
    # Initialisation
    conn = sq.connect(nameFile)    
    dataF = pd.read_sql_query("SELECT * FROM scoring_marker",conn)
    
    # Taking not deleted rows
    L_id_not_deleted =  list(dataF[dataF['is_deleted']==0]['id'])
    out = L_id_not_deleted.copy()
    for iD in L_id_not_deleted:
        tmp = dataF[dataF['id']==iD]
        if(len(tmp) != 1 ): # id is unique
            return None
        starts_at = int(tmp['starts_at'])
        ends_at = int(tmp['ends_at'])
        typee = list(tmp['type'])[0]
        location = list(tmp['location'])[0]
        is_deleted = 1
        # We check if this sample has been deleted ( ie if the :  id whith is_deleted=1 > id whith is_deleted=0)
        condition = (dataF['starts_at'] == starts_at) &  (dataF['ends_at'] == ends_at) & ( dataF['type']==typee ) & ( dataF['location']==location )
        max_id = max(list(dataF[condition]['id']))
        deleted = int ( dataF[dataF['id']==max_id]['is_deleted']  )
        if( deleted == 1):
            out.remove(iD)
    
    col=['starts_at', 'type', 'location', 'is_deleted']
    data_out = dataF[dataF['id'].isin(out)]
    data_out = data_out.drop_duplicates(subset=col, keep='last') 
    data_out.sort_values(by='starts_at', inplace=True)
    data_out['starts_at']=data_out['starts_at'].apply(NdbCare.dateNdb.decoder_date)
    data_out['ends_at']=data_out['ends_at'].apply(NdbCare.dateNdb.decoder_date)
    data_out.reset_index(drop=True,inplace=True)
    return data_out
