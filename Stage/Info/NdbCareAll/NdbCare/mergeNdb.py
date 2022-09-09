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
import NdbCare.annotationToDfNdb
import NdbCare.readNdb
import NdbCare.writeNdb
import NdbCare.dateNdb


def MergeDataFiles(name_file,name_file_to_be_modified,name_scorage,name_scorage_to_be_modified):
    """
    Merge Annotations of .ndb files. Take the annotations in the scoring sheet name_scorage  of file name_file and add it in the scoring sheet name_scorage_to_be_modified
    of name_file_to_be_modified.

    :Parameters:
    ----------
    name_file                   :   str
                                    Name of the .ndb file where we take the annotations.
    name_file_to_be_modified    :   str
                                    Name of the .ndb file where we put the annotations.
    name_scorage                :   str
                                    Name of the scoring sheet where we take the annotations. Must exist.
    name_scorage_to_be_modified :   str
                                    Name of the scoring sheet where we put the annotations. If not exist it will be created.
    :Notes:
    -----
    Duplicates may exist after merge.
    
    WARNING : always record the scorages of the data before merge !

    :Examples:
    --------
    MergeDataFiles('Data1.ndb','Data2.ndb','B-SEG','B-SEG2')
    """

    # Initialisation
    conn = sq.connect(name_file_to_be_modified)
    conn1 = sq.connect(name_file)
    
    requet1= " (SELECT max(id) FROM scoring_revision WHERE name='" + name_scorage + "') )"
    requet1="  SELECT key_id FROM scoring_revision_to_key WHERE revision_id = "+requet1
    requet1= " SELECT * FROM scoring_marker WHERE key_id IN ( "+requet1
    
    df1 = pd.read_sql_query(requet1,conn1)
    df_r=df1
   
    # Process diffrent tables
    key_id=NdbCare.updateDependenciesNdb.addNewIdToScoringKey(conn)
    revisionId=NdbCare.updateDependenciesNdb.addNewScoringRevision(conn,name_scorage_to_be_modified)
    NdbCare.updateDependenciesNdb.addNewScoringRevisionToKey(revisionId,key_id,conn,name_scorage_to_be_modified)
    NdbCare.updateDependenciesNdb.addNewDataToScoringMarker(df_r,key_id,conn)
    
    conn.commit()
    conn.close()
    conn.close
    conn1.close()
    conn1.close
    
    # Saving 
    conn = sq.connect(name_file_to_be_modified)
    cur = conn.cursor()
    requet1=" DELETE FROM temporary_scoring_group WHERE id IN (SELECT id FROM temporary_scoring_group)"
    if('temporary_scoring_group' in NdbCare.readNdb.ShowTablesName(name_file_to_be_modified)): cur.execute(requet1)
    conn.commit()
    cur.close()

