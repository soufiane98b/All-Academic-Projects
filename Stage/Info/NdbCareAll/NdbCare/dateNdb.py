
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
import NdbCare.writeNdb


def coder_date(anne, mois,j,h,mn,s,ms):
    """
    Takes a date in input and transform it in the format of noxturnal. 
    
    :Parameters:
    ----------
    ms         : int
                 milliseconds between 0 and 1000
    s          : int
                 seconds between 0 and 60
    mn         : int
                 minutes between 0 and 60
    h          : int
                 hours between 0 and 23
    day        : int
                 days between 1 and 31
    mois       : int
                 months between 1 and 12
    anne       : int
                 years between 1 and 9999
    :Returns:
    -------
    date      :  int
                 return the date in format of Noxtural.          
    """
    dt = datetime(anne, mois,j,h,mn,s,ms) 
    seconds = dt.timestamp()
    S=abs(datetime.fromisocalendar(2, 1, 1).timestamp())+seconds+(365*24*3600)-(24*3600)
    return int(S*1000*10000)

def coder_date_3(date):
    """
    Takes a date in input and transform it in the format of noxturnal. 
    
    :Parameters:
    ----------
    date         : int
                   datetime.datetime
    :Returns:
    -------
    date         : int
                   return the date in format of Noxtural.
    :Notes:
    -----
    Third version of coder_date.
    
    Sub-functions of CsvToNdb.   
    """
    seconds = date.timestamp()
    S=abs(datetime.fromisocalendar(2, 1, 1).timestamp())+seconds+(365*24*3600)-(24*3600)
    return int(S*1000*10000)

def coder_date_2(ms, sec, mn, h, day, month, year, precision=None):
    """
    Takes a date in input and transform it in the format of noxturnal. Convert units into higher units, for example if we enter sec=74 it's the same as if enter mn=1 and sec=14. 

    :Parameters:
    ----------
    ms         : int
                ::
    sec        : int 
                ::        
    mn         : int
                ::
    h          : int
                ::
    day        : int
                ::
    month      : int
                ::
    year       : int
                ::
    :Returns:
    -------
    date      :  int
                 return the date in format of Noxtural.           
    :Notes:
    -----
    Second version of coder_date.
    """
    
    s=0
    for i in range (1,year):
        if (i % 4 ==0 and i%100 !=0) or (i%400==0):
            s+=366*24*3600
        else:
            s+=365*24*3600


    if (year % 4 ==0 and year%100 !=0) or (year%400==0):
        m =[ 31 , 29 , 31 , 30 , 31 , 30 , 31 , 31 , 30 ,31 , 30 , 31 ]
        M=0
        for i in range(month-1):
            s+=m[i]*3600*24
    else :
        m =[ 31 , 28 , 31 , 30 , 31 , 30 , 31 , 31 , 30 ,31 , 30 , 31 ]
        for i in range(month-1):
            s+=m[i]*3600*24
    
    s+=h*3600+mn*60+sec+ms/1000+(day-1)*3600*24
    
    return int(s*1000*10000)

def decoder_date(nb):
    """
    Takes a date in the format of noxturnal and return it in the the format datetime.
    
    :Parameters:
    ----------
    nb         : int
                ::
    :Returns:
    -------
    df         : datetime.datetime
                 return the date in format of datetime.        
    :Notes:
    -----
    Sub-functions of real_annot.
    
    """
    nox_datetime =  nb
    seconds_since_JC = nox_datetime//10000000
    milliseconds_since_JC = (nox_datetime - seconds_since_JC*10000000)//10000
    micro_seconds_since_JC = (nox_datetime - seconds_since_JC*10000000 - milliseconds_since_JC*10000)//10
    timedelta_since_JC = timedelta(seconds=seconds_since_JC, milliseconds=milliseconds_since_JC, microseconds=micro_seconds_since_JC)
    JC_datetime = datetime.fromisocalendar(1, 1, 1)
    converted_datetime = JC_datetime + timedelta_since_JC
    return converted_datetime
    
def encodeDateNow():
    '''
    Returns the current date encoded in .ndb format.
    
    :Returns:
    -------
    date      :   int
                ::           
    :Notes:
    -----
    Sub-functions of MergeDataFiles.
    
    '''
    date = datetime.now()
    y = int(date.strftime('%Y'))
    m = int(date.strftime('%m'))
    d = int(date.strftime('%d'))
    h = int(date.strftime('%H'))
    mn = int(date.strftime('%M'))
    s = int(date.strftime('%S'))
    return int(coder_date(y,m, d, h, mn, s, 0))
    
