import sqlite3 as sq
import pandas as pd 
import math
from datetime import datetime
import numpy as np
from datetime import timedelta
import time
from dateutil.relativedelta import relativedelta, MO
import os
import NdbCare.mappingNdb
import NdbCare.mergeNdb
import NdbCare.annotationToDfNdb
import NdbCare.readNdb
import NdbCare.writeNdb
import NdbCare.dateNdb




def addRowsToData(row , nameOfTable , conn):
    '''
    Add a dataFrame to table of a database.
    
    :Parameters:
    ----------
    row               :   pandas.DataFrame
                      ::
    nameOfTable       :   str
                      ::
    conn              :   sqlite3.Connection
                      ::
                    
    :Notes:
    -----
    Sub-functions of MergeDataFiles.
    
    '''
    row.to_sql(name=nameOfTable,con=conn,if_exists='append', index=False)
    conn.commit()
        
def addNewIdToScoringKey(conn):
    '''
    Update the table scoring_key located in db file of conn.
    
    :Parameters:
    ----------
    conn       :   sqlite3.Connection
                ::
    :Notes:
    --------
    Sub-functions of MergeDataFiles.
    
    '''
    requet = " SELECT * FROM scoring_key "
    df = pd.read_sql_query(requet,conn)
    if(df.empty) : 
        key_id = 0
    else :
        key_id = (df[['id']].iloc[-1][0])+1
    Date = NdbCare.dateNdb.encodeDateNow()
    row = pd.DataFrame({  'id' : [key_id],'date_created':[Date],'name': [''],'owner':[''],'type':['Manual']   })
    addRowsToData(row,'scoring_key',conn)
    
    return key_id
    
    
def addNewScoringRevision(conn,name_scorage):
    '''
    Update the table scoring_revision located in db file of conn.
    
    :Parameters:
    ----------
    name_scorage    :   str
                    ::
    conn            :   sqlite3.Connection
                    ::
    
    :Notes:
    --------
    Sub-functions of MergeDataFiles.
    
    '''
    requetMaxId = " SELECT max(id) FROM scoring_revision "
    requetMaxVersion = " SELECT max(version) FROM scoring_revision WHERE name = '"+name_scorage+"' "
    df = pd.read_sql_query(requetMaxId,conn)
    if(df.empty or df['max(id)'][0]==None) : 
        maxRevisionId = 0
    else :
        maxRevisionId = int(df.loc[0]) + 1
    df = pd.read_sql_query(requetMaxVersion,conn)
    if(df.empty or df['max(version)'][0]==None):
        MaxVersion = 0
    elif(list(df.loc[0])[0] == None ) :
        # Scorage n'exite pas déjà , donc on le crée
        MaxVersion = 0
    else :
        MaxVersion = int(df.loc[0]) + 1
    Date = NdbCare.dateNdb.encodeDateNow()
    row = pd.DataFrame({  'id' : [maxRevisionId],'name':[name_scorage],'is_deleted': [0],'tags':[''],'version':[MaxVersion],'owner':[''],'date_created':[Date]   })
    addRowsToData(row , 'scoring_revision' , conn)
    
    return maxRevisionId


def addNewScoringRevisionToKey(revisionId,keyId,conn,name_scorage):
    '''
    Append a DataFrame to the table scoring_marker located in db file of conn.
    
    :Parameters:
    ----------
    data      :   pandas.DataFrame 
               ::
    key_id    :   int
               ::
    conn      :   sqlite3.Connection
               ::
    
    :Notes:
    --------
    Sub-functions of MergeDataFiles.
    
    '''
    
    requetKeysId= "(SELECT max(id) FROM scoring_revision WHERE name='" + name_scorage + "' AND id < (SELECT max(id) FROM scoring_revision))"
    requetKeysId="SELECT key_id FROM scoring_revision_to_key WHERE revision_id ="+requetKeysId
    df = pd.read_sql_query(requetKeysId,conn)
    L = list(df['key_id'])
    L.append(keyId)
    rows = pd.DataFrame({  'revision_id' : [revisionId]*(df.size+1),'key_id':L})
    rows.to_sql(name='scoring_revision_to_key',con=conn,if_exists='append', index=False)
    conn.commit()
    
    
    

def addNewDataToScoringMarker(data,key_id,conn):
    '''
    Add a DataFrame to the table scoring_marker located in db file of conn.
    
    :Parameters:
    ----------
    data      :   pandas.DataFrame
               :: 
    key_id    :   int
               ::
    conn      :   sqlite3.Connection
               ::
    
    :Notes:
    --------
    Sub-functions of MergeDataFiles.
    
    '''
    requetMaxId = " SELECT max(id) FROM scoring_marker "
    df = pd.read_sql_query(requetMaxId,conn)
    if(df.empty or df['max(id)'][0]==None):
        maxId = 0
    else :
        maxId = int(df.loc[0]) + 1
    L=[]
    for i in range(data['id'].size):
            L.append(maxId+i)
    Di=pd.DataFrame({  'id' : L})
    data['id']=Di['id'].values
    
    data['key_id']=data[['key_id']].apply(lambda x: key_id, axis = 1)
    addRowsToData(data , 'scoring_marker' , conn)


