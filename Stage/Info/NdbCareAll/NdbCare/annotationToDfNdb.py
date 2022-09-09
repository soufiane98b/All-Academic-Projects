import xml.etree.ElementTree as ET
from xml.dom import minidom
from lxml import etree
from tqdm import tqdm
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
import NdbCare.readNdb
import NdbCare.writeNdb
import NdbCare.dateNdb

def convertRmlStagesToDfNdbFormat(fichierRml,startDate,id_start):
    """
    
    Take from rml file sleep stages and put it in a data frame.

    :Parameters:
    ------------
    fichierRml        :   str
                          Name of the rml file.

    startDate         :   str
                          Strating Date of the recording.

    id_start          :   int
                          ::

    :Returns:
    ---------

    df                :   pandas.DataFrame
                          Dataframe with the format of scoring_marker Table of Noxturnal .ndb file.

    :Notes:
    -------

    Sub-functions of RmlToDfNdb.

    """
    
    df=pd.DataFrame(columns=['Type','Start'])
    a=0
    doc = minidom.parse(fichierRml)
    elements = doc.getElementsByTagName("Stage")
    print(f"Nous avons {elements.length} éléments")
    for i in tqdm(elements):
        df.loc[a]=[i.getAttribute("Type"),i.getAttribute("Start")]
        a+=1 
        
    # Taking duration of the recording
    mytree = ET.parse(fichierRml)
    myroot = mytree.getroot()
    duration = int(myroot[2][2][0][1].text)
    
    # Process the df by removing not desired data and reshape indexes and mapping the type
    index =df[df['Type']=='NotScored'].index
    df.drop(index,inplace=True)
    df.reset_index(drop=True,inplace=True)
    df['Type']=df['Type'].apply(NdbCare.mappingNdb.conversionStage)
    
    #Putting the df in new df with the right rows to match the format of ndb files
    df_=pd.DataFrame(columns=['id','starts_at','ends_at','notes','type','location','is_deleted','key_id'])
    df_['starts_at']=df['Start']
    df_['starts_at']=df_['starts_at'].astype(int)
    df_['starts_at']=NdbCare.mappingNdb.heure1(df_['starts_at'],startDate)
    for i in range(df_.shape[0]-1):
        df_['ends_at'][i]=df_['starts_at'][i+1]
    df_['id']=id_start+np.arange(df.shape[0])
    df_['location']=""
    df_['notes']=None
    df_['key_id']=1
    df_['is_deleted']=0
    df_['type']=df['Type']
    df_['ends_at'][df_.shape[0]-1]=NdbCare.mappingNdb.heure(duration,startDate)
    df_['ends_at']=df_['ends_at'].astype(int)
    
    return df_

def RmlToDfNdb(fichierRml):
    """
    Take from rml file annotations and put it in a data frame.

    :Parameters:
    ----------
    fichierRml        :   str
                          Name of the rml file.                  
    :Returns:
    -------
    df                :   pandas.DataFrame
                          Dataframe with the format of scoring_marker Table of Noxturnal .ndb file.
    :Notes:
    -----
    Put this dataFrame in the first argument of the function  AddAnnotationToNdb. 

    :Examples:
    --------
    RmlToDfNdb('B-013.rml')
    
    """  
    #Initialisation
    df=pd.DataFrame(columns=['Type','Start','Duration','Location'])
    
    ## Add Annotations from tags Event of Rml file to df table 
    a=0
    doc = minidom.parse(fichierRml)
    elements = doc.getElementsByTagName("Event")
    print(f"Nous avons {elements.length} éléments")
    for i in tqdm(elements):
        df.loc[a]=[i.getAttribute("Type"),i.getAttribute("Start"),i.getAttribute("Duration"),i.getAttribute("Family")]
        a+=1
    
    ## Removing non usefull tags from df table 
    index=df[df['Type']=='Gain'].index
    df.drop(index,inplace=True)
    index=df[df['Location']=='User'].index
    df.drop(index,inplace=True)
    
    ## Making df in DATA ndb shape
    df.rename(columns = {'Start': 'starts_at','Type':'type','Location':'location'},inplace=True)
    df.reset_index(drop = True, inplace = True)
    
    ## Taking the startRecording Data from RML file
    mytree = ET.parse(fichierRml)
    myroot = mytree.getroot()
    T=myroot[2][2][0][0].text
    T=NdbCare.mappingNdb.decomposerDate(T)
    
    ## Processing the rows of df  
    df['starts_at']=df['starts_at'].astype('float64')
    df['Duration']=df['Duration'].astype('float64')
    df['starts_at']=NdbCare.mappingNdb.heure1(df['starts_at'],T)
    df=df.assign(ends_at=0)
    df['ends_at']=df['starts_at']+df['Duration']*1000*10000
    df['ends_at']=df['ends_at'].astype('int64')
    df.drop(columns=["Duration"],inplace=True)
    df['type']=df['type'].apply(NdbCare.mappingNdb.conversionType)
    df['location']=df['location'].apply(NdbCare.mappingNdb.conversion_Location)
    df=df.assign(notes=None,id=0)
    df['key_id']=1
    df['id']=np.arange(df.shape[0])
    df['is_deleted']=0
    df = df.reindex(columns=['id','starts_at','ends_at','notes','type','location','is_deleted','key_id'])
    
    ## Taking the stages and adding it to data ndb file
    df_=convertRmlStagesToDfNdbFormat(fichierRml,T,df['id'][df.shape[0]-1]+1)
    
    dff = pd.concat([df, df_])
    dff.reset_index(drop=True,inplace=True)
    dff['starts_at']=dff['starts_at'].astype(int)
    return dff

