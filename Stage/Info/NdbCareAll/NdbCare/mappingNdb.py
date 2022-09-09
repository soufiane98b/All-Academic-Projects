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
import NdbCare.mergeNdb
import NdbCare.annotationToDfNdb
import NdbCare.readNdb
import NdbCare.writeNdb
import NdbCare.dateNdb

def conversionType(typee):
    """
    Convert the types of annotations in the rml file  in the good type for Noxturnal.

    :Parameters:
    ----------
    typee      :  str
                  Name of the type in the rml file (in the attribute Type of the marker Event).
    :Returns:
    -------
    typee      :  str
                  return the good label for Noxturnal.
    :Notes:
    -----
    Sub-functions of RmlToDfNdb.
    
    The all different types detected in all rml files are : 'Gain' , 'ChannelFail' , 'LegMovement' , 'RelativeDesaturation' , 'Arousal' , 'Hypopnea' , 'CentralApnea', 'ObstructiveApnea' , 'ObstructiveHypopnea' , 'Comment' , 'Bradycardia' , 'Snore' , 'LongRR' ,'PttDrop' , 'Tachycardia' , 'MixedApnea' , 'CentralHypopnea' ,'PeriodicRespiration' , 'AbsoluteDesaturation',  'Hypoventilation' ,'Asystole' , 'RERA', 'HeartRateDrop'
    """
    if(typee=='LegMovement'):
        return 'LegMovement'
    
    if(typee=='ObstructiveApnea'):
        return 'apnea-obstructive'   
    
    if(typee=='Hypopnea'):
        return 'hypopnea'
    
    if(typee=='CentralApnea'):
        return 'apnea-central'
    
    if(typee=='Snore'):
        return 'snorebreath' 
    
    if(typee=='MixedApnea'):
        return 'apnea-mixed'
        
    if(typee=='RERA'):
        return 'rera'
        
    if(typee=='PeriodicRespiration'):
        return 'breathing-periodic'
    
    if(typee=='ObstructiveHypopnea'):
        return 'hypopnea-obstructive'
    
    if(typee=='Bradycardia'):
        return 'arrhythmia-bradycardia'
    
    if(typee=='Tachycardia'):
        return 'arrhythmia-tachycardia'
    
    if(typee=='Arousal'):
        return 'arousal'
    
    if(typee=='RelativeDesaturation'):
        return 'oxygensaturation-drop'
    
    if(typee=='AbsoluteDesaturation'):
        return 'oxygensaturation-drop'
    
    if(typee=='AbsoluteDesaturation'):
        return 'oxygensaturation-drop'
    
    if(typee=='PttDrop'):
        return 'ptt-drop'
    
    return typee

def conversion_Location(l):
    """
    Convert the types of locations in the rml file in the good type for Noxturnal.
    In noxtunral a location refers to the Type of a signal (not to étiquette).

    :Parameters:
    ----------
    l         :    str
                   Name of the location in the rml file (in the attribute Family of the marker Event).
    :Returns:
    -------
    typee     :    str
                   return the good label for Noxturnal.         
    :Notes:
    -----
    Sub-functions of RmlToDfNdb.
    
    The all different types detected in all rml files are : 'User' , 'Limb' , 'SpO2' , 'Neuro' , 'Respiratory' , 'Cardiac' , 'Nasal' , 'Snore'
    
    """
    if(l=='Limb'):
        return 'EMG.Tibialis-Leg.Left'
    if(l=='SpO2'):
        return 'SpO2.Averaged-Probe'
    if(l=='Respiratory' or l=='Nasal'):
        return 'Resp.Flow-Cannula.Nasal'
    if(l=='Neuro'):
        return 'EMG.Submental-1-F'
    if(l=='Snore'):
        return 'Resp.Snore-Cannula.Nasal'
    if(l=='Cardiac'):
        return 'HeartRate-EKG'
    
    return l

def conversionStage(typee):
    """
    Convert the types of stages in the rml file in the good type for Noxturnal.

    :Parameters:
    ----------
    typee        :  str
                    Name of the location in the rml file (in the attribute Type of the marker Stage).
    :Returns:
    -------
    typee        :  str
                    return the good label for Noxturnal.             
    :Notes:
    -----
    Sub-functions of RmlToDfNdb.
    
    The all different stages detected in all rml files are : 'NonREM1' , 'NonREM2' , 'NonREM3' , 'Wake' , 'REM'
    
    """
    if(typee=='NonREM1'):
        return 'sleep-n1'   
    
    if(typee=='NonREM2'):
        return 'sleep-n2'
    
    if(typee=='NonREM3'):
        return 'sleep-n3'
    
    if(typee=='Wake'):
        return 'sleep-wake'
    
    if(typee=='REM'):
        return 'sleep-rem'
    return typee

def decomposerDate(date):
    """           
    Notes
    -----
    Sub-functions of RmlToDfNdb.
        
    """
    a = int(date[0:4])
    m = int(date[5:7])
    j = int(date[8:10])
    h = int(date[11:13])
    mn = int(date[14:16])
    s = int(date[17:19])
    L=[a,m,j,h,mn,s]
    return L

def decomposerDate2(date):
    """           
    Notes
    -----
    Sub-functions of CsvToDfNdb.
        
    """
    y = int(date.strftime('%Y'))
    m = int(date.strftime('%m'))
    d = int(date.strftime('%d'))
    h = int(date.strftime('%H'))
    mn = int(date.strftime('%M'))
    s = int(date.strftime('%S'))
    return [y,m,d,h,mn,s]
       
def heure(nb,T):
    """           
    Notes
    -----
    Sub-functions of RmlToDfNdb.
        
    """
    return NdbCare.dateNdb.coder_date_2(0,T[5]+nb,T[4], T[3], T[2],T[1],T[0]) 

def heure1(row,T):
    """           
    Notes
    -----
    Sub-functions of RmlToDfNdb.
        
    """
    for i in range(row.size):
        row[i]= NdbCare.dateNdb.coder_date_2(0,T[5]+row[i],T[4], T[3], T[2],T[1],T[0]) 
    return row
