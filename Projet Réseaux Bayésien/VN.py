import numpy as np

class Variable :
    name = ""
    type = ""
    value = []
    parents = []
    proba = []

    def __init__(self, name, arg):
        self.name = name
        self.type = arg[1]
        self.value = arg[2:]

    def addDepProba(self, par, arg):
        self.parents = par
        if self.parents == []:
            for val in arg[0][1:]:
                self.proba = self.proba + [float(val)]
        else:
            for i in range(len(arg)):
                self.proba = self.proba + [[arg[i][:len(self.parents)]] + [list(np.float_(arg[i][len(self.parents):]))]]

    def trouver_proba(self, bool = None, bool_par = []):
        if len(bool_par) > len(self.parents):
            print("Erreur : la liste de probabilite conditionnelle entree est trop grande")
            return

        while len(bool_par) < len(self.parents):
            bool_par = bool_par + [self.parents[len(bool_par)].value[0]]

        if self.parents == []:
            if(bool==True):
                return self.proba[0]
            else:
                return self.proba[1]


        if self.parents != []:
            for p in self.proba:
                if p[0] == bool_par:
                    if bool == None:
                        return p[1]
                    elif bool:
                        return p[1][0]
                    else:
                        return p[1][1]

        print("Erreur : les valeurs parentes entrees ne correpondent a aucun cas")

    def __repr__(self):
        return self.name

    def info(self):
        np=""
        for p in self.parents:
            np=np+p.name+", "
        return "Nom : "+self.name+" | Parents : "+np


class Network:
    name = ""
    liste_var = []
    prob = []

    def __init__(self, name):
        self.name = name

    def addVar(self, var):
        self.liste_var = self.liste_var + [var]

    def addP(self, dep, arg):
        var = None
        par = []
        for v in self.liste_var:
            if v.name == dep[0]:
                var = v
            elif v.name in dep:
                par = par + [v]

        var.addDepProba(par, arg)

    def addProb(self, arg):
        res = []
        indexMax = -1
        for i in range(len(self.liste_var)):
            val = self.liste_var[i].name
            if val not in arg:
                res = res + [None]
            elif val == arg[0]:
                indexMax = max(indexMax, 0)
                res = res + [self.liste_var[i]]
            else:
                indexMax = max(indexMax, arg.index(val) + 1)
                res = res + [arg[arg.index(val) + 1]]

        self.prob = [res] + [arg[indexMax + 1:]]

    def vByName(self, n):
        for l in self.liste_var:
            if(l.name==n):
                return l

    def __repr__(self):
        np="Le nom du réseau est : "+self.name+"\nVoici les variables : \n"
        for p in self.liste_var:
            np=np+p.info()+"\n"
        return np

    def varibales_certaines_b(self):
        L=[]
        tmp = self.prob[0]
        for i in range(len(tmp)):
            if(tmp[i]=='True' or tmp[i]=='true' or tmp[i]=='1'):
                L.append((self.liste_var[i],True))
            if(tmp[i]=='False' or tmp[i]=='false' or tmp[i]=='0'):
                L.append((self.liste_var[i],False))
        return L

    def varibales_certaines(self):
        L=[]
        tmp = self.prob[0]
        for i in range(len(tmp)):
            if(tmp[i]=='True' or tmp[i]=='true' or tmp[i]=='1'):
                L.append(self.liste_var[i])
            if(tmp[i]=='False' or tmp[i]=='false' or tmp[i]=='0'):
                L.append(self.liste_var[i])
        return L

    def target(self):
        for v in self.prob[0]:
            if isinstance(v,Variable):
                #print(v)
                return v

    # Renvoi la liste des variables dans la couverture de markov
    def enfants_v(self,variable):
        enfants=[]
        for v in self.liste_var:
            if(variable in v.parents):
                enfants.append(v)
        return enfants

    def femmes_v(self,variable):
        femmes=[]
        for v in self.liste_var:
            if(v==variable):
                continue
            inter_enfants = list(set( self.enfants_v(variable) ) & set( self.enfants_v(v) ))
            if( len(inter_enfants) != 0 ):
                femmes.append(v)
        return femmes

    def couverture_markov(self,v):
        return self.enfants_v(v) + self.femmes_v(v) + v.parents

    def proba_v_couverture_markov(self,v,v_bool,echantillon):
        bool_l_parent=[]
        for e in v.parents:
                index = echantillon[0].index(e)
                bool_l_parent.append(echantillon[1][index])
        proba= v.trouver_proba(v_bool,bool_l_parent)
        for tmp in self.couverture_markov(v):
            bool_l_parent=[]
            for e in tmp.parents:
                if(e==v):
                    bool_l_parent.append(str(v_bool).lower())
                else:
                    index = echantillon[0].index(e)
                    bool_l_parent.append(echantillon[1][index]) 
            index = echantillon[0].index(tmp)
            tmp_bool_str = echantillon[1][index]
            if (tmp_bool_str=="true"):
                tmp_bool=True
            if (tmp_bool_str=="false"):
                tmp_bool=False
            if (tmp_bool_str!="false" and tmp_bool_str!="true"):
                return
            proba= proba * tmp.trouver_proba(tmp_bool,bool_l_parent)
        return proba