def MergeByAddAll(name_file,name_file_to_be_modified):
    """
    Add all the information (from specific tables) from name_file to name_file_to_be_modified.

    :Parameters:
    ----------
    name_file                    :   str
                                     Name of the .ndb file where we take all the annotations.
    name_file_to_be_modified     :   str
                                     Name of the .ndb file that will be add all the annotations.                                     
    :Notes:
    -----
    This function will add all the informations from the tables scoring_marker,scoring_revision,scoring_key,scoring_revision_to_key of the file name_file 
    and add it to the file name_file_to_be_modified, so it add all the annotations from name_file to name_file_to_be_modified.
    
    This function could be usefull if the dependecies of the data base are mismanaged. 
    
    WARNING : Use it only in case of the function MergeDataFiles doesn't work well or as expected.
                                                          
    :Examples:
    --------
    MergeByAddAll('B-013.ndb','B-013-2.ndb')
    
    """
    # Initialisation
    conn = sq.connect(name_file_to_be_modified)
    conn1 = sq.connect(name_file)
    
    # Update the table scoring_key
    requet1 = " SELECT max(id) FROM  scoring_key "
    df = pd.read_sql_query(requet1,conn)
    max_id_key = int(df.loc[0])+1
    requet=" SELECT * FROM scoring_key "
    df = pd.read_sql_query(requet,conn1)
    df['id']=df['id']+max_id_key
    df.to_sql(name='scoring_key',con=conn,if_exists='append', index=False)
    
    # Update the tables scoring_marker
    requet1 = " SELECT max(id) FROM  scoring_marker "
    df = pd.read_sql_query(requet1,conn)
    max_id_marker = int(df.loc[0])+1
    requet=" SELECT * FROM scoring_marker "
    df = pd.read_sql_query(requet,conn1)
    df['key_id']=df['key_id']+max_id_key
    df['id']=df['id']+max_id_marker
    df.to_sql(name='scoring_marker',con=conn,if_exists='append', index=False)
    
    # Update the table scoring_revision
    requet1 = " SELECT max(id) FROM  scoring_revision "
    df = pd.read_sql_query(requet1,conn)
    max_id_revision = int(df.loc[0])+1
    requet=" SELECT * FROM scoring_revision "
    df = pd.read_sql_query(requet,conn1)
    df['id']=df['id']+max_id_revision
    df.to_sql(name='scoring_revision',con=conn,if_exists='append', index=False)
    
    # Update the tables coring_revision_to_key
    requet=" SELECT * FROM scoring_revision_to_key "
    df = pd.read_sql_query(requet,conn1)
    df['revision_id']=df['revision_id']+max_id_revision
    df['key_id']=df['key_id']+max_id_key
    df.to_sql(name='scoring_revision_to_key',con=conn,if_exists='append', index=False)
    
    conn.close()
    conn.close
    conn1.close()
    
    # Saving
    conn = sq.connect(name_file_to_be_modified)
    cur = conn.cursor()
    requet1=" DELETE FROM temporary_scoring_group WHERE id IN (SELECT id FROM temporary_scoring_group)"
    if('temporary_scoring_group' in NdbCare.readNdb.ShowTablesName(name_file_to_be_modified)): cur.execute(requet1)
    conn.commit()
    conn.close()

def AddAnnotationToNdb(table,name_file_to_be_modified,name_scorage_to_be_modified):
    """
    Add annotations to an .ndb file.

    :Parameters:
    ----------
    table                           :   pandas.DataFrame
                                        Dataframe with the format of scoring_marker Table of Noxturnal .ndb file.
    name_file_to_be_modified        :   str
                                        Name of the .ndb file that will be changed.
    name_scorage_to_be_modified     :   str
                                        Name of the scoring sheet in the .ndb file where tu put the annotations in table, if not exist it will be created.                                 
    :Notes:
    -----
    This function updates all the table dependencies in the database.
    
    This function is used after using this function : takeAndTransformDesiredAnnotationToTableNdb or RmlToDfNdb or CsvToDfNdb.
                                                          
    :Examples:
    --------
    AddAnnotationToNdb(df,'B-013.ndb','bichat')
    
    """
    # Initialisation
    conn = sq.connect(name_file_to_be_modified)
    df_r=table
   
    # Process diffrent tables
    key_id=NdbCare.updateDependenciesNdb.addNewIdToScoringKey(conn)
    revisionId=NdbCare.updateDependenciesNdb.addNewScoringRevision(conn,name_scorage_to_be_modified)
    NdbCare.updateDependenciesNdb.addNewScoringRevisionToKey(revisionId,key_id,conn,name_scorage_to_be_modified)
    NdbCare.updateDependenciesNdb.addNewDataToScoringMarker(df_r,key_id,conn)
    conn.commit()
    conn.close()
    conn.close
    
    ## Saving
    conn = sq.connect(name_file_to_be_modified)
    cur = conn.cursor()
    requet1=" DELETE FROM temporary_scoring_group WHERE id IN (SELECT id FROM temporary_scoring_group)"
    if('temporary_scoring_group' in NdbCare.readNdb.ShowTablesName(name_file_to_be_modified)): cur.execute(requet1)
    conn.commit()
    cur.close()