def CsvToDfNdb(fichierCsv,start_date):
    """
    Take from Csv file annotations and put it in a data frame.

    :Parameters:
    ----------
    fichierCsv        :   str
                          Name of the Csv file.
    start_date        :   datetime.datetime
                          The start date of the recording.
    :Returns:
    -------
    df                :   pandas.DataFrame
                          Dataframe with the format of scoring_marker Table of Noxturnal .ndb file.
    :Notes:
    -----
    Put this dataFrame in the first argument of the function  AddAnnotationToNdb.
    
    WARNING : The rows of the Csv : Description and Destination have to respect a strict format. For the row Description, the name of it's values has to be same to 
    the name of events in noxturnal, espicially for the name of sleep stages so it can be recognized by noxturnal (nb: for sleep stages let the row Destination empty). For 
    the row Desinitation, it must have the type of the signal, not "étiquette".
    
    :Examples:
    --------
    CsvToNdb('B-013.csv')
    
    """
    df = pd.read_csv(fichierCsv)
    df.rename(columns = {'Onset': 'starts_at','Description':'type','Destination':'location'},inplace=True)
    df['starts_at']=df['starts_at'].astype(int)
    T = NdbCare.mappingNdb.decomposerDate2(start_date)
    df['starts_at']=NdbCare.mappingNdb.heure1(df['starts_at'],T)
    df['ends_at']=df['starts_at']+df['Duration']*1000*10000
    df.drop(columns=["Duration"],inplace=True)
    df=df.assign(notes=None,id=0)
    df['key_id']=1
    df['id']=np.arange(df.shape[0])
    df['is_deleted']=0
    df = df.reindex(columns=['id','starts_at','ends_at','notes','type','location','is_deleted','key_id'])
    return df

def takeAndTransformDesiredAnnotationToTableNdb(nomFichier,nomFeuilleScorage,location='Pos.Elevation-Gravity',types=(),extension='A'):
    """
    Transform selected annotations from a scoring sheet to a DataFrame. 

    :Parameters:
    ----------
    nomFichier        :   str
                          Name of the .ndb file. 
    nomFeuilleScorage :   str
                          Name of the scoring sheet in the .ndb file where to take the annotations.
    location          :   str, optional
                          Name of the location where to put the annotations, in noxturnal it's reffered to the Type of a signal.
    types             :   tuple, optional
                          Name of the types of annotations that will be selected from nomFeuilleScorage and then renamed.
    extension         :   str, optional
                          The extension to give to our annotations so we can distinguish it from other annotations of other scorers.
    :Returns:
    -------
    df              :   pandas.DataFrame
                        Dataframe with the format of scoring_marker Table of Noxturnal .ndb file.
    :Notes:
    -----
    Put this dataFrame in the first argument of the function  AddAnnotationToNdb.
    
    Duplicates may exist after merge.
    
    Usefull when evaluating annotations with bichat. Avoid to switch between differents scoring sheets.
    
    By default the types that will be selected and modified are : 'arousal' , 'apnea-centralam' , 'hypopnea' , 'apnea-obstructive' , 'rera' , 'apnea-mixed' , 'hypopnea-obstructive','apnea-undefined' , 'hypopnea-central' , 'apnea-central' .
                                                                                                              
    :Examples:
    --------
    takeAndTransformDesiredAnnotationToTableNdb('B-013.ndb','bichat')
    """
    requet1= " (SELECT max(id) FROM scoring_revision WHERE name='" + nomFeuilleScorage + "') )"
    requet1="  SELECT key_id FROM scoring_revision_to_key WHERE revision_id = "+requet1
    if (types != ()) :
        types = str(types)
        requet1 = " SELECT * FROM scoring_marker WHERE type IN "+types+" AND key_id IN ( "+requet1
    else :
        requet1= " SELECT * FROM scoring_marker WHERE type IN ('arousal', 'apnea-centralam', 'hypopnea','apnea-obstructive', 'rera', 'hypopnea-obstructive','apnea-undefined', 'hypopnea-central', 'apnea-central','apnea-mixed') AND key_id IN ( "+requet1
    
    conn = sq.connect(nomFichier)
    df1 = pd.read_sql_query(requet1,conn)
    df1['type']=df1['type']+extension
    df1['location']=location
    
    return df1

        